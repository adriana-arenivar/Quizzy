package com.example.quizapplicationproject;

import java.util.ArrayList;
import java.util.List;

// Combines the concepts of a Set and Flashcard into a cohesive class structure
public class FlashcardSet {
    private String setName;
    private final List<Flashcard> flashcards; // Collection of Flashcard objects

    // Inner class representing a Flashcard
    public static class Flashcard {
        private String term, definition;

        // Constructors
        public Flashcard() {}

        public Flashcard(String term, String definition) {
            this.term = term;
            this.definition = definition;
        }

        // Getters and Setters
        public String getTerm() {
            return term;
        }

        public void setTerm(String term) {
            this.term = term;
        }

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }
    }

    // Constructor for FlashcardSet
    public FlashcardSet(String setName) {
        this.setName = setName;
        this.flashcards = new ArrayList<>();
    }

    // Getters and Setters for setName
    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    // Methods to manipulate the flashcards list
    public void addFlashcard(Flashcard flashcard) {
        flashcards.add(flashcard);
    }

    public boolean removeFlashcard(Flashcard flashcard) {
        return flashcards.remove(flashcard);
    }

    // Getter for the flashcards list
    public List<Flashcard> getFlashcards() {
        return flashcards;
    }
}
