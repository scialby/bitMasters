package com.pizzagpt.userSession;

import com.pizzagpt.Globals;
import com.pizzagpt.Loader;

import java.io.IOException;

public class SessionLoader extends Loader {

    // Variabili
    private SessionController controller;

    // Costruttore
    public SessionLoader(User user, String path) throws IOException {
        super(user, path);
        setCss(Globals.css);
        controller = (SessionController)super.getController();
        controller.setUser(user);
        show();
        start();
    }

    // Getter
    @Override
    public SessionController getController() {
        return controller;
    }
}
