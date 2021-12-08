#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
let data = contents.split("\n").map(parseInt)

let pre = data[0]
let larger = 0
let preWindow
let largerWindow = 0
for (let i = 2; i < data.length; i++) {
  let window = 0
  for (let j = 0; j < 3; j++) {
    if (i - j >= 0) window += data[i - j]
  }
  if (preWindow !== undefined) if (preWindow < window) largerWindow++
  preWindow = window

  if (pre < data[i]) {
    larger++
  }
  pre = data[i]
}

console.log("Part One:", larger)
console.log("Part Two:", largerWindow)
