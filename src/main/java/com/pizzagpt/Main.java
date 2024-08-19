package com.pizzagpt;

import com.pizzagpt.sortino.SortinoLoader;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stg;
    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com.pizzagpt/scenes/userSession/LoginScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void setScene(String path) throws IOException {

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stg.setScene(scene);
            stg.show();

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