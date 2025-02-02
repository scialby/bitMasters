package com.bitmasters.marchesini.loader;

import com.bitmasters.PATHS;
import com.bitmasters.marchesini.controller.SelectionController;
import com.bitmasters.userSession.User;

import java.io.File;
import java.io.IOException;

// Loader per la schermata della Selezione di esercizi

public class SelectionLoader extends MarchesiniLoader<SelectionController> {

    public SelectionLoader(User user) throws IOException {
        super(user, PATHS.MARCHESINI_SELECTION, 0, 0);
        File newFolder = new File(PATHS.MARCHESINI_JAR, "saves");
        if (!newFolder.exists()) {
            newFolder.mkdir();
        }
        controller.loadProgress();
        load();
        setTitle("Selezione di Marchesini");
        show();
    }
}