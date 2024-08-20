package com.pizzagpt;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import com.pizzagpt.Main;

public class Utils {

    public static void setScene(String path) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(path));
        Scene scene = new Scene(fxmlLoader.load(), Main.windowWidth, Main.windowHeight);
        Main.stg.setScene(scene);
        Main.stg.show();

    }
}
