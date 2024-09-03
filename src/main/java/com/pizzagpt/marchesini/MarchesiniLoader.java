package com.pizzagpt.marchesini;

import com.pizzagpt.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MarchesiniLoader {

    @FXML
    public void wrongChoice() {
        System.out.println("Scelta sbagliata");
        try {
            Main.util.setScene("/com.pizzagpt/scenes/marchesini/MarchesiniEx1_1.fxml"); //Sposta in un'altra finestra
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}