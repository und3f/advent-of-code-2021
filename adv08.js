#!/usr/bin/env node

"use strict"
const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents
  .split("\n")
  .map((line) => line.split(/\s*\|\s*/).map((line) => line.split(" ")))

const allNumbersSections = [6, 2, 5, 5, 4, 5, 6, 3, 7, 6]
const numbersSections = [0, 2, 0, 0, 4, 0, 0, 3, 7, 0]
let mapping = new Array(7).fill(null)
const clockDigits = [
  "abcefg",
  "cf",
  "acdeg",
  "acdfg",
  "bcdf",
  "abdfg",
  "abdefg",
  "acf",
  "abcdefg",
  "abcdfg",
]

const refMappings = clockDigits.map((segments) => new Set(segments.split("")))
const allDigits = refMappings[8]
const allMaps = {}
for (const section of allDigits) {
  allMaps[section] = allDigits
}
const bSet = new Set("b")
const cSet = new Set("c")
const gSet = new Set("g")

function updateMappings(possibleMappings, sections, refMap) {
  for (const section of allDigits) {
    let modifier = (a) => a
    if (sections.indexOf(section) === -1) modifier = (a) => !a

    possibleMappings[section] = new Set(
      [...possibleMappings[section]].filter((possibleMappingSection) =>
        modifier(refMap.has(possibleMappingSection))
      )
    )
  }
}

let partOne = 0
for (const segment of data) {
  for (const digit of segment[1]) {
    const number = numbersSections.indexOf(digit.length)
    if (-1 !== number) {
      partOne++
    }
  }
}

let partTwo = 0
for (const segment of data) {
  let possibleMappings = Object.assign({}, allMaps)

  for (const digit of segment[0]) {
    const number = numbersSections.indexOf(digit.length)
    const refMap = refMappings[number]

    if (-1 !== number) {
      updateMappings(possibleMappings, digit, refMap)
    }
  }

  // Find Zero by checking bb dddd sections
  const bdSet = new Set(
    Object.keys(possibleMappings).filter(
      (k) => possibleMappings[k].has("d") && possibleMappings[k].has("b")
    )
  )

  for (const digit of segment[0]) {
    const number = allNumbersSections.indexOf(digit.length)
    const refMap = refMappings[number]
    if (0 === number) {
      const intersection = digit.split("").filter((s) => bdSet.has(s))
      if (intersection.length === 1) {
        updateMappings(possibleMappings, intersection[0], bSet)
      }
    }
  }

  // Find Five, it should be 5 digits and have bd section
  const egSet = new Set(
    Object.keys(possibleMappings).filter(
      (k) => possibleMappings[k].has("e") && possibleMappings[k].has("g")
    )
  )
  for (const digit of segment[0]) {
    const number = allNumbersSections.indexOf(digit.length)
    const refMap = refMappings[number]
    if (2 === number) {
      const intersection = digit.split("").filter((s) => bdSet.has(s))
      if (intersection.length === 2) {
        const gIntersection = digit.split("").filter((s) => egSet.has(s))
        updateMappings(possibleMappings, gIntersection[0], gSet)
      }
    }
  }
  // Find Two, have eg full intersection
  const cfSet = new Set(
    Object.keys(possibleMappings).filter(
      (k) => possibleMappings[k].has("c") && possibleMappings[k].has("f")
    )
  )
  for (const digit of segment[0]) {
    const number = allNumbersSections.indexOf(digit.length)
    const refMap = refMappings[number]
    if (2 === number) {
      const intersection = digit.split("").filter((s) => egSet.has(s))
      if (intersection.length === 2) {
        const cIntersection = digit.split("").filter((s) => cfSet.has(s))
        updateMappings(possibleMappings, cIntersection[0], cSet)
      }
    }
  }

  const mappings = {}
  for (const [c, m] of Object.entries(possibleMappings)) {
    mappings[c] = m.values().next().value
  }

  let digitValue = 0
  for (const digit of segment[1]) {
    const segments = digit
      .split("")
      .map((s) => mappings[s])
      .sort()
      .join("")
    digitValue = digitValue * 10 + clockDigits.indexOf(segments)
  }
  partTwo += digitValue
}

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
