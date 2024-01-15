package org.example;

import org.example.exceptions.CloneException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuRowTest {
    private SudokuRow sudokuRow;
    private SudokuRow sudokuRow2;

    SudokuField[] fields = new SudokuField[9];

    @Test
    public void CloneTest() throws CloneException {
        for (int i = 1; i < 10; i++) {
            fields = new SudokuField[i];
        }

        sudokuRow = new SudokuRow(fields);
        sudokuRow2 = (SudokuRow) sudokuRow.clone();

        assertTrue(sudokuRow.equals(sudokuRow2));
        assertTrue(sudokuRow2.equals(sudokuRow));
    }
}