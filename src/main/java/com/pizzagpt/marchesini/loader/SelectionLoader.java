package com.pizzagpt.marchesini.loader;

import com.pizzagpt.PATHS;
import com.pizzagpt.marchesini.controller.SelectionController;
import com.pizzagpt.userSession.User;

import java.io.IOException;

// Loader per la schermata della Selezione di esercizi

public class SelectionLoader extends MarchesiniLoader<SelectionController> {

    public SelectionLoader(User user) throws IOException {
        super(user, PATHS.MARCHESINI_SELECTION, 0, 0);
        controller.loadProgress();
        load();
        setTitle("Selezione di Marchesini");
        show();
    }
}