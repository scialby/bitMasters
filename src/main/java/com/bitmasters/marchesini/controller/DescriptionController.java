package com.bitmasters.marchesini.controller;

import com.bitmasters.marchesini.loader.ExerciseLoader;
import com.bitmasters.marchesini.json.JsonHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Controller per il controllo della schermata della Descrizione

public class DescriptionController extends MarchesiniController implements Initializable {

    // VARIABILI
    @FXML
    private Label description, title;

    // Carica la descrizione della categoria
    public void loadDescription() {
        if(canLoadFromJson()) {
            title.setText("Categoria " + getCategory());
            description.setText(getCategory_info().getDescription());
            description.setWrapText(true);
        }
    }

    // Manda l'utente al primo esercizio
    public void toExercise() throws IOException {
        JsonHandler.cleanUserJson(getUser().getId(), getCategory());
        new ExerciseLoader(getUser(), getCategory(), 1);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
