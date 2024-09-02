package com.pizzagpt.userSession;

import javafx.fxml.FXML;
import com.pizzagpt.Main;
public class SessionController {
    @FXML
    public void skipLogin(){
        System.out.println("Skip login");
        try {
            Main.util.setScene("/com.pizzagpt/scenes/sortino/SortinoMainView.fxml", Main.windowWidth, Main.windowHeight); //Sposta in un'altra finestra
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
