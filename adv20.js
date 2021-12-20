#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents.split("\n\n")

class Image {
  constructor(str, emptyState) {
    this.image = str.map((line) => line.split(""))
    this.emptyState = emptyState || 0
  }

  resize() {
    for (let i = 0; i < this.image.length; i++)
      this.image[i] = ["."].concat(this.image[i], ["."])
    this.image.unshift(new Array(this.image[0].length).fill("."))
    this.image.push(new Array(this.image[0].length).fill("."))
  }

  value(row, col) {
    const image = this.image
    let res = 0
    for (let i = -1; i <= 1; i++) {
      for (let j = -1; j <= 1; j++) {
        let value = this.emptyState
        if (
          row + i >= 0 &&
          col + j >= 0 &&
          row + i < image.length &&
          col + j < image[0].length
        )
          value = image[row + i][col + j] === "#" ? 1 : 0

        res = res * 2 + value
      }
    }
    return res
  }

  applyAlg(alg) {
    let res = []
    for (let row = -2; row < this.image.length + 2; row++) {
      let str = ""
      for (let col = -2; col < this.image[0].length + 2; col++) {
        str += alg[this.value(row, col)]
      }
      res.push(str)
    }
    const emptyState = alg[this.value(-3, -3)] === "#" ? 1 : 0

    return new Image(res, emptyState)
  }

  litValue() {
    let lit = 0
    for (let row = 0; row < this.image.length; row++)
      for (let col = 0; col < this.image[0].length; col++)
        if (this.image[row][col] === "#") lit++
    return lit
  }

  toString() {
    return (
      this.image.map((line) => line.join("")).join("\n") +
      `\nLitting: ${this.litValue()}\n` +
      "\n"
    )
  }
}

const Iterations = 2
const Iterations2 = 50
let image = new Image(data[1].split("\n"))
const alg = data[0]
for (let i = 0; i < Iterations; i++) {
  image = image.applyAlg(alg)
  console.log(image.toString())
}

let partOne = image.litValue(),
  partTwo

for (let i = Iterations; i < Iterations2; i++) {
  image = image.applyAlg(alg)
}
partTwo = image.litValue()

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
