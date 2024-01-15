package org.example;

import org.example.exceptions.CloneException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoxTest {
    private SudokuBox sudokuBox;
    private SudokuBox sudokuBox2;

    SudokuField[] fields = new SudokuField[9];

    @Test
    public void CloneTest() throws CloneException {
        for (int i = 1; i < 10; i++) {
            fields = new SudokuField[i];
        }

        sudokuBox = new SudokuBox(fields);
        sudokuBox2 = (SudokuBox) sudokuBox.clone();

        assertTrue(sudokuBox.equals(sudokuBox2));
        assertTrue(sudokuBox2.equals(sudokuBox));
    }
}