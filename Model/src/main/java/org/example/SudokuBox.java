package org.example;

import org.example.exceptions.CloneException;

import java.util.ResourceBundle;

public class SudokuBox extends SudokuFieldGroup {
    public SudokuBox(SudokuField[] sudokuFields) {
        super(sudokuFields);
    }

    @Override
    protected Object clone() throws CloneException {

        try {
            SudokuField[] fields = new SudokuField[9];

            return new SudokuBox(fields);
        } catch (Exception e) {
            ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessages");
            String message = bundle.getString("org.example.CloneException");
            throw new CloneException(message);
        }
    }
}
