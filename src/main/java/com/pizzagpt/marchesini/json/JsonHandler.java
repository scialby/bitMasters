package com.pizzagpt.marchesini.json;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pizzagpt.PATHS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class JsonHandler {

    private static ObjectMapper mapper = new ObjectMapper();
    private static MarchesiniInfo.Root root = getJsonRoot(PATHS.MARCHESINI_JSON, MarchesiniInfo.Root.class);

    //Leggo il file.json, dato il suo path e la classe a cui appartiene
    public static <T extends JsonInterface> T getJsonRoot(String json_path, Class<T> json_class) {
        T json_data = null;
        try {
            json_data = mapper.readValue(new File(json_path), json_class);
        } catch (StreamReadException e) {
            System.out.println("[" + e.getMessage() + "] Dati corrotti oppure formato inatteso.");
        } catch (DatabindException e) {
            System.out.println("[" + e.getMessage() + "] Problema nella conversione dal file JSON.");
        } catch (IOException e) {
            System.out.println("[" + e.getMessage() + "] Accesso al file non riuscito.");
        }
        return json_data;
    }

    private static File setUserFile(int user_id) {
        File user_json = new File(PATHS.MARCHESINI_SAVES + "user" + user_id + ".json");
        return user_json;
    }

    public static boolean existsUser(int user_id) {
        File user_json = new File(PATHS.MARCHESINI_SAVES + "user" + user_id + ".json");
        if(user_json.exists()) {
            return true;
        }
        return false;
    }

    public static int totalCategories() {
        int tot_categories = 0;
        for(MarchesiniInfo.Category cat : root.getCategories()) {
            tot_categories += 1;
        }
        return tot_categories;
    }

    public static void cleanUserJson(int user_id, int category) {
        File user_json = setUserFile(user_id);
        MarchesiniUser.Root user_root;
        MarchesiniUser.Category user_category = null;

        //Controlla se esiste la root (il file in se), altrimenti la crea
        if(!user_json.exists()) {
            user_root = new MarchesiniUser.Root();
            user_root.setCategories(new ArrayList<>());
        } else {
            user_root = getJsonRoot(user_json.getPath(), MarchesiniUser.Root.class);
        }
        //Controlla se l'utente aveva già qualche salvataggio della categoria
        for(MarchesiniUser.Category cat : user_root.getCategories()) {
            if(cat.getId() == category) {
                user_category = cat;
            }
        }
        //Se ce lo aveva, lo elimina
        if(user_category != null) {
            user_root.getCategories().remove(user_category);
        }
        //E poi crea la nuova categoria vuota
        user_category = new MarchesiniUser.Category();
        user_category.setId(category);
        user_category.setExercises(new ArrayList<>());
        user_root.getCategories().add(user_category);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(user_json, user_root);
        } catch (IOException e) {
            throw new RuntimeException("[" + e.getMessage() + "] Impossibile scrivere nel file .json dell'user.");
        }
    }

    public static void saveExercise(int user_id, int category, int exercise, String choice) {
        File user_json = setUserFile(user_id);
        MarchesiniUser.Root user_root = getJsonRoot(user_json.getPath(), MarchesiniUser.Root.class);
        for(MarchesiniUser.Category user_category : user_root.getCategories()) {
            if(user_category.getId() == category) {
                MarchesiniUser.Exercise user_exercise = new MarchesiniUser.Exercise();
                user_exercise.setId(exercise);
                user_exercise.setChoice(choice);
                user_category.getExercises().removeIf(ex -> ex.getId() == exercise);
                user_category.getExercises().add(user_exercise);
            }
        }
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(user_json, user_root);
        } catch (IOException e) {
            throw new RuntimeException("[" + e.getMessage() + "] Impossibile scrivere nel file .json dell'user.");
        }
    }

    // Controlla se l'esercizio è presente nel database degli esercizi
    //(Funzione complementare alla successiva)
    private static boolean existsExercise(MarchesiniInfo.Category user_category, int exercise) {
        if(!user_category.getExercises().isEmpty()) {
            for(MarchesiniInfo.Exercise ex : user_category.getExercises()) {
                if(ex.getId() == exercise) {
                    return true;
                }
            }
        }
        return false;
    }

    // Controlla se l'esercizio esiste
    public static boolean existsExercise(int category, int exercise) {
        boolean exists = false;
        for(MarchesiniInfo.Category cat : root.getCategories()) {
            if(cat.getId() == category) {
                exists = existsExercise(cat, exercise);
                return exists;
            }
        }
        return exists;
    }

    //Restituisce il numero totale di esercizi di una categoria
    public static int totalExercises(int category) {
        for(MarchesiniInfo.Category cat : root.getCategories()) {
            if(cat.getId() == category) {
                return cat.getExercises().size();
            }
        }
        return 0;
    }

    //Restituisce il numero totale di esercizi corretti di una categoria
    public static int correctExercises(int user_id, int category) {
        int tot_correct_answers = 0;
        MarchesiniUser.Root user_root = getJsonRoot(PATHS.MARCHESINI_SAVES + "user" + user_id + ".json", MarchesiniUser.Root.class);
        for(MarchesiniUser.Category cat : user_root.getCategories()) {
            if(cat.getId() == category) {
                for(MarchesiniUser.Exercise ex : cat.getExercises()) {
                    String correct_answer = getCorrectAnswer(category, ex.getId());
                    if(ex.getChoice().equals(correct_answer)) {
                        tot_correct_answers += 1;
                    }
                }
            }
        }
        return tot_correct_answers;
    }

    //Capisce se una risposta è corretta o sbagliata
    private static String getCorrectAnswer(int category, int exercise) {
        for(MarchesiniInfo.Category cat : root.getCategories()) {
            if(cat.getId() == category) {
                for(MarchesiniInfo.Exercise ex : cat.getExercises()) {
                    if(ex.getId() == exercise) {
                        for(MarchesiniInfo.Choice choice : ex.getChoices()) {
                            if(choice.isCorrect()) {
                                return choice.getId();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
