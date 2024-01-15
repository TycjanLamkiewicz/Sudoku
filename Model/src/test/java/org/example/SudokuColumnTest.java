package org.example;

import org.example.exceptions.CloneException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuColumnTest {
    private SudokuColumn sudokuColumn;
    private SudokuColumn sudokuColumn2;

    SudokuField[] fields = new SudokuField[9];

    @Test
    public void CloneTest() throws CloneException {
        for (int i = 1; i < 10; i++) {
            fields = new SudokuField[i];
        }

        sudokuColumn = new SudokuColumn(fields);
        sudokuColumn2 = (SudokuColumn) sudokuColumn.clone();

        assertTrue(sudokuColumn.equals(sudokuColumn2));
        assertTrue(sudokuColumn2.equals(sudokuColumn));
    }
}