package org.example;

import org.example.exceptions.CloneException;

import java.util.ResourceBundle;

public class SudokuColumn extends SudokuFieldGroup {
    public SudokuColumn(SudokuField[] sudokuFields) {
        super(sudokuFields);
    }

    @Override
    protected Object clone() throws CloneException {
        try {
            SudokuField[] fields = new SudokuField[9];

            return new SudokuColumn(fields);
        } catch (Exception e) {
            ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessages");
            String message = bundle.getString("org.example.CloneException");
            throw new CloneException(message);
        }
    }
}
