package com.pizzagpt.sortino;

import com.pizzagpt.LoggerManager;
import com.pizzagpt.Main;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;

public class Exercise {
    @FXML
    private ImageView exerciseImage; // Collegato al FXML tramite fx:id
    @FXML
    public void initialize() {
        try {
            LoggerManager.debug("Loading image for exercise " + ExerciseController.getExImage(Main.exId));;
            //crea l'oggetto l'immagine partendo dal path dell'immagine
            try {
                String imgPath = ExerciseController.getExImage(Main.exId);
                java.io.File file = new java.io.File(imgPath);
                exerciseImage.setImage(new javafx.scene.image.Image(file.toURI().toString()));
            } catch (Exception e) {
                LoggerManager.debug("Error loading image: " + e.getMessage());
                exerciseImage.setImage(new javafx.scene.image.Image(getClass().getResource("src/main/resources/com.pizzagpt/images/sortinoEx1/Image-not-found.png").toString()));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
