package com.example.applicationnotepad;

// MVC - Controller

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivityAddNote extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_add_note);

        titleEditText = findViewById(R.id.editTextTitle);
        descriptionEditText = findViewById(R.id.editTextDescription);

        Button addNoteButton = findViewById(R.id.buttonAddNewNote);
        addNoteButton.setOnClickListener(view -> {

            // Hämta titel och beskrivning från användaren
            String title = titleEditText.getText().toString();
            String description = descriptionEditText.getText().toString();

            // Skicka data tillbaka till huvudaktiviteten
            // Lägga till data om "title" och "description" i Intent-objektet
            Intent intent = new Intent();
            intent.putExtra("title", title);
            intent.putExtra("description", description);
            setResult(RESULT_OK, intent);
            finish(); // Stänger den nuvarande aktiviteten
        });

    }
}


