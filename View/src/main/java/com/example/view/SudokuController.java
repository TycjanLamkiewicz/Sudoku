package com.example.view;

import javafx.beans.binding.Bindings;
import javafx.beans.property.adapter.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.util.StringConverter;
import org.example.*;
import javafx.scene.control.TextFormatter;
import org.example.exceptions.DaoException;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SudokuController implements Initializable {
    ISudokuSolver solver = new BacktrackingSudokuSolver();
    SudokuBoard sudokuBoard = new SudokuBoard(solver);
    DifficultyLevel difficultyLevel;

    SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
    IDao<SudokuBoard> fileSudokuBoardDao = sudokuBoardDaoFactory.getFileDao("file");
    IDao<SudokuBoard> dbSudokuBoardDao;
    IDao<SudokuBoard> dbToSudokuBoardDao;
    private ResourceBundle bundle = ResourceBundle.getBundle("lang");

    @FXML
    private GridPane sudokuBoardGrid;
    @FXML
    private TextField nameText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (HelloController.getMyDiff() == difficultyLevel.FILE) {
            try {
                sudokuBoard = fileSudokuBoardDao.read();
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        } else if (HelloController.getMyDiff() == difficultyLevel.DB) {
            dbSudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao(HelloController.getName());
            try {
                sudokuBoard = dbSudokuBoardDao.read();
            } catch (DaoException e) {
                throw new RuntimeException(e);
            }
        } else {
            sudokuBoard.solveGame();
            setDifficultyLevel(HelloController.getMyDiff(), sudokuBoard);
        }

        fillGrid();

        nameText.setText(HelloController.getName());
        nameText.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[a-zA-Z0-9]*")) {
                return change;
            } else {
                return null;
            }
        }));
    }

    private void fillGrid() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                TextField textField = new TextField();
                textField.setMinSize(50, 50);
                textField.setFont(Font.font(25));
                textField.setAlignment(Pos.CENTER);

                textField.setTextFormatter(new TextFormatter<>(change -> {
                    String newText = change.getControlNewText();
                    if (newText.length() <= 1 && newText.matches("[1-9\\s]*")) {
                        return change;
                    } else {
                        return null;
                    }
                }));

                bind(textField, sudokuBoard.getField(i,j));

                if (sudokuBoard.get(i, j) != 0) {
                    textField.setText(String.valueOf(sudokuBoard.get(i, j)));

                    if (sudokuBoard.isFilled(i, j)) {
                        textField.setDisable(true);
                    }
                }

                sudokuBoardGrid.add(textField, i, j);
            }
        }
    }

    public void bind(TextField textField, SudokuField sudokuField) {
        JavaBeanIntegerProperty fieldProp;
        try {
            fieldProp = JavaBeanIntegerPropertyBuilder
                    .create()
                    .bean(sudokuField)
                    .name("value")
                    .build();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        Bindings.bindBidirectional(textField.textProperty(), fieldProp, new StringConverter<Number>() {
            @Override
            public String toString(Number number) {
                if (!number.equals(0)) {
                    return String.valueOf(number);
                }
                return "";
            }

            @Override
            public Number fromString(String s) {
                if (!s.isBlank()) {
                    return Integer.parseInt(s);
                }
                return 0;
            }
        });
    }

    public void setDifficultyLevel(DifficultyLevel level, SudokuBoard board) {
        int cells = level.getCellsToRemove();

        while (cells > 0) {
            int x = (int) (Math.random() * 9);
            int y = (int) (Math.random() * 9);

            if (board.get(x,y) != 0) {
                board.set(x,y,0);
                board.setFilled(x,y,false);
                cells--;
            }
        }
    }

    @FXML
    public void onActionButtonSaveFile(ActionEvent actionEvent) throws IOException {
        //updateBoard();

        try {
            fileSudokuBoardDao.write(sudokuBoard);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onActionButtonSaveDb(ActionEvent actionEvent) throws IOException {
        if (nameText.getText() == null) {
            dbToSudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao("default");
        } else {
            dbToSudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao(nameText.getText());
        }
        try {
            dbToSudokuBoardDao.write(sudokuBoard);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onActionButtonMenu(ActionEvent actionEvent) throws IOException {
        FxmlStageSetup.buildStage("hello-view.fxml", bundle);
    }

    private void updateBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String fieldValue = ((TextField) sudokuBoardGrid.getChildren().get(i * 9 + j)).getText();
                if (!fieldValue.equals("")) {
                    if (!fieldValue.equals(" ")) {
                        sudokuBoard.set(i, j, Integer.parseInt(fieldValue));
                    } else {
                        sudokuBoard.set(i, j, 0);
                    }
                } else {
                    sudokuBoard.set(i, j, 0);
                }
            }
        }

    }
}
