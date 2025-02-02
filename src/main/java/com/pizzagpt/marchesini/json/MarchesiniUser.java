package com.pizzagpt.marchesini.json;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MarchesiniUser {

    public static class Exercise {
        @JsonProperty("id")
        private int id;
        @JsonProperty("choice")
        private String choice;
        @JsonBackReference
        private Category parent;

        public int getId() { return id; }
        public String getChoice() { return choice; }
        public Category getParent() { return parent; }

        public void setId(int id) { this.id = id; }
        public void setChoice(String choice) { this.choice = choice; }
        public void setParent(Category parent) { this.parent = parent; }
    }

    public static class Category {
        @JsonProperty("id")
        private int id;
        @JsonProperty("exercises")
        private List<Exercise> exercises;
        @JsonBackReference
        private Root parent;

        public int getId() { return id; }
        public List<Exercise> getExercises() { return exercises; }
        public Root getParent() { return parent; }

        public void setId(int id) { this.id = id; }
        public void setExercises(List<Exercise> exercises) { this.exercises = exercises; }
        public void setParent(Root parent) { this.parent = parent; }
    }

    public static class Root implements JsonInterface {
        @JsonProperty("categories")
        private List<Category> categories;

        public List<Category> getCategories() { return categories; }
        public void setCategories(List<Category> categories) { this.categories = categories; }
    }
}
