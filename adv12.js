#!/usr/bin/env node

"use strict"

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
const data = contents.split("\n").map((line) => line.split("-"))

class G {
  constructor(relations) {
    this.names = relations
      .reduce((a, v) => a.concat(...v), [])
      .filter((v, i, a) => a.indexOf(v) === i)
    this.bigCaves = this.names.map((a) => a[0].toUpperCase() === a[0])

    this.edges = this.names.map(() => [])
    for (const [name1, name2] of relations) {
      const v1 = this.nameIndex(name1)
      const v2 = this.nameIndex(name2)
      this.edges[v1].push(v2)
      this.edges[v2].push(v1)
    }

    this.start = this.nameIndex("start")
    this.end = this.nameIndex("end")
  }

  nameIndex(name) {
    return this.names.indexOf(name)
  }

  dfs(path, paths) {
    const v = path[path.length - 1]

    if (this.end === v) {
      paths.push(path)
      return
    }

    for (const w of this.edges[v]) {
      if (!(this.bigCaves[w] || path.indexOf(w) === -1)) continue

      this.dfs(path.concat(w), paths)
    }
  }

  dfsComplex(path, paths, state) {
    const v = path[path.length - 1]
    // console.log(path, state)

    if (this.end === v) {
      paths.push(path)
      return
    }

    for (const w of this.edges[v]) {
      let thisState = Object.assign({}, state)
      if (w === this.start) continue

      if (!this.bigCaves[w]) {
        let firstVisit = path.indexOf(w) === -1
        if (!firstVisit) {
          if (thisState.doubleSmallCave === true) continue
          thisState.doubleSmallCave = true
        }
      }

      this.dfsComplex(path.concat(w), paths, thisState)
    }
  }

  findPaths() {
    const paths = []

    this.dfs([this.start], paths, (w, path) => path.indexOf(w) === -1)
    return paths
  }

  findPaths2() {
    const paths = []

    this.dfsComplex([this.start], paths, {})
    return paths
  }

  printPaths(paths) {
    for (const path of paths)
      console.log(path.map((v) => this.names[v]).join(","))
  }
}

const g = new G(data)

let partOne = g.findPaths().length,
  partTwo = g.findPaths2().length

// g.printPaths(g.findPaths2())

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
