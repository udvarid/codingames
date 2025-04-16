def main():
    sudoku = [
        "800000000",
        "003600000",
        "070090200",
        "050007000",
        "000045700",
        "000100030",
        "001000068",
        "008500010",
        "090000400"
    ]

    for row in solve_sudoku(sudoku):
        print(row)

def solve_sudoku(sudoku):
    # Konvertáljuk a bemeneti sztring listát 2D tömbbé
    array_2d = [[0 for _ in range(9)] for _ in range(9)]

    for i, s in enumerate(sudoku):
        for j, c in enumerate(s):
            array_2d[i][j] = int(c)

    sudoku_result = solve_sudoku_with_backtracking(array_2d)

    if sudoku_result:
        return make_result(sudoku_result)
    else:
        return []

def solve_sudoku_with_backtracking(board):
    empty_cell = find_empty_cell(board)

    if not empty_cell:
        return board  # Készen vagyunk, nincs több üres cella

    row, col = empty_cell

    for num in range(1, 10):
        if is_valid(board, row, col, num):
            board[row][col] = num

            if solve_sudoku_with_backtracking(board):
                return board

            board[row][col] = 0  # Visszalépés

    return None

def find_empty_cell(board):
    for i in range(9):
        for j in range(9):
            if board[i][j] == 0:
                return (i, j)
    return None  # Nincs több üres cella

def is_valid(board, row, col, num):
    # Ellenőrizzük a sort
    for j in range(9):
        if board[row][j] == num:
            return False

    # Ellenőrizzük az oszlopot
    for i in range(9):
        if board[i][col] == num:
            return False

    # Ellenőrizzük a 3x3-as blokkot
    start_row = (row // 3) * 3
    start_col = (col // 3) * 3

    for i in range(start_row, start_row + 3):
        for j in range(start_col, start_col + 3):
            if board[i][j] == num:
                return False

    return True

def make_result(array):
    result = []
    for i in range(9):
        row = ''.join(str(array[i][j]) for j in range(9))
        result.append(row)
    return result

if __name__ == "__main__":
    main()
