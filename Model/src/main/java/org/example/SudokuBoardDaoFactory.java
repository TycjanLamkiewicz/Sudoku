package org.example;

public class SudokuBoardDaoFactory {
    public IDao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public IDao<SudokuBoard> getJdbcDao(String fileName) {
        return new JdbcSudokuBoardDao(fileName);
    }
}
