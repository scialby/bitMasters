package com.bitmasters;

// PATHS
// File dedicato alle variabili globali, in genere directories
public final class PATHS {

    // GENERALI
    public final static String RESOURCES = "src/main/resources"; // Directory delle risorse principali
    public final static String SCENES_PATH = "/com.bitmasters/scenes/"; // Percorso base per le scene
    public final static String CSS = SCENES_PATH + "style.css"; // Percorso del file CSS generale
    public final static String ACCOUNTS = "accounts.txt"; // Percorso file degli account
    public final static String USER_SESSION = SCENES_PATH + "userSession/"; // Percorso base per la sessione utente
    public final static String LOGIN_SCENE = USER_SESSION + "LoginScene.fxml"; // Percorso del file FXML per il login
    public final static String REGISTER_SCENE = USER_SESSION + "RegisterScene.fxml"; // Percorso del file FXML per la registrazione
    public final static String MAIN_MENU = USER_SESSION + "MainView.fxml"; // Percorso per il menù principale
    public final static String IMAGES = RESOURCES + "/com.bitmasters/images/";

    // MARCHESINI
    public final static String MARCHESINI = SCENES_PATH + "marchesini/"; // Percorso base per la scena "Marchesini"
    public final static String MARCHESINI_VIEWS = MARCHESINI + "views/"; // Percorso per le viste della scena "Marchesini"
    public final static String MARCHESINI_SAVES = RESOURCES + MARCHESINI + "saves/"; // Percorso per i file di salvataggio
    public final static String MARCHESINI_IMAGES = IMAGES + "marchesini"; // Percorso per le immagini di Marchesini
    public final static String MARCHESINI_SELECTION = MARCHESINI_VIEWS + "Selection.fxml"; // Percorso per il menù di "Marchesini"
    public final static String MARCHESINI_EXERCISE = MARCHESINI_VIEWS + "Exercise.fxml"; // Percorso per la schermata generale degli esercizi
    public final static String MARCHESINI_DESCRIPTION = MARCHESINI_VIEWS + "Description.fxml"; // Percorso per la schermata di descrizione della categoria
    public final static String MARCHESINI_RESULTS = MARCHESINI_VIEWS + "Results.fxml"; // Percorso per la schermata dei risultati di Marchesini
    public final static String MARCHESINI_JSON = RESOURCES + MARCHESINI + "Exercises.json"; // Percorso per il .json di tutti gli esercizi

    // SORTINO
    public final static String SORTINO = SCENES_PATH + "sortino/"; // Percorso base per la scena "Sortino"
    public final static String SORTINO_MAIN = SORTINO + "SortinoMain.fxml"; // Percorso per le viste della scena "Sortino"
    public final static String SORTINO_TUTORIAL = SORTINO + "Tutorial.fxml";
    public final static String SORTINO_EX = "/com.bitmasters/scenes/sortino/Exercise.fxml"; // Percorso per la vista dell'esercizio
    public final static String CORRECTVIEW = "/com.bitmasters/scenes/sortino/Correct.fxml"; // Percorso per la vista dell'esercizio
    public final static String WRONGVIEW = "/com.bitmasters/scenes/sortino/Wrong.fxml"; // Percorso per la vista dell'esercizio

    private PATHS() {
        // Costruttore privato per evitare l'istanza della classe
    }
}
