package com.pizzagpt.marchesini;

import com.pizzagpt.Main;
import com.pizzagpt.Utils;
import com.pizzagpt.userSession.SessionController;
import com.pizzagpt.userSession.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private User user;
    private Scene root;

    public void setUser(User user) {
        this.user = user;
    }

    //----------------//
    // Scrive su file //
    //----------------//
    public void writeToFile(String[] tokens, String choice) {
        try {
            PrintWriter write = new PrintWriter(Main.marchesini_saves + "user" + user.getId() + "/" + tokens[0] + ".txt");
            write.println(tokens[1] + "," + choice);
            write.close();
        } catch(FileNotFoundException ex) {
            System.out.println("[Eccezione: " + ex + "]");
        }
    }

    //---------------------------------------//
    // Ritorna alla selezione degli esercizi //
    //---------------------------------------//
    public void goToSelection() throws IOException {
        System.out.println("Torna al menu (loginscreen come placeholder)");
        Utils.setScene("/com.pizzagpt/scenes/userSession/LoginScene.fxml");
    }

    //---------------------------------//
    // Precedente / Prossimo esercizio //
    //---------------------------------//
    //TODO: Sistemare perché funzioni
    public void navigationControl(ActionEvent event) throws IOException {
        Button btn = (Button)event.getSource(); //Determina qual è il tasto cliccato
        String id = btn.getId(); //Estrapola l'ID del button
        String[] tokens = btn.getParent().getParent().getId().split("_"); //Estrapola le informazioni dell'esercizio corrente
        int temp = 1;
        if(id.equals("previous")) {
            temp = -1;
        }
        //Utilities.loadScene(("cat" + Integer.parseInt(tokens[0]) + "/Es" + Integer.parseInt(tokens[1])) + ".fxml");
    }

    //-------------------------------------------------------//
    // Stabilisce il risultato al click di una delle opzioni //
    //-------------------------------------------------------//
    @FXML
    public void choiceHandler(ActionEvent event) {
        Button btn = (Button)event.getSource(); //Determina qual è il tasto cliccato
        String choice = btn.getId();
        String[] tokens = btn.getParent().getParent().getId().split("_");

        for(Node child : btn.getParent().getChildrenUnmodifiable()) { //Dal tasto cliccato risale al parente (HBox) e prende tutti i figli, poi li cicla
            if(child.getStyleClass().contains("option")) { //Se uno dei figli ha la classe "option" allora fa cambiamenti:
                child.getStyleClass().remove("option");
                child.getStyleClass().add("neutral");
                child.setDisable(true);
            }
        }
        //Controlla il tasto cliccato
        if(btn.getId().contains("wrong")) {
            btn.getStyleClass().remove("neutral");
            btn.getStyleClass().add("wrong");
        } else {
            btn.getStyleClass().remove("neutral");
            btn.getStyleClass().add("correct");
        }
        writeToFile(tokens, choice);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
