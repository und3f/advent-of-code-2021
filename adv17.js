#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents.split("\n")[0]

const target = Object.fromEntries(
  data
    .split(": ")[1]
    .split(", ")
    .map((f) => {
      let vel = f.split("=")
      vel[1] = vel[1].split("..").map((v) => parseInt(v))
      return vel
    })
)

class Launcher {
  constructor(velocity) {
    this.velocity = velocity
    this.position = [0, 0]
  }

  step() {
    for (let i = 0; i < 2; i++) this.position[i] += this.velocity[i]

    if (this.velocity[0] > 0) this.velocity[0]--
    else if (this.velocity[0] < 0) this.velocity[0]++

    this.velocity[1]--
  }

  trace(target) {
    let maxY = this.position[1]
    while (this.position[1] > target.y[0]) {
      this.step()
      maxY = Math.max(maxY, this.position[1])

      if (
        this.position[0] >= target.x[0] &&
        this.position[0] <= target.x[1] &&
        this.position[1] >= target.y[0] &&
        this.position[1] <= target.y[1]
      )
        return maxY
    }
  }
}

let best = {
  maxY: Number.MIN_SAFE_INTEGER,
  start: null,
}

let suitable = 0

for (let x = 1; x < 2 * target.x[1]; x++) {
  for (let y = -Math.abs(target.y[0]); y < 2 * Math.abs(target.y[0]); y++) {
    const maxY = new Launcher([x, y]).trace(target)
    if (maxY !== undefined) {
      suitable++
      if (maxY > best.maxY)
        best = {
          maxY: maxY,
          start: [x, y],
        }
    }
  }
}

let partOne = best.maxY,
  partTwo = suitable

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
