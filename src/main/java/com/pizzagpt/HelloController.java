package com.pizzagpt;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class HelloController {
    @FXML
    private Button button;
    @FXML
    private Label wrongLogIn;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;


    public void readFile(String filename){
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
            }
            myReader.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    public void userLogin(ActionEvent event) throws IOException {
        checkLogin();

    }

    public void userRegister(ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("test.fxml");

    }

    private void checkLogin() throws IOException {

        //get data from users.txt
        //check if username and password match
        //if match, change scene
        //if not, display error message
        // file name is users.txt
        // format is username,password

        File file = new File("users.txt");
        if(file.exists()) {

        } else {
            wrongLogIn.setText("No users found");
        }
    }
}