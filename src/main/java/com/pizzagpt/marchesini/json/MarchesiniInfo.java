package com.pizzagpt.marchesini.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class MarchesiniInfo {

    public static class Choice {
        @JsonProperty("id")
        private String id;
        @JsonProperty("text")
        private String text;
        @JsonProperty("correct")
        private boolean correct;

        public String getId() { return id; }
        public String getText() { return text; }
        public boolean isCorrect() { return correct; }
    }

    public static class Exercise {
        @JsonProperty("id")
        private int id;
        @JsonProperty("title")
        private String title;
        @JsonProperty("question")
        private String question;
        @JsonProperty("choices")
        private List<Choice> choices;

        public int getId() { return id; }
        public String getTitle() { return title; }
        public String getQuestion() { return question; }
        public List<Choice> getChoices() { return choices; }
    }

    public static class Category {
        @JsonProperty("id")
        private int id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("description")
        private String description;
        @JsonProperty("exercises")
        private List<Exercise> exercises;

        public int getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public List<Exercise> getExercises() { return exercises; }
    }

    public static class Root implements JsonInterface {
        @JsonProperty("categories")
        private List<Category> categories;

        public List<Category> getCategories() { return categories; }
    }
}
