package com.bitmasters.marchesini.controller;

import com.bitmasters.marchesini.json.JsonHandler;
import com.bitmasters.marchesini.loader.DescriptionLoader;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

// Classe controller per la gestione delle Scene di Marchesini
public class SelectionController extends MarchesiniController implements Initializable {

    @FXML
    private GridPane categories;

    //Aggiorna le progress bar di ogni categoria
    public void loadProgress() {
        if (JsonHandler.existsUser(getUser().getId())) {
            for (int i = 1; i <= JsonHandler.totalCategories(); i++) {
                for (Node node : categories.getChildren()) {
                    if (node.lookup("#cat" + i) != null) {
                        ProgressBar progress_bar = (ProgressBar) node.lookup("#progress_bar");
                        int total_exercises = JsonHandler.totalExercises(i);
                        int correct_answers = JsonHandler.correctExercises(getUser().getId(), i);
                        int threshold = (total_exercises / 2) + 1;
                        double progress_value = (double) correct_answers / total_exercises;
                        progress_bar.setProgress(progress_value);
                        if (correct_answers < threshold) {
                            progress_bar.setStyle("-fx-accent: red;");
                        } else {
                            progress_bar.setStyle("-fx-accent: green;");
                        }
                    }
                }
            }
        }
    }

    // Porta alla descrizione anticipativa della categoria selezionata
    public void toDescription(ActionEvent event) {
        try {
            //Capisco che tasto è stato cliccato dall'ID
            Button btn = (Button) event.getSource();
            String btn_id = btn.getId();
            String[] btn_tokens = btn_id.split("\\D+");
            setCategory(Integer.parseInt(btn_tokens[1]));
            //Carico la schermata di descrizione
            new DescriptionLoader(getUser(), getCategory());
        } catch (NumberFormatException | ClassCastException e) {
            System.out.println("[" + e.getMessage() + "] ID del tasto non valido.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
