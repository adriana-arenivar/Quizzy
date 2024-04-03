// Implementing FlashcardSets, which will later include term and definitions
package com.example.quizapplicationproject;

import java.util.List;

public class FlashcardSet {
    private String setName;
    private List<Flashcard> flashcards;

    public FlashcardSet() {

    }
    public FlashcardSet(String setName, List<Flashcard> flashcards) {
        this.setName = setName;
        this.flashcards = flashcards;
    }

    public String getSetName() {
        return setName;
    }
    public List<Flashcard> getFlashcards() {
        return flashcards;
    }
    public void setSetName(String setName) {
        this.setName = setName;
    }
    public void setFlashcards(List<Flashcard> flashcards) {
        this.flashcards = flashcards;
    }
}