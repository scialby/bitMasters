package com.pizzagpt;

// PATHS
public final class PATHS {

    // GENERALI
    public final static String RESOURCES = "src/main/resources"; // Directory delle risorse principali
    public final static String SCENES_PATH = "/com.pizzagpt/scenes/"; // Percorso base per le scene
    public final static String CSS = SCENES_PATH + "userSession/style.css"; // Percorso del file CSS generale
    public final static String ACCOUNTS = RESOURCES + SCENES_PATH + "accounts.txt"; // Percorso file degli account
    public final static String USER_SESSION = SCENES_PATH + "userSession/"; // Percorso base per la sessione utente
    public final static String LOGIN_SCENE = USER_SESSION + "LoginScene.fxml"; // Percorso del file FXML per il login
    public final static String REGISTER_SCENE = USER_SESSION + "RegisterScene.fxml"; // Percorso del file FXML per la registrazione

    // MARCHESINI
    public final static String MARCHESINI = SCENES_PATH + "marchesini/"; // Percorso base per la scena "Marchesini"
    public final static String MARCHESINI_VIEWS = MARCHESINI + "views/"; // Percorso per le viste della scena "Marchesini"
    public final static String MARCHESINI_SAVES = RESOURCES + MARCHESINI + "saves/"; // Percorso per i file di salvataggio
    public final static String MARCHESINI_IMAGES = RESOURCES + MARCHESINI + "images/"; // Percorso per le immagini
    public final static String MARCHESINI_CSS = MARCHESINI + "style.css"; // Percorso per il CSS della scena "Marchesini"

    // SORTINO
    public final static String SORTINO_SCENES = SCENES_PATH + "sortino/"; // Percorso base per la scena "Sortino"
    public final static String SORTINO_MAIN = SORTINO_SCENES + "SortinoMain.fxml"; // Percorso per le viste della scena "Sortino"
    public final static String TUTORIAL = SORTINO_SCENES + "Tutorial.fxml";


    private PATHS() {
        // Costruttore privato per evitare l'istanza della classe
    }
}
