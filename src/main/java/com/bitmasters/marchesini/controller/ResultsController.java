package com.bitmasters.marchesini.controller;

import com.bitmasters.marchesini.json.JsonHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultsController extends MarchesiniController implements Initializable {

    private int total_exercises, correct_answers;

    @FXML
    private Label result, points, title;

    // Imposta il titolo integrato nella schermata
    public void setTitle() {
        title.setText("Risultati categoria " + getCategory());
    }

    // Imposta le variabili locali per la gestione dei risultati
    private void setValues() {
        this.total_exercises = JsonHandler.totalExercises(getCategory());
        this.correct_answers = JsonHandler.correctExercises(getUser().getId(), getCategory());
    }

    // Mostra i punti a schermo
    public void setPoints() {
        setValues();
        points.setText(correct_answers + "/" + total_exercises);
    }

    // Imposta il risultato finale a video
    public void setResult() {
        int threshold = (total_exercises / 2) + 1;
        if(correct_answers < threshold) {
            result.setText("NON PASSATO");
            result.setStyle("-fx-text-fill: red;");
        } else {
            result.setText("PASSATO");
            result.setStyle("-fx-text-fill: green;");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
