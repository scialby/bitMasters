package com.pizzagpt.sortino;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class TutorialController {

    @FXML
    private Text content; // Collegato al FXML tramite fx:id


    // Metodo per cambiare il testo
    public void changeText(String newText) {
        System.out.println("Cambiato il testo in: " + newText);
        content.setText(newText);

    }


}
