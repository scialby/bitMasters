package com.pizzagpt.sortino;

import com.pizzagpt.Main;
import javafx.fxml.FXML;

import java.io.IOException;

public class SortinoLoader {

    public void loadExercises() throws IOException {
            System.out.println("ciaooo");
            Main.setScene("/com.pizzagpt/scenes/sortino/SortinoMainView.fxml");//carica la scena

    }

    public void startEx1() throws IOException {

        Main.setScene("/com.pizzagpt/scenes/sortino/SortinoEx1_1.fxml");//carica la scena
    }

}
