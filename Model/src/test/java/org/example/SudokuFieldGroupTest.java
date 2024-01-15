package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SudokuFieldGroupTest {
    SudokuFieldGroup sudokuRow1;
    SudokuFieldGroup sudokuRow2;
    SudokuFieldGroup sudokuColumn;

    @BeforeEach
    void init() {
        SudokuField[] fields = new SudokuField[9];

        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField(i);
        }

        sudokuRow1 = new SudokuRow(fields);
        sudokuRow2 = new SudokuRow(fields);
        sudokuColumn = new SudokuColumn(fields);
    }

    @Test
    void equalsTrue() {
        assertTrue(sudokuRow1.equals(sudokuRow2));
        assertTrue(sudokuRow1.equals(sudokuRow1));
    }

    @Test
    void equalsFalse() {
        assertFalse(sudokuRow1.equals(sudokuColumn));
        assertFalse(sudokuRow1.equals(null));
    }

    @Test
    void hashCodeTest() {
        assertTrue(sudokuRow1.equals(sudokuRow2));
        assertEquals(sudokuRow1.hashCode(), sudokuRow2.hashCode());
    }

    @Test
    void toStringNotNull() {
        assertNotNull(sudokuColumn.toString());
        assertNotNull(sudokuRow1.toString());
        assertNotNull(sudokuRow2.toString());
    }
}
