package main

import (
	"fmt"
	"strings"
)

func main() {
	sudoku := []string{
		"800000000",
		"003600000",
		"070090200",
		"050007000",
		"000045700",
		"000100030",
		"001000068",
		"008500010",
		"090000400",
	}

	result := solveSudoku(sudoku)
	for _, row := range result {
		fmt.Println(row)
	}
}

func solveSudoku(sudoku []string) []string {
	// 2D tömb létrehozása
	array2D := make([][]int, 9)
	for i := range array2D {
		array2D[i] = make([]int, 9)
	}

	// Bemeneti stringek konvertálása számokká
	for i, s := range sudoku {
		for j, c := range s {
			array2D[i][j] = int(c - '0')
		}
	}

	sudokuResult := solveSudokuWithBacktracking(array2D)
	if sudokuResult != nil {
		return makeResult(sudokuResult)
	} else {
		return []string{}
	}
}

func solveSudokuWithBacktracking(board [][]int) [][]int {
	row, col, found := findEmptyCell(board)
	if !found {
		return board // Nincs több üres cella, kész vagyunk
	}

	for num := 1; num <= 9; num++ {
		if isValid(board, row, col, num) {
			board[row][col] = num

			if result := solveSudokuWithBacktracking(board); result != nil {
				return result
			}

			board[row][col] = 0 // Visszalépés
		}
	}

	return nil
}

func findEmptyCell(board [][]int) (int, int, bool) {
	for i := 0; i < 9; i++ {
		for j := 0; j < 9; j++ {
			if board[i][j] == 0 {
				return i, j, true
			}
		}
	}
	return 0, 0, false // Nem találtunk üres cellát
}

func isValid(board [][]int, row, col, num int) bool {
	// Sor ellenőrzése
	for j := 0; j < 9; j++ {
		if board[row][j] == num {
			return false
		}
	}

	// Oszlop ellenőrzése
	for i := 0; i < 9; i++ {
		if board[i][col] == num {
			return false
		}
	}

	// 3x3-as blokk ellenőrzése
	startRow := (row / 3) * 3
	startCol := (col / 3) * 3

	for i := startRow; i < startRow+3; i++ {
		for j := startCol; j < startCol+3; j++ {
			if board[i][j] == num {
				return false
			}
		}
	}

	return true
}

func makeResult(array [][]int) []string {
	result := make([]string, 9)

	for i := 0; i < 9; i++ {
		var builder strings.Builder
		for j := 0; j < 9; j++ {
			builder.WriteRune(rune(array[i][j] + '0'))
		}
		result[i] = builder.String()
	}

	return result
}
