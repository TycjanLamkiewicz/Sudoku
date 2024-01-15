package org.example;

import org.example.exceptions.DaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JdbcSudokuBoardDaoTest {
    ISudokuSolver solver;
    SudokuBoard sudokuBoard;
    SudokuBoardDaoFactory sudokuBoardDaoFactory;
    IDao<SudokuBoard> sudokuBoardDao;

    @BeforeEach
    void init() {
        solver = new BacktrackingSudokuSolver();
        sudokuBoard = new SudokuBoard(solver);
        sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
    }

    @AfterEach
    void cleanAll() throws Exception {
        if (sudokuBoardDao != null) {
            sudokuBoardDao.close();
        }
    }

    @Test
    void testWriteAndRead() throws DaoException {
        sudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao("testWriteAndRead");
        sudokuBoard.solveGame();

        assertDoesNotThrow(() -> sudokuBoardDao.write(sudokuBoard));
        SudokuBoard sudokuBoardRead = sudokuBoardDao.read();

        assertNotSame(sudokuBoard, sudokuBoardRead);
        assertEquals(sudokuBoard, sudokuBoardRead);
    }

    @Test
    void testTransactionAndResources() {
        sudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao("testTransactionAndResources");
        sudokuBoard.solveGame();

        assertDoesNotThrow(() -> {
            assertDoesNotThrow(() -> {
                try (IDao<SudokuBoard> dao = sudokuBoardDaoFactory.getJdbcDao("testTransactionAndResources")) {
                    dao.write(sudokuBoard);
                }
            });
        });
    }

    @Test
    void testInvalidWrite() {
        sudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao("testInvalidWrite");
        sudokuBoard.solveGame();

        assertThrows(NullPointerException.class, () -> {
            try (IDao<SudokuBoard> dao = sudokuBoardDaoFactory.getJdbcDao("testInvalidWrite")) {
                dao.write(null);
            }
        });
    }

    @Test
    void testSuccessfulRead() throws DaoException {
        sudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao("testWriteAndRead");
        sudokuBoard.solveGame();

        assertDoesNotThrow(() -> sudokuBoardDao.write(sudokuBoard));
        SudokuBoard sudokuBoardRead = sudokuBoardDao.read();
    }
}