package com.pizzagpt;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerManager {

    // Logger globale
    public static final Logger LOGGER = Logger.getLogger("GlobalLogger");

    static {
        configureLogger();
    }

    /**
     * Configura il logger globale con un handler solo per la console.
     */
    private static void configureLogger() {
        // Rimuove gli handler predefiniti
        LOGGER.setUseParentHandlers(false);

        // Imposta il livello del logger
        LOGGER.setLevel(Level.ALL);

        // Handler per la console
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

        // Aggiunge l'handler al logger
        LOGGER.addHandler(consoleHandler);

        // Messaggio iniziale
        LOGGER.info("Logger avviato correttamente!");
    }

    /**
     * Metodo per registrare un messaggio di debug
     * @param message Messaggio da loggare
     */
    public static void debug(String message) {
        LOGGER.fine("[DEBUG] " + message);
    }

    /**
     * Metodo per registrare un messaggio di errore
     * @param message Messaggio di errore
     */
    public static void error(String message) {
        LOGGER.severe("[ERROR] " + message);
    }

    /**
     * Metodo per registrare un messaggio informativo
     * @param message Messaggio informativo
     */
    public static void info(String message) {
        LOGGER.info("[INFO] " + message);
    }

    /**
     * Metodo per registrare un messaggio di avvertimento
     * @param message Messaggio di avvertimento
     */
    public static void warn(String message) {
        LOGGER.warning("[WARN] " + message);
    }
}
