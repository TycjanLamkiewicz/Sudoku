package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("- Sudoku -");

        ISudokuSolver solver = new BacktrackingSudokuSolver();
        SudokuBoard sudokuBoard = new SudokuBoard(solver);
        sudokuBoard.solveGame();
        //sudokuBoard.printBoard();
        logger.info(sudokuBoard.toString());

        //        SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        //        IDao<SudokuBoard> fileSudokuBoardDao;
        //        fileSudokuBoardDao = sudokuBoardDaoFactory.getFileDao("Board1");
        //        fileSudokuBoardDao.write(sudokuBoard);
        //        fileSudokuBoardDao.read();

        //SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
        //IDao<SudokuBoard> sudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao("test2");

        /*try {
            // Establish connection
            Connection connection = DriverManager.getConnection("jdbc:h2:~/sudoku_db", "sa", "");

            // Create statement
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT f.id, f.field_x, f.field_y, f.field_value, f.is_editable, b.name "
                            + "FROM fields f "
                            + "JOIN board b ON f.board_id = b.id "
                            + "WHERE b.name = ?"
            );

            statement.setString(1, "test1");

            // Execute query
            ResultSet resultSet = statement.executeQuery();

            // Process the results
            while (resultSet.next()) {
                int fieldX = resultSet.getInt("field_x");
                int fieldY = resultSet.getInt("field_y");
                int fieldValue = resultSet.getInt("field_value");
                boolean isEditable = resultSet.getBoolean("is_editable");
                String boardName = resultSet.getString("name");

                // Process retrieved data
                logger.info("X: " + fieldX
                        + ", Y: " + fieldY
                        + ", Value: " + fieldValue
                        + ", Editable: " + isEditable
                        + ", Board Name: " + boardName);
            }

            // Close connections
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        /*try {
            // Establish connection
            Connection connection = DriverManager.getConnection("jdbc:h2:~/sudoku_db", "sa", "");

            // Create statement
            Statement statement = connection.createStatement();

            // Execute query for board table
            ResultSet boardResultSet = statement.executeQuery("SELECT * FROM board");

            // Process the results for board table
            while (boardResultSet.next()) {
                int boardId = boardResultSet.getInt("id");
                String boardName = boardResultSet.getString("name");

                // Process retrieved board data
                System.out.println("Board ID: " + boardId + ", Board Name: " + boardName);
            }

            // Close board result set and statement
            boardResultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/


        /*try {
            // Establish connection
            Connection connection = DriverManager.getConnection("jdbc:h2:~/sudoku_db", "sa", "");

            // Create statement
            Statement statement = connection.createStatement();

            // Execute query for fields table
            ResultSet fieldsResultSet = statement.executeQuery("SELECT * FROM fields");

            // Process the results for fields table
            while (fieldsResultSet.next()) {
                int fieldId = fieldsResultSet.getInt("id");
                int fieldX = fieldsResultSet.getInt("field_x");
                int fieldY = fieldsResultSet.getInt("field_y");
                int fieldValue = fieldsResultSet.getInt("field_value");
                boolean isEditable = fieldsResultSet.getBoolean("is_editable");
                int boardId = fieldsResultSet.getInt("board_id");

                // Process retrieved fields data
                System.out.println("Field ID: " + fieldId +
                        ", X: " + fieldX +
                        ", Y: " + fieldY +
                        ", Value: " + fieldValue +
                        ", Editable: " + isEditable +
                        ", Board ID: " + boardId);
            }

            // Close fields result set and statement
            fieldsResultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        /*String tableName = "FIELDS";
        String dropTableSQL = "DROP TABLE " + tableName;

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/sudoku_db", "sa", "");
             Statement statement = connection.createStatement()) {

            // Execute the SQL command to drop the table
            statement.executeUpdate(dropTableSQL);
            System.out.println("Table " + tableName + " dropped successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }*/

    }
}
