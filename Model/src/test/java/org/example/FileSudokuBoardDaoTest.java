package org.example;

import org.example.exceptions.DaoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileSudokuBoardDaoTest {

    ISudokuSolver solver;
    SudokuBoard sudokuBoard;
    SudokuBoardDaoFactory sudokuBoardDaoFactory;
    IDao<SudokuBoard> fileSudokuBoardDao;

    @BeforeEach
    void init() {
        solver = new BacktrackingSudokuSolver();
        sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        sudokuBoard = new SudokuBoard(solver);
    }

    @Test
    public void writeNotNull() throws DaoException {
        fileSudokuBoardDao = sudokuBoardDaoFactory.getFileDao("test1");
        fileSudokuBoardDao.write(sudokuBoard);
        assertNotNull(sudokuBoard);
    }

    @Test
    public void readNotNull() throws DaoException {
        fileSudokuBoardDao = sudokuBoardDaoFactory.getFileDao("test1");
        fileSudokuBoardDao.write(sudokuBoard);
        fileSudokuBoardDao.read();
        assertNotNull(sudokuBoard);
    }

    @Test
    public void readException() {
        fileSudokuBoardDao = sudokuBoardDaoFactory.getFileDao("!?@$%");
        assertThrows(RuntimeException.class, () -> fileSudokuBoardDao.write(sudokuBoard));
    }

    @Test
    public void autoCloseableTest() {
        assertDoesNotThrow(() -> {
            try (FileSudokuBoardDao dao = new FileSudokuBoardDao("testFileName")) {
                assertTrue(dao instanceof AutoCloseable);
            }
        });
    }
}