import java.util.ArrayList;
import java.util.List;

public class SudokuSolver {
    public static void main(String[] args) {
        List<String> sudoku = new ArrayList<>();
        sudoku.add("800000000");
        sudoku.add("003600000");
        sudoku.add("070090200");
        sudoku.add("050007000");
        sudoku.add("000045700");
        sudoku.add("000100030");
        sudoku.add("001000068");
        sudoku.add("008500010");
        sudoku.add("090000400");

        solveSudoku(sudoku).forEach(System.out::println);
    }

    public static List<String> solveSudoku(List<String> sudoku) {
        int[][] array2D = new int[9][9];

        for (int i = 0; i < sudoku.size(); i++) {
            String s = sudoku.get(i);
            for (int j = 0; j < s.length(); j++) {
                array2D[i][j] = Character.getNumericValue(s.charAt(j));
            }
        }

        int[][] sudokuResult = solveSudokuWithBacktracking(array2D);

        if (sudokuResult != null) {
            return makeResult(sudokuResult);
        } else {
            return new ArrayList<>();
        }
    }

    public static int[][] solveSudokuWithBacktracking(int[][] board) {
        int[] emptyCell = findEmptyCell(board);
        if (emptyCell == null) return board;

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int num = 1; num <= 9; num++) {
            if (isValid(board, row, col, num)) {
                board[row][col] = num;

                if (solveSudokuWithBacktracking(board) != null) {
                    return board;
                }

                board[row][col] = 0;
            }
        }

        return null;
    }

    public static int[] findEmptyCell(int[][] board) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }

    public static boolean isValid(int[][] board, int row, int col, int num) {
        for (int j = 0; j < 9; j++) {
            if (board[row][j] == num) return false;
        }

        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) return false;
        }

        int startRow = (row / 3) * 3;
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) return false;
            }
        }

        return true;
    }

    public static List<String> makeResult(int[][] array) {
        List<String> result = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < 9; j++) {
                row.append(array[i][j]);
            }
            result.add(row.toString());
        }

        return result;
    }
}
