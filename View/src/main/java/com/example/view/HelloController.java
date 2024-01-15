package com.example.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.exceptions.DaoException;
import org.example.IDao;
import org.example.SudokuBoard;
import org.example.SudokuBoardDaoFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class HelloController implements Initializable{
    ResourceBundle bundle = ResourceBundle.getBundle("lang");

    ResourceBundle authors = ResourceBundle.getBundle("com.example.view.Authors");

    @FXML
    private ComboBox<String> diffComboBox;

    @FXML
    private ComboBox<String> langComboBox;

    @FXML
    private ComboBox<String> nameComboBox;

    @FXML
    private Label auth1Label;

    @FXML
    private Label auth2Label;

    private static DifficultyLevel myDiff;
    private static String name;

    private String language;

    Connection connection;

    SudokuBoardDaoFactory sudokuBoardDaoFactory = new SudokuBoardDaoFactory();
    IDao<SudokuBoard> fileSudokuBoardDao = sudokuBoardDaoFactory.getFileDao("file");
    IDao<SudokuBoard> dbSudokuBoardDao = sudokuBoardDaoFactory.getJdbcDao("");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        diffComboBox.getItems().addAll(
            bundle.getString("diffEasy"),
            bundle.getString("diffMedium"),
            bundle.getString("diffHard")
        );
        diffComboBox.getSelectionModel().selectFirst();

        langComboBox.getItems().addAll(
            bundle.getString("langPl"),
            bundle.getString("langEng")
        );
        langComboBox.getSelectionModel().selectFirst();

        auth1Label.setText(authors.getString("1"));
        auth2Label.setText(authors.getString("2"));

        try {
            nameComboBox.getItems().addAll(getNames());
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        nameComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void onActionButtonStartGame(ActionEvent actionEvent) throws IOException {
        this.myDiff = DifficultyLevel.chooseLevel(diffComboBox.getValue(), bundle);
        FxmlStageSetup.buildStage("sudoku-view.fxml", bundle);
    }

    @FXML
    public void onActionButtonLanguage(ActionEvent actionEvent) throws IOException {
        this.language = langComboBox.getValue();

        ResourceBundle newBundle;
        if (language.equals(bundle.getString("langEng"))) {
            Locale.setDefault(new Locale("en"));
            newBundle = ResourceBundle.getBundle("lang_en");
        } else {
            Locale.setDefault(new Locale("pl"));
            newBundle = ResourceBundle.getBundle("lang_pl");
        }

        FxmlStageSetup.reloadBundle(newBundle, "hello-view.fxml");
    }

    @FXML
    public void onActionButtonLoadFile(ActionEvent actionEvent) throws IOException {
        try {
            this.myDiff = DifficultyLevel.FILE;
            FxmlStageSetup.buildStage("sudoku-view.fxml", bundle);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onActionButtonLoadDb(ActionEvent actionEvent) throws IOException {
        try {
            this.myDiff = DifficultyLevel.DB;
            this.name = nameComboBox.getValue();
            FxmlStageSetup.buildStage("sudoku-view.fxml", bundle);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public static DifficultyLevel getMyDiff() {
        return myDiff;
    }

    public static String getName() {
        return name;
    }

    public List<String> getNames() throws DaoException {
        List<String> names = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/sudoku_db", "sa", "");
             Statement statement = connection.createStatement()) {

            connection.setAutoCommit(false);

            try (ResultSet resultSet = statement.executeQuery("SELECT name FROM board")) {
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    names.add(name);
                }
                connection.commit();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
                throw new DaoException(ex);
            }
        } catch (SQLException | DaoException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new DaoException(e);
            }
            throw new DaoException(ex);
        }

        return names;
    }
}