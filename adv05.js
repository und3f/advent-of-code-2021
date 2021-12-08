#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents
  .split("\n")
  .map((line) =>
    line
      .split(" -> ")
      .map((part) => part.split(",").map((line) => parseInt(line)))
  )

let points = new Map()
let points2 = new Map()

for (const line of data) {
  const [start, end] = line

  if (start[1] == end[1]) {
    const y = start[1]
    for (
      let x = Math.min(start[0], end[0]);
      x <= Math.max(start[0], end[0]);
      x++
    ) {
      const coord = [x, y].join(",")
      points[coord] = (points[coord] || 0) + 1
      points2[coord] = (points2[coord] || 0) + 1
    }
  } else if (start[0] == end[0]) {
    const x = start[0]
    for (
      let y = Math.min(start[1], end[1]);
      y <= Math.max(start[1], end[1]);
      y++
    ) {
      const coord = [x, y].join(",")
      points[coord] = (points[coord] || 0) + 1
      points2[coord] = (points2[coord] || 0) + 1
    }
  } else {
    let step = [1, 1]
    if (start[0] > end[0]) step[0] = -1
    if (start[1] > end[1]) step[1] = -1
    for (
      let x = start[0], y = start[1];
      x != end[0] + step[0], y != end[1] + step[1];
      x += step[0], y += step[1]
    ) {
      const coord = [x, y].join(",")
      points2[coord] = (points2[coord] || 0) + 1
    }
  }
}

let partOne = 0,
  partTwo = 0

for (const i of Object.values(points)) {
  if (i > 1) partOne++
}

for (const i of Object.values(points2)) {
  if (i > 1) partTwo++
}

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
