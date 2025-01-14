package com.pizzagpt.userSession;

import com.pizzagpt.Loader;
import com.pizzagpt.PATHS;

import java.io.IOException;

public class SessionLoader extends Loader {

    // Variabili
    private SessionController controller;

    // Costruttore
    public SessionLoader(User user, String path) throws IOException {
        super(user, path);
        setCss(PATHS.CSS);
        controller = (SessionController)super.getController();
        controller.setUser(user);
        load();
        show();
    }

    // Getter
    @Override
    public SessionController getController() {
        return controller;
    }
}
