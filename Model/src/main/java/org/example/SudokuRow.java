package org.example;


import org.example.exceptions.CloneException;

public class SudokuRow extends SudokuFieldGroup {
    public SudokuRow(SudokuField[] sudokuFields) {
        super(sudokuFields);
    }

    @Override
    protected Object clone() throws CloneException {

        SudokuField[] fields = new SudokuField[9];

        return new SudokuRow(fields);
    }
}
