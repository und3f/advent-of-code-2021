#!/usr/bin/env node

const fs = require("fs")
const path = require("path")

let contents = fs
  .readFileSync(path.basename(__filename, ".js") + ".txt", "utf8")
  .trim()
let data = contents.split("\n\n")

const input = data[0].split(",").map((v) => parseInt(v))

let boards = new Array(data.length - 1)
for (let i = 0; i < data.length - 1; i++) {
  boards[i] = data[i + 1].split(/\n/).map((line) =>
    line
      .trim()
      .split(/\s+/)
      .map((v) => parseInt(v))
  )
}

const boardsMarks = new Array(boards.length)
  .fill(null)
  .map((board) =>
    new Array(boards[0].length)
      .fill(null)
      .map((row) => new Array(boards[0][0].length).fill(false))
  )

function isBoardComplete(boardMarks, r, c) {
  let isComplete = true
  for (let i = 0; i < boardMarks[r].length; i++) {
    if (boardMarks[r][i] == false) {
      isComplete = false
      break
    }
  }
  if (isComplete) return true

  isComplete = true
  for (let i = 0; i < boardMarks.length; i++) {
    if (boardMarks[i][c] == false) {
      isComplete = false
      break
    }
  }
  return isComplete
}

function calcScore(board, boardMarks) {
  let score = 0
  for (let r = 0; r < board.length; r++) {
    for (let c = 0; c < board.length; c++) {
      if (!boardMarks[r][c]) score += board[r][c]
    }
  }
  return score
}

function markBoards(number, boards, boardsMarks) {
  let completeBoard = []
  for (let i = 0; i < boards.length; i++) {
    for (let r = 0; r < boards[i].length; r++) {
      for (let c = 0; c < boards[i][r].length; c++) {
        if (boards[i][r][c] == number) {
          boardsMarks[i][r][c] = true
          if (isBoardComplete(boardsMarks[i], r, c)) {
            completeBoard.push(i)
          }
        }
      }
    }
  }

  return completeBoard
}

let score, lastBoardScore
let wonBoards = new Array(boards.length).fill(false)

numbersLoop: for (const number of input) {
  completedBoards = markBoards(number, boards, boardsMarks)
  for (const wonBoard of completedBoards) {
    if (score === undefined)
      score = number * calcScore(boards[wonBoard], boardsMarks[wonBoard])

    wonBoards[wonBoard] = true

    isLast = wonBoards.reduce((a, v) => a && v, true)
    if (isLast) {
      lastBoardScore =
        number * calcScore(boards[wonBoard], boardsMarks[wonBoard])
      break numbersLoop
    }
  }
}

let partOne = score,
  partTwo = lastBoardScore

console.log("Part One:", partOne)
console.log("Part Two:", partTwo)
