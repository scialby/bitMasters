package com.pizzagpt.sortino;

import com.pizzagpt.Main;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SortinoLoader implements Initializable {

    @FXML
    private ChoiceBox<String> choiceBoxEx1_1;

    private final String[] choiceBoxEx1_1Values = {
            "String risultato = numero1+numero2;",
            "Int risultato = numero1+numero2",
            "Float risultato = numero1+numero2;"  // Corretta
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (choiceBoxEx1_1 != null) {
            choiceBoxEx1_1.getItems().addAll(choiceBoxEx1_1Values);
            choiceBoxEx1_1.setValue(choiceBoxEx1_1Values[0]); // Set default value

            // Aggiungi listener per la scelta
            choiceBoxEx1_1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    System.out.println("Selected Value - " + newValue);
                    if ("Float risultato = numero1+numero2;".equals(newValue)) {
                        Main.playerScore++;
                        System.out.println("Correct answer selected! Score incremented.");
                    } else {
                        System.out.println("Incorrect answer selected.");
                    }
                    //imposto 2 scena 1 ex

                    try {
                        Main.util.setScene("/com.pizzagpt/scenes/sortino/SortinoEx1_2.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });

            System.out.println("ChoiceBox initialized with default values.");
        } else {
            System.out.println("ChoiceBox is not injected.");
        }
    }

    public void loadExercises() throws IOException {
        // Carica la scena principale di Sortino
        Main.util.setScene("/com.pizzagpt/scenes/sortino/SortinoMainView.fxml");
    }

    public void startEx1() throws IOException {
        System.out.println("Starting exercise 1...");

        // Carica e imposta la scena correttamente
        Main.util.setScene("/com.pizzagpt/scenes/sortino/SortinoEx1_1.fxml");


        // Il ChoiceBox viene ora inizializzato correttamente
    }
}
