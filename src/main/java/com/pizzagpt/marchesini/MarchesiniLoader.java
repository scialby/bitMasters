package com.pizzagpt.marchesini;

import com.pizzagpt.Globals;
import com.pizzagpt.Loader;
import com.pizzagpt.PATHS;
import com.pizzagpt.userSession.User;

import java.io.IOException;

public class MarchesiniLoader extends Loader {

    // Variabili
    private Controller controller;

    // Costruttore
    public MarchesiniLoader(User user, String path) throws IOException {
        super(user, Globals.marchesini_views + path);
        controller = (Controller)super.getController();
        controller.setUser(user);
        setCss(Globals.marchesini_css);
        show();
        start();
    }

    // Getter
    @Override
    public Controller getController() {
        return controller;
    }
}
