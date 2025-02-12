package com.example.applicationnotepad;

// MVC - Model

public class Note {
    private String title;
    private String description;

    // Konstruktorn skapar en ny anteckning genom att ta emot två värden, en titel och en beskrivning
    public Note(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // Getter-metod för att hämta titeln på anteckningen.
    public String getTitle() {
        return title;
    }

    // Setter-metod för att sätta titeln på anteckningen.
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}







