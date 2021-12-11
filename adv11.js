#!/usr/bin/env node

"use strict"
const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents
  .split("\n")
  .map((line) => line.split("").map((line) => parseInt(line)))

let partOne = 0,
  partTwo = 0

function adjV(map, row, col) {
  let around = []
  for (let i = -1; i <= 1; i++) {
    around.push([row + i, col - 1])
    around.push([row + i, col + 1])
  }
  around.push([row - 1, col])
  around.push([row + 1, col])

  return around.filter(
    (p) =>
      p[0] >= 0 && p[1] >= 0 && p[0] < map.length && p[1] < map[p[0]].length
  )
}

const FlashingValue = 10

function findFlashing(map) {
  const flashing = []
  for (let row = 0; row < map.length; row++) {
    for (let col = 0; col < map[row].length; col++) {
      if (map[row][col] >= FlashingValue) flashing.push([row, col])
    }
  }
  return flashing
}

function processMap(map) {
  for (let row = 0; row < map.length; row++)
    for (let col = 0; col < map[row].length; col++) map[row][col]++

  let flashed = new Set()
  let flashedNew = false
  do {
    flashedNew = false
    let flashing = findFlashing(map)

    for (const point of flashing) {
      const pointS = point.join(",")
      if (flashed.has(pointS)) continue

      flashedNew = true
      flashed.add(pointS)
      adjV(map, ...point).map((p) => map[p[0]][p[1]]++)
    }
  } while (flashedNew)

  for (const pointS of flashed.values()) {
    const [row, col] = pointS.split(",")
    map[row][col] = 0
  }
  return flashed.size
}

const partOneSteps = 100
partOne = 0
for (let i = 0; i < partOneSteps; i++) partOne += processMap(data)

const partTwoSteps = 195
for (let i = partOneSteps; true; i++) {
  const flashed = processMap(data)
  if (flashed === data.length * data[0].length) {
    partTwo = i + 1
    break
  }
}

console.log(data.map((row) => row.join("")).join("\n"), "\n")

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
