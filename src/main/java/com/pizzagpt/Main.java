package com.pizzagpt;

import com.pizzagpt.sortino.SortinoLoader;
import com.pizzagpt.Utils;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    //Globals
    public final static String resources = "src/main/resources";
    public final static String marchesini = "/com.pizzagpt/scenes/marchesini/";
    public final static String css = "/com.pizzagpt/scenes/userSession/style.css";
    public final static String accounts = resources + "/com.pizzagpt/scenes/accounts.txt";
    public final static String user_session = "/com.pizzagpt/scenes/userSession/";
    public final static String login_scene = user_session + "LoginScene.fxml";
    public final static String register_scene = user_session + "RegisterScene.fxml";
    public final static String marchesini_views = marchesini + "views/";
    public final static String marchesini_saves = resources + marchesini + "saves/";
    public final static String marchesini_images = resources + marchesini + "images/";
    public final static String marchesini_css = marchesini + "style.css";
    public final static String splitter = ",";

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