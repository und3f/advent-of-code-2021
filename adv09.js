#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents
  .split("\n")
  .map((line) => line.split("").map((line) => parseInt(line)))

let partOne = 0
let partTwo = 1

function adj(map, row, col) {
  return [
    [row - 1, col],
    [row, col + 1],
    [row + 1, col],
    [row, col - 1],
  ].filter(
    (p) =>
      p[0] >= 0 && p[1] >= 0 && p[0] < map.length && p[1] < map[p[0]].length
  )
}

function isLowPoint(map, row, col) {
  const location = map[row][col]
  for (const p of adj(map, row, col)) {
    if (map[p[0]][p[1]] <= location) return false
  }
  return true
}

function findBasin(map, startP) {
  let size = 0

  let points = [startP]
  while ((p = points.shift())) {
    const pValue = map[p[0]][p[1]]

    if (pValue !== 9) {
      map[p[0]][p[1]] = 9
      size++
    }

    for (const nextP of adj(map, ...p)) {
      const value = map[nextP[0]][nextP[1]]
      if (value < 9) {
        points.push(nextP)
      }
    }
  }
  return size
}

let points = []
for (let row = 0; row < data.length; row++) {
  for (let col = 0; col < data[row].length; col++) {
    if (isLowPoint(data, row, col)) {
      partOne += data[row][col] + 1
      points.push([row, col])
    }
  }
}

let basins = []
for (const p of points) {
  basins.push(findBasin(data, p))
}

basins.sort((b, a) => a - b)

partTwo = basins.slice(0, 3).reduce((a, v) => a * v)

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
