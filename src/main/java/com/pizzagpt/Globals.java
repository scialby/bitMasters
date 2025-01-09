package com.pizzagpt;

// Variabili globali (paths e statiche)

public class Globals {

    // Generali
    public final static String resources = "src/main/resources";
    public final static String scenes = "/com.pizzagpt/scenes/";
    public final static String images = "/com.pizzagpt/images/";
    public final static String user_session = scenes + "userSession/";
    public final static String css = user_session + "style.css";
    public final static String accounts = resources + scenes + "accounts.txt";
    public final static String splitter = ",";

    // Marchesini
    public final static String marchesini = scenes + "marchesini/";
    public final static String marchesini_views = marchesini + "views/";
    public final static String marchesini_saves = resources + marchesini + "saves/";
    public final static String marchesini_images = resources + marchesini + "images/";
    public final static String marchesini_css = marchesini + "style.css";

    // .fxml
    public final static String login_scene = user_session + "LoginScene.fxml";
    public final static String register_scene = user_session + "RegisterScene.fxml";
    public final static String main_menu = user_session + "MainMenu.fxml";
}
