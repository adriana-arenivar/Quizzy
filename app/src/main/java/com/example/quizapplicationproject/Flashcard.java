// Creates term and definition(flashcard) for the FlashcardSet

package com.example.quizapplicationproject;

public class Flashcard  {
    private String term, definition;

    public Flashcard() {
    }

    public Flashcard(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }
    // get term and definition
    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    // setting term and definition
    public void setTerm(String term) {
        this.term = term;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}

