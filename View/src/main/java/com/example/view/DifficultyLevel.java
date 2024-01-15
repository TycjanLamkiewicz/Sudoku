package com.example.view;

import org.example.SudokuBoard;

import java.util.ResourceBundle;

public enum DifficultyLevel {
    EASY(30),
    MEDIUM(40),
    HARD(50),
    FILE(1),
    DB(1);

    private final int cellsToRemove;

    DifficultyLevel(int cellsToRemove) {
        this.cellsToRemove = cellsToRemove;
    }

    public int getCellsToRemove() {
        return cellsToRemove;
    }

    public static DifficultyLevel chooseLevel(String str, ResourceBundle bundle) {
        if (str.equals(bundle.getString("diffEasy"))) {
            return EASY;
        } else if (str.equals(bundle.getString("diffMedium"))) {
            return MEDIUM;
        } else {
            return HARD;
        }
    }
}
