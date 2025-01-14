package com.pizzagpt.marchesini;

import com.pizzagpt.PATHS;
import com.pizzagpt.Utils;
import com.pizzagpt.*;
import com.pizzagpt.userSession.SessionLoader;
import com.pizzagpt.userSession.User;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.fxml.FXML;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

// Classe controller per la gestione delle Scene di Marchesini
public class MarchesiniController implements Initializable, ControllerInterface {

    // Variabile
    private int category, exercise;
    private User loggedUser;

    // Initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    // ControllerInterface
    @Override
    public void setUser(User user) {
        this.loggedUser = user;
    }
    @Override
    public void logOut() {
        try {
            Utils.setScene(PATHS.LOGIN_SCENE);
        } catch (IOException ex) {
            System.out.println("[Eccezione: " + ex + "] E' stato riscontrato un problema.");
        }
    }

    // Estrapola la categoria ed esercizio in cui presente
    public void setInfo(int category, int exercise) {
        this.category = category;
        this.exercise = exercise;
    }

    // Aggiorna le progress-bar se presenti
    //TODO: Caricare le progress bar degli esercizi
    public void loadProgress() {
    }

    // Porta all'esercizio selezionato
    public void toExercise(ActionEvent event) throws IOException {
        Button btn = (Button)event.getSource();
        new MarchesiniExerciseLoader(loggedUser, Integer.parseInt(btn.getId().substring(3)), 1);
    }
    public void toMainMenu() throws IOException {
        new SessionLoader(loggedUser, PATHS.MARCHESINI_MAIN);
    }
    public void toSelection() throws IOException {
        Loader selectionMarchesini = new Loader(loggedUser, PATHS.MARCHESINI_VIEWS + "Selection.fxml");
        selectionMarchesini.load();
        selectionMarchesini.show();
    }

    public void writeToFile(String[] tokens, String choice) {
        try {
            PrintWriter write = new PrintWriter(PATHS.MARCHESINI_SAVES + "user" + loggedUser.getId() + "/" + tokens[0] + ".txt");
            write.println(tokens[1] + "," + choice);
            write.close();
        } catch(FileNotFoundException ex) {
            System.out.println("[Eccezione: " + ex + "]");
        }
    }

    // Precedente e prossimo esercizio
    //TODO: Sistemare perché funzioni
    public void navigationControl(ActionEvent event) throws IOException {
        Button btn = (Button)event.getSource(); //Determina qual è il tasto cliccato
        String id = btn.getId(); //Estrapola l'ID del button
        if(id.equals("previous")) {
            new MarchesiniExerciseLoader(loggedUser, 1, exercise-1);
        } else {
            new MarchesiniExerciseLoader(loggedUser, 1, exercise+1);
        }
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
}
