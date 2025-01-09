package com.pizzagpt.userSession;

import com.pizzagpt.*;
import com.pizzagpt.marchesini.Controller;
import com.pizzagpt.marchesini.MarchesiniLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SessionController implements Initializable, ControllerInterface {

    // Variabili
    private String username, password;
    private UserManager users;
    private User loggedUser;

    // Oggetti
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorField;
    @FXML
    private Label windowName;

    // Implementazione
    @Override
    public void setUser(User user) {
        this.loggedUser = user;
    }
    @Override
    public void logOut() {
        try {
            Utils.setScene(Globals.login_scene);
        } catch (IOException ex) {
            System.out.println("[Eccezione: " + ex + "] E' stato riscontrato un problema.");
        }
    }

    // Initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Main.stg.setTitle(windowName.getText());
        Main.stg.setResizable(false);
        users = new UserManager();
    }

    // Login
    public void toLogin() throws IOException {
        Main.util.setScene(Globals.login_scene);
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
                    SessionLoader loader = new SessionLoader(loggedUser, Globals.main_menu); //Importa l'utente nel SessionController e cambia scena
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
        Main.util.setScene(Globals.register_scene);
    }
    public void registerHandler(ActionEvent event) throws IOException {
        this.username = usernameField.getText();
        this.password = passwordField.getText();
        if(!username.isEmpty() && !password.isEmpty()) { //Controlla spazi vuoti
            users.fromFile();
            User user = new User(username, password);
            if(users.addUser(user)) { //Crea il nuovo utente e accede
                System.out.println(new File(Globals.marchesini_saves + "user" + user.getId()).mkdir());
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
