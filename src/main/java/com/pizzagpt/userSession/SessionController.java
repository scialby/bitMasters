package com.pizzagpt.userSession;

import com.pizzagpt.Main;
import com.pizzagpt.PATHS;
import com.pizzagpt.Utils;
import com.pizzagpt.marchesini.MarchesiniExerciseLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//logs
import static com.pizzagpt.LoggerManager.LOGGER;
import static com.pizzagpt.LoggerManager.debug;
import static com.pizzagpt.LoggerManager.error;

public class SessionController implements Initializable {

    // Variabili
    private String username, password;
    private UserManager users;

    // Oggetti
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorField;
    @FXML
    private Label windowName;

    // Initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.stg.setTitle("titolo");
        Main.stg.setResizable(false);
        users = new UserManager();
    }
    public void startSortino(){
        debug("avvio esercizi sortino");
        try {
            Utils.setScene(PATHS.SORTINO_MAIN);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void startMarchesini(){
        debug("avvio esercizi marchesini");

    }
    // Login
    public void toLogin() throws IOException {
        Utils.setScene(PATHS.LOGIN_SCENE); // Utilizzato PATHS per il percorso
    }

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
                    // Login confermato, si passa alla MainView del programma
                    debug("user autenticato con successo");

                    Utils.setScene("/com.pizzagpt/scenes/userSession/MainView.fxml");
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
            if (users.addUser(user)) { // Crea il nuovo utente e accede
                new File(PATHS.MARCHESINI_SAVES + "user" + user.getId()).mkdir();
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
