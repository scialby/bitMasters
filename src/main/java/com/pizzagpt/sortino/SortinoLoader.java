package com.pizzagpt.sortino;

import com.pizzagpt.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SortinoLoader implements javafx.fxml.Initializable {
    public static int score = 0;
    public void loadExercises() throws IOException {
            System.out.println("ciaooo");
            Main.util.setScene("/com.pizzagpt/scenes/sortino/SortinoMainView.fxml");//carica la scena

    }

    public void startEx1() throws IOException {

        Main.util.setScene("/com.pizzagpt/scenes/sortino/SortinoEx1_1.fxml");//carica la scena
    }

    @FXML
    private ChoiceBox<String> choiceBoxEx1_1;
    private String[] choiceBoxEx1_1Values = {"System.out.println(adsdasdasdasadsdasdasdasdasdas)", "2", "3", "4", "5"};
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (choiceBoxEx1_1 != null) {
            choiceBoxEx1_1.getItems().addAll(choiceBoxEx1_1Values);

        } else {
            System.out.println("choiceBoxEx1_1 is not injected.");
        }
    }
}
