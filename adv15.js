#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents
  .split("\n")
  .map((line) => line.split("").map((line) => parseInt(line)))

class G {
  map
  V

  constructor(data) {
    this.map = data
    this.V = data.length * data[0].length
  }

  adj(v) {
    const col = v % this.map[0].length
    const row = Math.floor(v / this.map[0].length)

    let around = []

    around.push([row, col - 1])
    around.push([row, col + 1])
    around.push([row - 1, col])
    around.push([row + 1, col])

    return around
      .filter(
        (p) =>
          p[0] >= 0 &&
          p[1] >= 0 &&
          p[0] < this.map.length &&
          p[1] < this.map[p[0]].length
      )
      .map((p) => [p[0] * this.map[0].length + p[1], this.map[p[0]][p[1]]])
  }

  lastV() {
    return this.map[0].length * this.map.length - 1
  }

  pathTo
  bfs(start) {
    this.pathTo = new Array(this.V).fill(Number.MAX_SAFE_INTEGER)
    this.pathTo[start] = 0

    let pq = [start]
    while (pq.length > 0) {
      const v = pq.shift()

      for (const [w, weight] of this.adj(v)) {
        const fromVWeight = this.pathTo[v] + weight
        if (this.pathTo[w] > fromVWeight) {
          this.pathTo[w] = fromVWeight
          pq.push(w)
        }
      }
      pq.sort()
    }
  }

  toString(map) {
    return "\n" + map.map((row) => row.join("")).join("\n")
  }
}

let partOne, partTwo

const g = new G(data)
g.bfs(0)
partOne = g.pathTo[g.lastV()]

const Multiplier = 5
const data2 = new Array(data.length * Multiplier)
  .fill(null)
  .map(() => new Array(data[0].length * Multiplier).fill(0))

const WrapValue = 9
function copyData(dst, src, row, col) {
  const inc = row + col

  for (let i = 0; i < src.length; i++) {
    for (let j = 0; j < src[i].length; j++) {
      let value = ((src[i][j] + row + col - 1) % WrapValue) + 1
      dst[row * src.length + i][col * src[i].length + j] = value
    }
  }
}

for (let row = 0; row < Multiplier; row++)
  for (let col = 0; col < Multiplier; col++) copyData(data2, data, row, col)

// console.log(data2.map((row) => row.join("")).join("\n"))
const g2 = new G(data2)
g2.bfs(0)
partTwo = g2.pathTo[g2.lastV()]

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
