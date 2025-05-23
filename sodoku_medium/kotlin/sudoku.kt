fun main() {
    val sudoku = mutableListOf<String>()

    sudoku.add("800000000")
    sudoku.add("003600000")
    sudoku.add("070090200")
    sudoku.add("050007000")
    sudoku.add("000045700")
    sudoku.add("000100030")
    sudoku.add("001000068")
    sudoku.add("008500010")
    sudoku.add("090000400")

    solveSudoku(sudoku).forEach { println(it) }
}

fun solveSudoku(sudoku: List<String>): List<String> {
    val array2D = Array(9) { Array(9) { 0 } }
    sudoku.forEachIndexed { i, s ->
        s.forEachIndexed { j, c ->
            array2D[i][j] = c.digitToInt()
        }
    }
    val sudokuResult = solveSudokuWithBacktracking(array2D)

    return if (sudokuResult != null) {
        makeResult(sudokuResult)
    } else {
        emptyList()
    }
}

fun solveSudokuWithBacktracking(board: Array<Array<Int>>): Array<Array<Int>>? {
    val emptyCell = findEmptyCell(board) ?: return board

    val (row, col) = emptyCell

    for (num in 1..9) {
        if (isValid(board, row, col, num)) {
            board[row][col] = num

            if (solveSudokuWithBacktracking(board) != null) {
                return board
            }

            board[row][col] = 0
        }
    }

    return null
}

fun findEmptyCell(board: Array<Array<Int>>): Pair<Int, Int>? {
    for (i in 0..8) {
        for (j in 0..8) {
            if (board[i][j] == 0) {
                return Pair(i, j)
            }
        }
    }
    return null
}

fun isValid(board: Array<Array<Int>>, row: Int, col: Int, num: Int): Boolean {
    for (j in 0..8) {
        if (board[row][j] == num) return false
    }

    for (i in 0..8) {
        if (board[i][col] == num) return false
    }

    val startRow = (row / 3) * 3
    val startCol = (col / 3) * 3
    for (i in startRow until startRow + 3) {
        for (j in startCol until startCol + 3) {
            if (board[i][j] == num) return false
        }
    }

    return true
}

fun makeResult(array: Array<Array<Int>>): List<String> {
    val result = mutableListOf<String>()
    for (i in 0..8) {
        var row = ""
        for (j in 0..8) {
            row += array[i][j].toString()
        }
        result.add(row)
    }
    return result
}