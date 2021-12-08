#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()

const data = contents
  .split("\n")
  .map((line) => line.split(",").map((value) => parseInt(value)))[0]

const partOne = calculateMovement(data, crabMovement)
const partTwo = calculateMovement(data, crabComplexMovement)

function calculateMovement(crabs, movementCalculator) {
  const min = Math.min(...crabs)
  const max = Math.max(...crabs)

  let fuel = Number.MAX_SAFE_INTEGER
  for (let i = min; i <= max; i++)
    fuel = Math.min(
      fuel,
      crabs.reduce((sum, crab) => sum + movementCalculator(crab, i), 0)
    )
  return fuel
}

function crabMovement(crab, toPosition) {
  return Math.abs(crab - toPosition)
}

function crabComplexMovement(crab, toPosition) {
  const steps = crabMovement(crab, toPosition)
  return ((1 + steps) * steps) / 2
}

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
