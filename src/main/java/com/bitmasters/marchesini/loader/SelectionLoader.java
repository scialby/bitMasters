package com.bitmasters.marchesini.loader;

import com.bitmasters.PATHS;
import com.bitmasters.marchesini.controller.SelectionController;
import com.bitmasters.userSession.User;

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