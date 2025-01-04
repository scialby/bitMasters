package com.pizzagpt.sortino;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TutorialController {

    @FXML
    private Label tutorialLabel; // Collegato al FXML tramite fx:id


    public void setLabelText(String text) {
        tutorialLabel.setText("figlio di"); // Aggiorna il testo del Label
    }
}
