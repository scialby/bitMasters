package com.pizzagpt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(com.pizzagpt.Main.class.getResource("/scenes/MainScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public void changeScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(com.pizzagpt.Main.class.getResource("/scenes/" + fxml));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stg.setScene(scene);
        stg.show();
    }
    public static void main(String[] args) {
        launch();
    }
}