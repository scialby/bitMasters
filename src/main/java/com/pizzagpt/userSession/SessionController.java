package com.pizzagpt.userSession;

import javafx.fxml.FXML;
import com.pizzagpt.Main;
public class SessionController {
    @FXML
    public void skipLogin(){
        System.out.println("Skip login");
        try {
            Main.changeScene("prova.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
