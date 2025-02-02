package com.pizzagpt.marchesini.loader;

import com.pizzagpt.PATHS;
import com.pizzagpt.marchesini.controller.DescriptionController;
import com.pizzagpt.userSession.User;

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