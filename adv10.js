#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents.split("\n")

let partOne = 0
let partTwo = 0

const opening = new Set("({[<".split(""))
const closing = new Set(">]})".split(""))
const openingCorresponding = {
  "(": ")",
  "{": "}",
  "[": "]",
  "<": ">",
}

const charScore = {
  ")": 3,
  "]": 57,
  "}": 1197,
  ">": 25137,
}

const completeScore = {
  "(": 1,
  "[": 2,
  "{": 3,
  "<": 4,
}

function complete(line) {
  let stack = []
  for (const char of line.split("")) {
    if (opening.has(char)) stack.push(char)
    else if (closing.has(char)) {
      stack.pop()
    } else throw `Unknown char ${char}`
  }

  let score = 0
  while (stack.length > 0) {
    let char = stack.pop()
    score = score * 5 + completeScore[char]
  }

  return score
}

function isCorrect(line) {
  let stack = []
  for (const char of line.split("")) {
    if (opening.has(char)) stack.push(char)
    else if (closing.has(char)) {
      const last = stack.pop()
      if (openingCorresponding[last] !== char) {
        return charScore[char]
      }
    } else throw `Unknown char ${char}`
  }

  return 0
}

partOne = data.map((line) => isCorrect(line)).reduce((a, v) => a + v)

const scores = data
  .filter((line) => isCorrect(line) === 0)
  .map((line) => complete(line))
  .sort((a, b) => a - b)
partTwo = scores[Math.floor(scores.length / 2)]

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
