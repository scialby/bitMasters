package com.pizzagpt.userSession;

import com.pizzagpt.Main;
import com.pizzagpt.PATHS;
import com.pizzagpt.Utils;
import com.pizzagpt.marchesini.MarchesiniExerciseLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
=========
import com.pizzagpt.*;
import com.pizzagpt.marchesini.Controller;
import com.pizzagpt.marchesini.MarchesiniLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

//logs
import static com.pizzagpt.LoggerManager.LOGGER;
import static com.pizzagpt.LoggerManager.debug;
import static com.pizzagpt.LoggerManager.error;

public class SessionController implements Initializable, ControllerInterface {

    // Variabili
    private String username, password;
    private UserManager users;
    private User loggedUser;

    // Oggetti
    private Button btn;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorField;
    @FXML
    private Label windowName;

    // IMPLEMENTAZIONE

    // Dichiara l'utente attualmente loggato nella sessione
    @Override
    public void setUser(User user) {
        this.loggedUser = user;
    }

    // Ritorna alla schermata di login (disconnette l'utente)
    @Override
    public void logOut() {
        try {
            Utils.setScene(PATHS.LOGIN_SCENE);
        } catch (IOException ex) {
            System.out.println("[Eccezione: " + ex + "] E' stato riscontrato un problema.");
        }
    }

    // Inizializzazione della view
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO: Sistemare setTitle dinamico
        //Main.stg.setTitle(windowName.getText());
        Main.stg.setResizable(false);
        users = new UserManager();
    }

    // Inizializza il menù di "Sortino"
    public void startSortino(){
        debug("avvio esercizi sortino");
        try {
            Utils.setScene(PATHS.SORTINO_MAIN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Inizializza il menu di "Marchesini"
    public void startMarchesini() throws IOException {
        debug("avvio esercizi marchesini");
        MarchesiniLoader loadMarchesini = new MarchesiniLoader(loggedUser, "Selection.fxml");
        loadMarchesini.start();
        loadMarchesini.show();
    }

    // Login
    public void toLogin() throws IOException {
        Utils.setScene(PATHS.LOGIN_SCENE); // Utilizzato PATHS per il percorso
    }

    public void loginHandler(ActionEvent event) throws IOException {
        this.username = usernameField.getText();
        this.password = passwordField.getText();
        if(!username.isEmpty() && !password.isEmpty()) {
            if(!users.fromFile()) {
                errorField.setText("Nessun account ancora registrato.");
                return;
            }
            for(User user : users.getUsers()) {
                if(username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                    loggedUser = new User(username, password, user.getId());
                    SessionLoader loader = new SessionLoader(loggedUser, PATHS.MAIN_MENU); //Passa l'utente che si è loggato e cambia scena
                    return;
                }
            }
            errorField.setText("Username o password sbagliati.");
        } else {
            errorField.setText("Compila gli spazi vuoti.");
        }
    }

    // Register
    public void toRegister() throws IOException {
        Utils.setScene(PATHS.REGISTER_SCENE); // Utilizzato PATHS per il percorso
    }

    public void registerHandler(ActionEvent event) throws IOException {
        this.username = usernameField.getText();
        this.password = passwordField.getText();
        if (!username.isEmpty() && !password.isEmpty()) { // Controlla spazi vuoti
            users.fromFile();
            User user = new User(username, password);
            if (!users.exists(user)) {
                users.addUser(user);
                users.toFile();
                loginHandler(event);
            } else {
                errorField.setText("Utente già esistente.");
            }
        } else {
            errorField.setText("Compila gli spazi vuoti.");
        }
    }

    // Selezione del menù principale
    //TODO: Sistemare
    public void toMarchesini() throws IOException {
        MarchesiniLoader loader = new MarchesiniLoader(loggedUser, "Selection.fxml"); //Importa l'utente nel Controller e cambia scena
        //controller.loadProgress(); //TODO: Da completare
    }
    // Temporaneo
    public void temp() throws IOException {
        Main.util.setScene("/com.pizzagpt/scenes/sortino/SortinoEx1_2.fxml");
    }
}
