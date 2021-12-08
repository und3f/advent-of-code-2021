#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
let data = contents.split("\n").map((line) => {
  let data = line.split(" ")
  data[1] = parseInt(data[1])
  return data
})

let horizontal = 0,
  depth = 0,
  aim = 0

for (let move of data) {
  const value = move[1]
  switch (move[0]) {
    case "forward":
      horizontal += value
      depth += aim * value
      break
    case "up":
      aim -= value
      break
    case "down":
      aim += value
      break
  }
}

console.log("Part One:", horizontal * aim)
console.log("Part Two:", horizontal * depth)
