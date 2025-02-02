package com.bitmasters.marchesini.loader;

import com.bitmasters.PATHS;
import com.bitmasters.marchesini.controller.DescriptionController;
import com.bitmasters.userSession.User;

import java.io.IOException;

// Loader per la schermata della Descrizione

public class DescriptionLoader extends MarchesiniLoader<DescriptionController> {

    public DescriptionLoader(User user, int category) throws IOException {
        super(user, PATHS.MARCHESINI_DESCRIPTION, category, 1);
        controller.loadDescription();
        load();
        setTitle("Categoria " + category);
        show();
    }
}