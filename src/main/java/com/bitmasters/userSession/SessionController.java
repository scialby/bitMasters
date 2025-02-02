package com.bitmasters.userSession;

import com.bitmasters.Main;
import com.bitmasters.PATHS;
import com.bitmasters.Utils;
import com.bitmasters.marchesini.loader.Loader;
import com.bitmasters.marchesini.loader.SelectionLoader;
import com.bitmasters.sortino.ProgressManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import com.bitmasters.*;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//logs
import static com.bitmasters.LoggerManager.debug;
import static com.bitmasters.Main.setCurrentUser;

public class SessionController implements Initializable, ControllerInterface {

    // Variabili
    private String username, password;
    private UserManager users;
    private User loggedUser;
    private ProgressManager progressManager;
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
    @Override
    public User getUser() { return loggedUser; }
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
        new SelectionLoader(loggedUser);
    }

    // Login
    // Porta alla videata del login
    public void toLogin() throws IOException {
        Utils.setSceneCSS(PATHS.LOGIN_SCENE); // Utilizzato PATHS per il percorso
    }

    // Gestisce il login
    public void loginHandler(ActionEvent event) throws IOException {
        this.username = usernameField.getText();
        this.password = passwordField.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            if (!users.fromFile()) {
                errorField.setText("Nessun account ancora registrato.");
                return;
            }
            for (User user : users.getUsers()) {
                if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
                    loggedUser = new User(username, password, user.getId());
                    setCurrentUser(loggedUser);
                    // Carica i progressi dell'utente
                    loggedUser.setProgress(ProgressManager.loadProgress(username));

                    Loader loader = new Loader(loggedUser, PATHS.MAIN_MENU); // Passa l'utente loggato e cambia scena
                    loader.load();
                    loader.show();
                    return;
                }
            }
            errorField.setText("Username o password sbagliati.");
        } else {
            errorField.setText("Compila gli spazi vuoti.");
        }
    }

    // Register
    // Porta alla videata del register
    public void toRegister() throws IOException {
        Utils.setSceneCSS(PATHS.REGISTER_SCENE); // Utilizzato PATHS per il percorso
    }

    // Gestisce il register
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

}
