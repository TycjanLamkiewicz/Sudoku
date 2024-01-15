package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardTest {
    ISudokuSolver solver;
    SudokuBoard sudokuBoard;

    @BeforeEach
    void init() {
        solver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(solver);
    }

    @Test
    void getterSetter() {
        sudokuBoard.solveGame();
        sudokuBoard.set(0, 0, 1);
        assertEquals(sudokuBoard.get(0, 0), 1);
    }

    @Test
    void checkBoardTrue() {
        sudokuBoard.solveGame();
        assertTrue(sudokuBoard.checkBoard());
    }

    @Test
    void checkBoardFalse() {
        sudokuBoard.solveGame();
        sudokuBoard.set(0,0,1);
        sudokuBoard.set(0,1,1);
        sudokuBoard.set(1,0,1);
        sudokuBoard.set(1,1,1);
        assertFalse(sudokuBoard.checkBoard());
    }

    @Test
    void equalsTestFalse() {
        assertFalse(sudokuBoard.equals(solver));
        assertFalse(sudokuBoard.equals(null));
    }

    @Test
    void equalsTestThis() {
        assertTrue(sudokuBoard.equals(sudokuBoard));
    }

    @Test
    void toStringNotNull() {
        assertNotNull(sudokuBoard.toString());
    }
}
