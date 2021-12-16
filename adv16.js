#!/usr/bin/env node

"use strict"
const fs = require("fs")
const path = require("path")
const { inspect } = require("util")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents.split("\n")[0]

const ByteBits = 4
class Datastream {
  // bytes, byteOffset, bitOffset

  constructor(data) {
    this.bytes = data
    this.byteOffset = 0
    this.bitOffset = 0
  }

  parseBits(size) {
    let bits = 0

    while (size > 0) {
      const available = ByteBits - this.bitOffset

      if (available >= size) {
        const mask = (1 << size) - 1
        const chunk = (this.bytes[this.byteOffset] >> (available - size)) & mask
        bits <<= size
        bits += chunk
        this.bitOffset += size
        if (this.bitOffset === ByteBits) {
          this.bitOffset = 0
          this.byteOffset++
        }

        size = 0
      } else {
        bits <<= available
        const mask = (1 << available) - 1
        bits += this.bytes[this.byteOffset] & mask

        size -= available
        this.bitOffset = 0
        this.byteOffset++
      }
    }
    return bits
  }

  padByte() {
    if (this.bitOffset > 0) {
      this.bitOffset = 0
      this.byteOffset++
    }
  }

  bitsOffset() {
    return this.byteOffset * ByteBits + this.bitOffset
  }
}

class PacketStream {
  constructor(stream) {
    this.stream = stream
  }

  parseLiteralValue() {
    let literal = []
    let lastGroup = false

    do {
      lastGroup = this.stream.parseBits(1) === 0
      let byte = this.stream.parseBits(4)
      literal.push(byte)
    } while (!lastGroup)
    // this.stream.padByte()
    return literal.reduce((a, v) => a * 16n + BigInt(v), 0n)
  }

  parseSubPacketsByBits() {
    const packetsBits = this.stream.parseBits(15)

    let packets = []
    const dst = packetsBits + this.stream.bitsOffset()
    while (dst > this.stream.bitsOffset()) {
      packets.push(this.parsePacket())
    }

    return packets
  }

  parseSubPacketsByAmount() {
    const packetsAmount = this.stream.parseBits(11)

    let packets = []
    while (packets.length < packetsAmount) packets.push(this.parsePacket())

    return packets
  }

  parseSubPacketsSize() {
    const lengthTypeID = this.stream.parseBits(1)
    if (lengthTypeID === 0) return this.parseSubPacketsByBits()
    else return this.parseSubPacketsByAmount()
  }

  parsePacket() {
    const version = this.stream.parseBits(3)
    const typeID = this.stream.parseBits(3)

    let packet = {
      version: version,
      type: typeID,
    }

    if (typeID === 4) {
      packet.data = this.parseLiteralValue()
    } else {
      packet.packets = this.parseSubPacketsSize()
    }

    return packet
  }
}

function sumVersions(packet) {
  let sum = packet.version

  if (!("packets" in packet)) return sum

  for (const sp of packet.packets) {
    sum += sumVersions(sp)
  }
  return sum
}

function packetValue(packet) {
  if (packet.type === 4) return packet.data

  const packets = packet.packets.map((packet) => packetValue(packet))

  switch (packet.type) {
    case 0:
      return packets.reduce((a, v) => a + v, 0n)
    case 1:
      return packets.reduce((a, v) => a * v, 1n)
    case 2:
      return packets.reduce((m, e) => (e < m ? e : m))
    case 3:
      return packets.reduce((m, e) => (e > m ? e : m))
    case 5:
      return packets[0] > packets[1] ? 1n : 0n
    case 6:
      return packets[0] < packets[1] ? 1n : 0n
    case 7:
      return packets[0] === packets[1] ? 1n : 0n
  }
}

function printPacket(packet) {
  if (packet.type === 4) return packet.data

  const packets = packet.packets.map((packet) => printPacket(packet))
  function coverJoin(joinE) {
    return `(${packets.join(joinE)})`
  }

  switch (packet.type) {
    case 0:
      return coverJoin(" + ")
    case 1:
      return coverJoin(" * ")
    case 2:
      return "Min" + coverJoin(", ")
    case 3:
      return "Max" + coverJoin(", ")
    case 5:
      return coverJoin(" > ")
    case 6:
      return coverJoin(" < ")
    case 7:
      return coverJoin(" = ")
  }

  return
}

const datastream = new Datastream(
  data.split("").map((char) => parseInt(char, 16))
)
const ps = new PacketStream(datastream)

const packet = ps.parsePacket()

let partOne = sumVersions(packet),
  partTwo = packetValue(packet).toString()

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
