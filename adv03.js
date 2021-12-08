#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
let data = contents
  .split("\n")
  .map((line) => line.split("").map((bit) => parseInt(bit)))

function cb(data, d, mcb) {
  const bits = [0, 0]
  for (const word of data) {
    bits[word[d]]++
  }
  if ((bits[0] > bits[1]) ^ mcb) {
    return 1
  } else return 0
}

let gamma = 0,
  epsilon = 0
for (let i = 0; i < data[0].length; i++) {
  gamma = gamma * 2 + cb(data, i, true)
  epsilon = epsilon * 2 + cb(data, i, false)
}
console.log("Part one:", gamma * epsilon)

let filtered = data.slice()
let filtered2 = data.slice()

for (let i = 0; i < data[0].length; i++) {
  if (filtered.length > 1) {
    let bit = cb(filtered, i, true)
    filtered = filtered.filter((record) => record[i] === bit)
  }

  if (filtered2.length > 1) {
    let lcb = cb(filtered2, i, false)
    filtered2 = filtered2.filter((record) => record[i] === lcb)
  }
}
const oxygen = filtered[0].reduce((a, v) => a * 2 + v, 0)
const co2 = filtered2[0].reduce((a, v) => a * 2 + v, 0)
console.log("Part two:", oxygen * co2)
