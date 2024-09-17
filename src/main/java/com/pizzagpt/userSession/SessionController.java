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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;

import java.awt.image.PackedColorModel;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

public class SessionController implements Initializable {

    // Variabili
    private String username, password;
    private UserManager users;

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

    // Initialize
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.stg.setTitle(windowName.getText());
        Main.stg.setResizable(false);
        users = new UserManager();
    }

    // Login
    public void toLogin() throws IOException {
        Main.util.setScene(Main.login_scene);
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
                    new MarchesiniExerciseLoader(new User(username, password, user.getId()), 1, 1);
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
        Main.util.setScene(Main.register_scene);
    }
    public void registerHandler(ActionEvent event) throws IOException {
        this.username = usernameField.getText();
        this.password = passwordField.getText();
        if(!username.isEmpty() && !password.isEmpty()) { //Controlla spazi vuoti
            users.fromFile();
            User user = new User(username, password);
            if(users.addUser(user)) { //Crea il nuovo utente e accede
                System.out.println(new File(Main.marchesini_saves + "user" + user.getId()).mkdir());
                users.toFile();
                loginHandler(event);
            } else {
                errorField.setText("Utente già esistente.");
            }
        } else {
            errorField.setText("Compila gli spazi vuoti.");
        }
    }

    // Temporaneo
    public void temp() throws IOException {
        Main.util.setScene("/com.pizzagpt/scenes/sortino/SortinoEx1_2.fxml");
    }
}
