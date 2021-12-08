#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents
  .split("\n")
  .map((line) => line.split(",").map((line) => parseInt(line)))[0]

const RESET_TIMER = 6
const SPAWN_TIMER = 8
const DAYS1 = 80
const DAYS2 = 256

let fishes = new Array(SPAWN_TIMER + 1).fill(0)
for (const fish of data) fishes[fish]++

function day(fishes) {
  const newFishes = 0

  let spawn = fishes.shift()
  fishes.push(spawn)
  fishes[RESET_TIMER] += spawn
  return fishes
}

for (let i = 0; i < DAYS1; i++) {
  fishes = day(fishes)
}
const partOne = fishes.reduce((a, v) => a + v)

for (let i = DAYS1; i < DAYS2; i++) {
  fishes = day(fishes)
}
const partTwo = fishes.reduce((a, v) => a + v)

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
