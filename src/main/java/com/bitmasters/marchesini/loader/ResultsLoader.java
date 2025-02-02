package com.bitmasters.marchesini.loader;

import com.bitmasters.PATHS;
import com.bitmasters.marchesini.controller.ResultsController;
import com.bitmasters.userSession.User;

import java.io.IOException;

// Loader per la schermata dei Risultati

public class ResultsLoader extends MarchesiniLoader<ResultsController> {

    public ResultsLoader(User user, int category) throws IOException {
        super(user, PATHS.MARCHESINI_RESULTS, category, 0);
        controller.setTitle();
        controller.setPoints();
        controller.setResult();
        load();
        setTitle("Risultati Categoria " + category);
        show();
    }
}