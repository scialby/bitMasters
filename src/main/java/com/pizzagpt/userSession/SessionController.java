package com.pizzagpt.userSession;

import com.pizzagpt.Main;
import com.pizzagpt.Utils;
import com.pizzagpt.marchesini.MarchesiniExerciseLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.awt.image.PackedColorModel;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SessionController implements Initializable {

    //Variabili
    private String username, password;
    private UserManager users;

    //Oggetti
    private Button btn;
    @FXML
    private TextField usernameField, passwordField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        users = new UserManager();
        users.fromFile();
    }

    //////////////
    // Funzioni //
    //////////////

    // Estrapola il testo
    // TODO: Migliorare struttura degli oggetti nel .fxml
    /*public void getCredentials(ActionEvent event) {
        btn = (Button)event.getSource();
        VBox vBox = (VBox)btn.getParent().getParent().getChildrenUnmodifiable().getFirst();
        HBox usernameBox = (HBox)vBox.getChildren().getFirst();
        HBox passwordBox = (HBox)vBox.getChildren().getLast();
        usernameField = (TextField)usernameBox.getChildren().getLast();
        passwordField = (TextField)passwordBox.getChildren().getLast();
        username = usernameField.getText().trim();
        password = passwordField.getText().trim();
    }*/

    // Login //TODO: Migliorare e compattare le variabili con il register
    public void loginHandler(ActionEvent event) {
        this.username = usernameField.getText();
        this.password = passwordField.getText();
        try {
            Scanner read = new Scanner(new File(Main.accounts));
            while(read.hasNextLine()) {
                String line = read.nextLine();
                String[] tokens = line.split(Main.splitter);
                if(username.equals(tokens[0]) && password.equals(tokens[1])) {
                    int id = Integer.parseInt(tokens[2]);
                    User user = new User(username, password, id);
                    new MarchesiniExerciseLoader(user, 1, 1);
                }
            }
        } catch(IOException ex) {
            System.out.println("[Eccezione: " + ex + "] E' stato riscontrato un problema nel login.");
        }
    }

    public void registerHandler(ActionEvent event) {
        this.username = usernameField.getText();
        this.password = passwordField.getText();
        if(!username.isEmpty() && !password.isEmpty()) { //Se entrambi hanno testo non vuoto allora procedi
            User user = new User(username, password);
            if(users.addUser(user)) { //Se lo aggiunge allora
                System.out.println(new File(Main.marchesini_saves + "user" + user.getId()).mkdir()); //Crea salvataggio
                users.toFile(); //Salva
                System.out.println("Utente registrato");
            } else {
                System.out.println("Utente non registrato");
            }
        } else { //Eliminare
            System.out.println("Immettere spazi non vuoti");
        }
    }
    @FXML
    public void skipLogin() {
        System.out.println("Skip login");
        try {
            //new ExerciseLoader(1, 1); //Sposta in un'altra finestra
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
