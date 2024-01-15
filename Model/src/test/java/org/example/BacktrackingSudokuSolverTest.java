package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BacktrackingSudokuSolverTest {
    ISudokuSolver solver;
    SudokuBoard sudokuBoard;

    @BeforeEach
    void init() {
        solver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(solver);
    }

    @Test
    void solve() {
        sudokuBoard.solveGame();

        for (int i = 0; i < 9; i++) {
            assertTrue(areUnique(getRow(sudokuBoard, i)));
        }

        for (int i = 0; i < 9; i++) {
            assertTrue(areUnique(getColumn(sudokuBoard, i)));
        }

        for (int i = 0; i < 9; i += 3) {
            for (int j = 0; j < 9; j += 3) {
                assertTrue(areUnique(getBox(sudokuBoard, i, j)));
            }
        }
    }

    private boolean areUnique(int[] array) {
        boolean[] seen = new boolean[10];
        for (int num : array) {
            if (seen[num]) {
                return false;
            }
            seen[num] = true;
        }
        return true;
    }

    private int[] getColumn(SudokuBoard board, int col) {
        int[] colArray = new int[9];
        for (int i = 0; i < 9; i++) {
            colArray[i] = board.get(i, col);
        }
        return colArray;
    }

    private int[] getRow(SudokuBoard board, int row) {
        int[] rowArray = new int[9];
        for (int i = 0; i < 9; i++) {
            rowArray[i] = board.get(row, i);
        }
        return rowArray;
    }


    private int[] getBox(SudokuBoard board, int row, int col) {
        int[] boxArray = new int[9];
        int a = 0;
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                boxArray[a++] = board.get(i, j);
            }
        }
        return boxArray;
    }

    @Test
    void differentBoards() {
        SudokuBoard sudokuBoard2 = new SudokuBoard(solver);

        sudokuBoard.solveGame();
        sudokuBoard2.solveGame();

        assertNotEquals(sudokuBoard.hashCode(), sudokuBoard2.hashCode());
        assertFalse(sudokuBoard.equals(sudokuBoard2));
    }
}