package com.pizzagpt;

import com.pizzagpt.sortino.SortinoMain;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import java.io.IOException;
import java.util.Objects;
import static com.pizzagpt.LoggerManager.debug;
public class Utils {

    public static void setScene(String path, int windowWidth, int windowHeight) throws IOException {
        debug("inizio caricamento scena: "+ path);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
        Parent loadedContent = fxmlLoader.load();
        Scene scene = new Scene(loadedContent, windowWidth, windowHeight);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBounds.getWidth() - windowWidth) / 2;
        double centerY = (screenBounds.getHeight() - windowHeight) / 2;

        Main.stg.setX(centerX);
        Main.stg.setY(centerY);
        Main.stg.setWidth(windowWidth);
        Main.stg.setHeight(windowHeight);
        Main.stg.setMinWidth(windowWidth);
        Main.stg.setMinHeight(windowHeight);
        Main.stg.setMaxWidth(windowWidth);
        Main.stg.setMaxHeight(windowHeight);
        Main.stg.setResizable(false);

        Main.stg.setScene(scene);
        Main.stg.show();

    }

    public static void setSceneDynamic(Scene scene) throws IOException {

        scene.getStylesheets().add(Objects.requireNonNull(Utils.class.getResource(PATHS.CSS)).toExternalForm());
        Main.stg.setMinWidth(0);
        Main.stg.setMinHeight(0);
        Main.stg.setScene(scene);
        Main.stg.sizeToScene();
        Main.stg.centerOnScreen();
        Main.stg.show();
    }
    public static void setSceneDynamic(Scene scene, int minWidth, int minHeight) throws IOException {

        scene.getStylesheets().add(Objects.requireNonNull(Utils.class.getResource(PATHS.CSS)).toExternalForm());
        Main.stg.setMinWidth(minWidth);
        Main.stg.setMinHeight(minHeight);
        Main.stg.setScene(scene);
        Main.stg.sizeToScene();
        Main.stg.centerOnScreen();
        Main.stg.show();
    }
    public static void setSceneDynamic(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
        Parent loadedContent = fxmlLoader.load();
        Scene scene = new Scene(loadedContent);

        scene.getStylesheets().add(Objects.requireNonNull(Utils.class.getResource(PATHS.CSS)).toExternalForm());
        Main.stg.setMinWidth(0);
        Main.stg.setMinHeight(0);
        Main.stg.setScene(scene);
        Main.stg.sizeToScene();
        Main.stg.centerOnScreen();
        Main.stg.show();
    }

    /* Prende il nome del file fxml in cui ci si trova*/
    public static String getSceneName() {
        if (Main.stg != null && Main.stg.getScene() != null && Main.stg.getScene().getRoot() != null) {
            return Main.stg.getScene().getRoot().getId();
        } else {
            return "Root, Scene, or Stage is not set.";
        }
    }
    public static void setScene(Scene scene) throws IOException {
        int windowWidth = Main.defaultWindowWidth;
        int windowHeight = Main.defaultWindowHeight;



        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBounds.getWidth() - windowWidth) / 2;
        double centerY = (screenBounds.getHeight() - windowHeight) / 2;

        Main.stg.setX(centerX);
        Main.stg.setY(centerY);
        Main.stg.setWidth(windowWidth);
        Main.stg.setHeight(windowHeight);
        Main.stg.setMinWidth(windowWidth);
        Main.stg.setMinHeight(windowHeight);
        Main.stg.setMaxWidth(windowWidth);
        Main.stg.setMaxHeight(windowHeight);
        Main.stg.setResizable(false);

        Main.stg.setScene(scene);
        Main.stg.show();
        debug("Settaggio scena completato, id esercizio: "+ SortinoMain.exId);

    }

    public static void setScene(String path) throws IOException {
        int windowWidth = Main.defaultWindowWidth;
        int windowHeight = Main.defaultWindowHeight;


        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
        Parent loadedContent = fxmlLoader.load();
        Scene scene = new Scene(loadedContent, windowWidth, windowHeight);

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBounds.getWidth() - windowWidth) / 2;
        double centerY = (screenBounds.getHeight() - windowHeight) / 2;

        Main.stg.setX(centerX);
        Main.stg.setY(centerY);
        Main.stg.setWidth(windowWidth);
        Main.stg.setHeight(windowHeight);
        Main.stg.setMinWidth(windowWidth);
        Main.stg.setMinHeight(windowHeight);
        Main.stg.setMaxWidth(windowWidth);
        Main.stg.setMaxHeight(windowHeight);
        Main.stg.setResizable(false);

        Main.stg.setScene(scene);
        Main.stg.show();
        debug("Settaggio scena completato, id esercizio: "+ SortinoMain.exId);

    }

    /*IMPOSTA LA SCENA CON IL CSS*/
    public static void setSceneCSS(String path) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
        Pane loadedContent = fxmlLoader.load();
        Scene scene = new Scene(loadedContent);
        scene.getStylesheets().add(Objects.requireNonNull(Utils.class.getResource(PATHS.CSS)).toExternalForm());
        Main.stg.setScene(scene);
        Main.stg.setTitle("");
        Main.stg.show();
    }
    public static void setSceneCSS(Scene scene) throws IOException {

        scene.getStylesheets().add(Objects.requireNonNull(Utils.class.getResource(PATHS.CSS)).toExternalForm());
        Main.stg.setScene(scene);
        Main.stg.setTitle("");
        Main.stg.show();
    }
}