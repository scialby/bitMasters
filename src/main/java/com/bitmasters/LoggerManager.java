package com.bitmasters;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerManager {

    // Logger globale usato per registrare messaggi
    public static final Logger LOGGER = Logger.getLogger("GlobalLogger");

    // Blocco statico per configurare il logger all'avvio
    static {
        configureLogger();
    }

    /**
     * Configura il logger globale, impostando il livello e un handler per la console.
     */
    private static void configureLogger() {
        // Rimuove gli handler predefiniti
        LOGGER.setUseParentHandlers(false);

        // Imposta il livello del logger a mostrare tutti i messaggi
        LOGGER.setLevel(Level.ALL);

        // Crea un handler per la console e imposta il livello
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.ALL); // Mostra tutti i livelli nella console
        consoleHandler.setFormatter(new SimpleFormatter() {
            @Override
            public synchronized String format(java.util.logging.LogRecord record) {
                String level = record.getLevel().getName(); // Livello del messaggio
                String message = record.getMessage(); // Messaggio
                return String.format("[%s] %s%n", level, message); // Formato personalizzato
            }
        });

        // Aggiunge l'handler configurato al logger
        LOGGER.addHandler(consoleHandler);

        // Logga un messaggio iniziale per indicare che il logger è avviato
        LOGGER.info("Logger avviato correttamente!");
    }

    /**
     * Registra un messaggio di debug.
     */
    public static void debug(String message) {
        LOGGER.fine("[DEBUG] " + message);
    }

    /**
     * Registra un messaggio di errore.
     */
    public static void error(String message) {
        LOGGER.severe("[ERROR] " + message);
    }

    /**
     * Registra un messaggio informativo.
     */
    public static void info(String message) {
        LOGGER.info("[INFO] " + message);
    }

    /**
     * Registra un messaggio di avvertimento.
     */
    public static void warn(String message) {
        LOGGER.warning("[WARN] " + message);
    }
}
