package com.pizzagpt.sortino;

import com.pizzagpt.PATHS;
import com.pizzagpt.Main;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import com.pizzagpt.sortino.TutorialController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.pizzagpt.LoggerManager.debug;

public class SortinoLoader {

    // Dichiarazione dei ChoiceBox,(ogni choicebox avrà selezionabile il livello facile,medio,difficile)
    @FXML
    private ChoiceBox<String> choiceBoxEx1_1;

    @FXML
    private ChoiceBox<String> choiceBoxEx1_2;

    @FXML
    private ChoiceBox<String> choiceBoxEx1_3;

    // Metodo per aggiungere le opzioni selezionabili ai ChoiceBox
    @FXML
    public void initialize() {
        // Aggiunta delle opzioni per il primo ChoiceBox
        choiceBoxEx1_1.setItems(FXCollections.observableArrayList("1", "2", "3"));

        // Aggiunta delle opzioni per il secondo ChoiceBox
        choiceBoxEx1_2.setItems(FXCollections.observableArrayList("1", "2", "3"));

        // Aggiunta delle opzioni per il terzo ChoiceBox
        choiceBoxEx1_3.setItems(FXCollections.observableArrayList("1", "2", "3"));

        // Listener per ciascun ChoiceBox per rilevare la selezione
        choiceBoxEx1_1.setOnAction(this::onChoiceBoxAction);
        choiceBoxEx1_2.setOnAction(this::onChoiceBoxAction);
        choiceBoxEx1_3.setOnAction(this::onChoiceBoxAction);
    }




    // Metodo per gestire la selezione degli elementi nei ChoiceBox
    private void onChoiceBoxAction(ActionEvent event) {
        // Variabili per salvare il valore e l'ID selezionato
        String selectedValue = "";
        String selectedId = "";

        // Verifica quale ChoiceBox ha generato l'evento
        if (event.getSource() == choiceBoxEx1_1) {
            selectedValue = choiceBoxEx1_1.getValue();
            selectedId = "1";
        } else if (event.getSource() == choiceBoxEx1_2) {
            selectedValue = choiceBoxEx1_2.getValue();
            selectedId = "2";
        } else if (event.getSource() == choiceBoxEx1_3) {
            selectedValue = choiceBoxEx1_3.getValue();
            selectedId = "3";
        }

        // Stampa il valore selezionato e l'ID dell'elemento
        if (selectedValue != null) {
            String id=selectedId+"_"+selectedValue;
            debug("Esercizio selezionato: " + id);
            try {
                TutorialController c = new TutorialController();
                c.changeTutorialText(id);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }


}
