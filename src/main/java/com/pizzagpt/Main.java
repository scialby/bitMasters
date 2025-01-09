package com.pizzagpt;

import com.pizzagpt.sortino.SortinoLoader;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    public static int playerScore = 0;
    public static Label playerScoreLabel;
    public static Stage stg;
    public static Utils util;
    public static int windowWidth = 1000;
    public static int windowHeight = 600;
    private String mainViewPath = "/com.pizzagpt/scenes/userSession/LoginScene.fxml";

    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage;
        util.setScene(mainViewPath);
    }

    @FXML
    public void startSortinoView(){
        System.out.println("start sortino View");
        SortinoLoader sortinoLoader = new SortinoLoader();
        try {
            sortinoLoader.loadExercises();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        launch();
    }
}