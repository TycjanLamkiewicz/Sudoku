package org.example;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.example.exceptions.CloneException;

import java.io.Serializable;
import java.util.ResourceBundle;

public class SudokuBoard implements Serializable, Cloneable {
    private SudokuField[][] board = new SudokuField[9][9];
    private ISudokuSolver solver;

    public SudokuBoard(ISudokuSolver solver) {
        this.solver = solver;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = new SudokuField();
            }
        }
    }

    public int get(int x, int y) {
        return board[x][y].getValue();
    }

    public void set(int x, int y, int value) {
        board[x][y].setValue(value);
    }

    public boolean checkBoard() {
        System.out.println("row: " + checkRow() + ", col: " + checkColumn() + ", box: " + checkBox());
        return checkRow() || checkColumn() || checkBox();
    }

    public SudokuField getField(int x, int y) {
        return board[x][y];
    }

    private boolean checkRow() {
        for (int i = 0; i < 9; i++) {
            if (!getRow(i).verify()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkColumn() {
        for (int j = 0; j < 9; j++) {
            if (!getColumn(j).verify()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkBox() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!getBox(i,j).verify()) {
                    return false;
                }
            }
        }
        return true;
    }

    public SudokuRow getRow(int y) {
        SudokuField[] fields = new SudokuField[9];

        for (int i = 0; i < 9; i++) {
            fields[i] = board[y][i];
        }

        return new SudokuRow(fields);
    }

    public SudokuColumn getColumn(int x) {
        SudokuField[] fields = new SudokuField[9];

        for (int i = 0; i < 9; i++) {
            fields[i] = board[i][x];
        }

        return new SudokuColumn(fields);
    }

    public SudokuBox getBox(int x, int y) {
        SudokuField[] fields = new SudokuField[9];
        int index = 0;
        int boxStartRow = x - x % 3;
        int boxStartColumn = y - y % 3;

        for (int i = boxStartRow; i < boxStartRow + 3; i++) {
            for (int j = boxStartColumn; j < boxStartColumn + 3; j++) {
                fields[index++] = board[i][j];
            }
        }

        return new SudokuBox(fields);
    }

    public void solveGame() {
        solver.solve(this);
    }

    public boolean isFilled(int x, int y) {
        return board[x][y].isSet();
    }

    public void setFilled(int x, int y, boolean value) {
        board[x][y].setSet(value);
    }

    /*public void printBoard() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("-----------");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print("|");
                }
                System.out.print(board[i][j].getFieldValue());
            }
            System.out.println();
        }
    }*/

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("board",board)
                .append("solver",solver)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SudokuBoard that = (SudokuBoard) o;

        return new EqualsBuilder().append(board, that.board).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(board).toHashCode();
    }

    @Override
    protected Object clone() throws CloneException {
        try {
            ISudokuSolver solver = new BacktrackingSudokuSolver();
            SudokuBoard sudokuBoard = new SudokuBoard(solver);

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sudokuBoard.set(i, j, get(i, j));
                }
            }

            return sudokuBoard;
        } catch (Exception e) {
            ResourceBundle bundle = ResourceBundle.getBundle("ExceptionMessages");
            String message = bundle.getString("org.example.CloneException");
            throw new CloneException(message);
        }
    }
}
