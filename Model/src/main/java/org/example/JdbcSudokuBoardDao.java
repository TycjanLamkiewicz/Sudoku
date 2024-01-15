package org.example;

import org.example.exceptions.DaoException;
import org.example.exceptions.DatabaseException;
import org.example.exceptions.ExistenceException;

import java.sql.*;

public class JdbcSudokuBoardDao implements IDao<SudokuBoard>, AutoCloseable {
    private String fileName;
    private Connection connection;

    public JdbcSudokuBoardDao(String fileName) {
        this.fileName = fileName;

        try {
            Class.forName("org.h2.Driver");

            Connection connection = DriverManager.getConnection("jdbc:h2:~/sudoku_db", "sa", "");

            Statement statement = connection.createStatement();

            String createTableSql = "CREATE TABLE IF NOT EXISTS board ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "name VARCHAR(255) NOT NULL"
                    + ")";

            statement.execute(createTableSql);

            String createFieldsTableSql = "CREATE TABLE IF NOT EXISTS fields ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "field_x TINYINT NOT NULL,"
                    + "field_y TINYINT NOT NULL,"
                    + "field_value TINYINT NOT NULL,"
                    + "is_editable BOOLEAN NOT NULL,"
                    + "board_id INT NOT NULL"
                    + ")";

            statement.execute(createFieldsTableSql);

            statement.close();
            connection.close();
        } catch (ExistenceException | DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SudokuBoard read() throws DaoException {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/sudoku_db", "sa", "");
             Statement statement = connection.createStatement()) {

            connection.setAutoCommit(false);

            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM fields "
                    + "JOIN board ON fields.board_id = board.id "
                    + "WHERE board.name = '" + fileName + "'")) {

                SudokuBoard sudokuBoard = new SudokuBoard(new BacktrackingSudokuSolver());

                while (resultSet.next()) {
                    int fieldX = resultSet.getInt("field_x");
                    int fieldY = resultSet.getInt("field_y");
                    int fieldValue = resultSet.getInt("field_value");
                    boolean isEditable = resultSet.getBoolean("is_editable");

                    sudokuBoard.set(fieldX, fieldY, fieldValue);
                    sudokuBoard.setFilled(fieldX, fieldY, isEditable);
                }

                connection.commit();

                return sudokuBoard;
            } catch (DatabaseException ex) {
                try {
                    connection.rollback();
                } catch (DatabaseException e) {
                    throw new DaoException(e);
                }
                throw new DaoException(ex);
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
            throw new DaoException(ex);
        }
    }

    @Override
    public void write(SudokuBoard obj) throws DaoException {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/sudoku_db", "sa", "");) {
            connection.setAutoCommit(false);

            String checkFileNameQuery = "SELECT id FROM board WHERE name = ?";
            try (PreparedStatement checkFileNameStatement = connection.prepareStatement(checkFileNameQuery)) {
                checkFileNameStatement.setString(1, fileName);
                ResultSet resultSet = checkFileNameStatement.executeQuery();

                int boardId = -1;
                if (resultSet.next()) {
                    boardId = resultSet.getInt("id");
                } else {
                    String insertFileNameQuery = "INSERT INTO board (name) VALUES (?)";
                    try (PreparedStatement insertFileNameStatement = connection.prepareStatement(insertFileNameQuery,
                            Statement.RETURN_GENERATED_KEYS)) {
                        insertFileNameStatement.setString(1, fileName);
                        insertFileNameStatement.executeUpdate();

                        ResultSet generatedKeys = insertFileNameStatement.getGeneratedKeys();
                        if (generatedKeys.next()) {
                            boardId = generatedKeys.getInt(1);
                        }
                    }
                }

                String insertOrUpdateQuery = "MERGE INTO fields (field_x, field_y, field_value, is_editable, board_id) "
                        + "KEY (field_x, field_y, board_id) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement mergeStatement = connection.prepareStatement(insertOrUpdateQuery)) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            int fieldValue = obj.get(i, j);
                            boolean isEditable = obj.getField(i, j).isSet();

                            mergeStatement.setInt(1, i);
                            mergeStatement.setInt(2, j);
                            mergeStatement.setInt(3, fieldValue);
                            mergeStatement.setBoolean(4, isEditable);
                            mergeStatement.setInt(5, boardId);

                            mergeStatement.executeUpdate();
                        }
                    }
                } catch (DatabaseException ex) {
                    try {
                        connection.rollback();
                    } catch (DatabaseException e) {
                        throw new DaoException(e);
                    }
                    throw new DaoException(ex);
                }
            }

            connection.commit();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
