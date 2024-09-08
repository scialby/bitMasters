package com.pizzagpt.userSession;

import com.pizzagpt.marchesini.MarchesiniUtils;
import javafx.fxml.FXML;

public class SessionController {
    @FXML
    public void skipLogin() {
        System.out.println("Skip login");
        try {
            MarchesiniUtils.loadScene(1, 1); //Sposta in un'altra finestra
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
