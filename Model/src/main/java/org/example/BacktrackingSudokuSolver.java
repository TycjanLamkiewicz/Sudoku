package org.example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BacktrackingSudokuSolver implements ISudokuSolver, Serializable {
    public void solve(SudokuBoard board) {
        List<Integer> numbers = Arrays.asList(new Integer[9]);

        for (int num = 1; num <= 9; num++) {
            numbers.set(num - 1, num);
        }

        Collections.shuffle(numbers);

        int[] emptyCell = emptyCell(board);
        int row = emptyCell[0];
        int col = emptyCell[1];

        if (row == -1 && col == -1) {
            return;
        }

        for (int numTry : numbers) {
            if (isValid(board, numTry, row, col)) {
                board.set(row, col, numTry);

                solve(board);

                if (isBoardFilled(board)) {
                    return;
                }
            }
        }
        board.set(row, col, 0);
    }

    private boolean usedInRow(SudokuBoard board, int number, int row) {
        for (int i = 0; i < 9; i++) {
            if (board.get(row, i) == number) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInColumn(SudokuBoard board, int number, int column) {
        for (int i = 0; i < 9; i++) {
            if (board.get(i, column) == number) {
                return true;
            }
        }
        return false;
    }

    private boolean usedInBox(SudokuBoard board, int number, int row, int column) {
        int boxStartRow = row - row % 3;
        int boxStartColumn = column - column % 3;

        for (int i = boxStartRow; i < boxStartRow + 3; i++) {
            for (int j = boxStartColumn; j < boxStartColumn + 3; j++) {
                if (board.get(i, j) == number) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValid(SudokuBoard board, int number, int row, int column) {
        return !usedInRow(board, number, row)
                && !usedInColumn(board, number, column)
                && !usedInBox(board, number, row, column);
    }

    private int[] emptyCell(SudokuBoard board) {
        int[] coord = new int[2];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.get(i, j) == 0) {
                    coord[0] = i;
                    coord[1] = j;
                    return coord;
                }
            }
        }

        coord[0] = -1;
        coord[1] = -1;
        return coord;
    }

    private boolean isBoardFilled(SudokuBoard board) {
        int[] emptyCell = emptyCell(board);
        int row = emptyCell[0];
        int col = emptyCell[1];

        if (row == -1 && col == -1) {
            return true;
        } else {
            return false;
        }
    }
}
