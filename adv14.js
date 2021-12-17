#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents.split("\n\n")

const template = data[0]
const rules = new Map(data[1].split("\n").map((line) => line.split(" -> ")))

console.log(rules)

function pairInsertion(template, rule) {
  const pair = rule[0]

  return template.replace(pair, pair[0] + rule[1] + pair[1])
}

function iteration(template, rules) {
  let result = template[0]
  for (let i = 0; i < template.length - 1; i++) {
    const pair = template.substring(i, i + 2)

    const replace = rules.get(pair)
    result += replace + pair[1]
  }

  return result
}

let processedTemplate = template

const Steps = 10
for (let i = 0; i < Steps; i++)
  processedTemplate = iteration(processedTemplate, rules)

function calcOccurs(processedTemplate) {
  elementsOccurance = {}
  for (const el of processedTemplate) {
    elementsOccurance[el] ||= 0
    elementsOccurance[el]++
  }
  console.log(elementsOccurance)
  // const elements = Object.keys(elementsOccurance).sort((b, a) => elementsOccurance[a] - elementsOccurance[b])
  const occurs = Object.values(elementsOccurance).sort((a, b) => b - a)
  return occurs[0] - occurs[occurs.length - 1]
}
let partOne = calcOccurs(processedTemplate)

const StepsP2 = 40
for (let i = Steps; i < StepsP2; i++) {
  processedTemplate = iteration(processedTemplate, rules)
  console.log("Step " + i)
  calcOccurs(processedTemplate)
}

// .map((line) => line.split(",").map((line) => parseInt(line)))
const partTwo = calcOccurs(processedTemplate)

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
