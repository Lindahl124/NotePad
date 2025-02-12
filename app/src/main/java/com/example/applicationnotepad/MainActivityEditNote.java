package com.example.applicationnotepad;

// MVC - Controller

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivityEditNote extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private String originalTitle; // Sparar den ursprungliga titeln

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_edit_note);

        titleEditText = findViewById(R.id.editTextTitle);
        descriptionEditText = findViewById(R.id.editTextDescription);
        Button editNoteButton = findViewById(R.id.buttonEditNewNote);

        // Hämta ursprunglig titel från intent
        originalTitle = getIntent().getStringExtra("title");
        titleEditText.setText(originalTitle);

        // Hämta ursprunglig beskrivning från intent
        String originalDescription = getIntent().getStringExtra("description");
        descriptionEditText.setText(originalDescription);

        editNoteButton.setOnClickListener(view -> {

            // Hämta uppdaterad titel och beskrivning från användaren
            String updatedTitle = titleEditText.getText().toString();
            String updatedDescription = descriptionEditText.getText().toString();

            // Skapa en ny intent för att skicka tillbaka uppdaterad information
            Intent resultIntent = new Intent();
            resultIntent.putExtra("updatedTitle", updatedTitle);
            resultIntent.putExtra("updatedDescription", updatedDescription);

            // Skicka även med den ursprungliga titeln för att identifiera vilken anteckning som ska uppdateras
            resultIntent.putExtra("originalTitle", originalTitle);

            // Ange resultatet som RESULT_OK och skicka tillbaka intent
            setResult(RESULT_OK, resultIntent); //Avslutats och har producerat ett önskat resultat
            finish();
        });



    }
}

