#!/usr/bin/env node

"use strict"
const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents.split("\n\n")

let coords = data[0]
  .split("\n")
  .map((line) => line.split(",").map((line) => parseInt(line)))
const foldRe = /^fold along (y|x)=(\d+)$/
const folds = data[1].split("\n").map((line) => {
  const m = foldRe.exec(line)
  return [m[1], m[2]]
})

function mapToString(coords) {
  const maxX = Math.max(...coords.map((coord) => coord[0])) + 1
  const maxY = Math.max(...coords.map((coord) => coord[1])) + 1

  const map = new Array(maxY).fill(null).map(() => new Array(maxX).fill(" "))
  for (const coord of coords) map[coord[1]][coord[0]] = "#"

  return "\n" + map.map((row) => row.join("")).join("\n")
}

function foldX(lineCoord, _coords) {
  let coords = _coords.map((coord) => {
    if (coord[0] < lineCoord) return coord
    else return [lineCoord - (coord[0] - lineCoord), coord[1]]
  })
  return coords
}

function foldY(lineCoord, _coords) {
  let coords = _coords.map((coord) => {
    if (coord[1] < lineCoord) return coord
    else return [coord[0], lineCoord - (coord[1] - lineCoord)]
  })
  return coords
}

function processFolds(coords, folds) {
  for (const fold of folds) {
    if (fold[0] === "y") coords = foldY(fold[1], coords)
    else coords = foldX(fold[1], coords)

    coords = coords.filter(
      (coord, i, a) =>
        a.findIndex(
          (coord2) => coord2[0] === coord[0] && coord2[1] === coord[1]
        ) === i
    )
  }

  return coords
}

let partOne = processFolds(coords, [folds[0]]).length,
  partTwo = mapToString(processFolds(coords, folds))

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
