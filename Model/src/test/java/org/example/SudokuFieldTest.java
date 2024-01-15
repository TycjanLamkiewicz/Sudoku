package org.example;

import org.example.exceptions.ValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SudokuFieldTest {
    ISudokuSolver solver;
    SudokuField sudokuField;
    SudokuField sudokuField2;


    @BeforeEach
    void init() {
        solver = new BacktrackingSudokuSolver();
        sudokuField = new SudokuField();
        sudokuField2 = new SudokuField();
    }

    @Test
    void equalsFalse() {
        assertFalse(sudokuField.equals(null));
        assertFalse(sudokuField.equals(solver));
    }

    @Test
    void equalsTrue() {
        assertTrue(sudokuField.equals(sudokuField));
    }

    @Test
    void toStringNotNull() {
        assertNotNull(sudokuField.toString());
    }

    @Test
    public void compareToEquals() {
        sudokuField.setValue(1);
        sudokuField2.setValue(1);
        assertEquals(sudokuField.compareTo(sudokuField2), 0);

        sudokuField.setValue(2);
        sudokuField2.setValue(1);
        assertEquals(sudokuField.compareTo(sudokuField2), 1);

        sudokuField.setValue(1);
        sudokuField2.setValue(2);
        assertEquals(sudokuField.compareTo(sudokuField2), -1);

    }

    @Test
    public void cloneTest() throws CloneNotSupportedException {
        sudokuField = new SudokuField(1);
        sudokuField2 = (SudokuField) sudokuField.clone();

        assertTrue(sudokuField.equals(sudokuField2));
        assertTrue(sudokuField2.equals(sudokuField));
    }

    @Test
    public void checkCompareToNPE() {
        sudokuField = new SudokuField(1);
        SudokuField sudokuField3 = null;

        assertThrows(ValueException.class, () -> sudokuField.compareTo(sudokuField3));
    }
}
