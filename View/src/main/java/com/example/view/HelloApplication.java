package com.example.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class HelloApplication extends Application {
    private ResourceBundle bundle = ResourceBundle.getBundle("lang");

    @Override
    public void start(Stage stage) throws IOException {
        FxmlStageSetup.buildStage(stage, "hello-view.fxml", "Sudoku", bundle);
    }

    public static void main(String[] args) {
        launch();
    }
}