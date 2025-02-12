package com.example.applicationnotepad;

// MVC - Controller

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteAdapter.NoteDeleteListener, NoteAdapter.NoteEditListener {

    private ListView listView;
    private List<Note> noteList;
    private NoteAdapter adapter;
    private ActivityResultLauncher<Intent> addNoteLauncher;
    private ActivityResultLauncher<Intent> editNoteLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewNotes);

        // Ladda anteckningar från SharedPreferences när appen startar
        noteList = NoteSharedPreferences.getNotes(this); // Hämta anteckningar från SharedPreferences
        adapter = new NoteAdapter(this, R.layout.list_item_note, noteList);
        listView.setAdapter(adapter);

        // Hitta och sätt upp knappen för att skapa nya anteckningar
        Button createNoteButton = findViewById(R.id.buttonCreateNote);
        createNoteButton.setOnClickListener(view -> {

            // Öppna aktiviteten för att lägga till anteckningar när knappen klickas
            Intent intent = new Intent(MainActivity.this, MainActivityAddNote.class);
            addNoteLauncher.launch(intent);
        });


        // Visa detaljer för vald anteckning
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Hämta den valda anteckningen
            Note selectedNote = noteList.get(position);

            // Skapa en ny intent för att öppna redigeringsaktiviteten
            Intent editIntent = new Intent(MainActivity.this, MainActivityEditNote.class);

            // Skicka informationen om den valda anteckningen till redigeringsaktiviteten
            editIntent.putExtra("title", selectedNote.getTitle());
            editIntent.putExtra("description", selectedNote.getDescription());

            // Starta redigeringsaktiviteten
            editNoteLauncher.launch(editIntent);
        });


        // Skapa launchers för att hantera resultat från andra aktiviteter
        addNoteLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Hantera skapandet av en ny anteckning efter att användaren har lagt till den
                        Intent data = result.getData();
                        String title = data.getStringExtra("title");
                        String description = data.getStringExtra("description");

                        // Skapa en ny anteckning med titel och beskrivning
                        Note newNote = new Note(title, description);

                        // Lägg till den nya anteckningen i listan och uppdatera adaptern
                        noteList.add(newNote);
                        adapter.notifyDataSetChanged(); // Ändrat och uppdaterat gränssnittet
                        // Spara anteckningar i SharedPreferences efter att ha lagt till en ny anteckning
                        NoteSharedPreferences.saveNotes(MainActivity.this, noteList);
                    }
                });


        // Startar en redigeringsaktivitet och tar emot resultatet från aktiviteten
        // Använder ActivityResultCallback<ActivityResult> för att hantera resultatet
        editNoteLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Hantera uppdatering av en befintlig anteckning efter redigering
                        Intent data = result.getData();
                        String updatedTitle = data.getStringExtra("updatedTitle");
                        String updatedDescription = data.getStringExtra("updatedDescription");
                        String originalTitle = data.getStringExtra("originalTitle");

                        // Här behöver du hitta den rätta anteckningen i noteList och uppdatera beskrivningen och titeln
                        for (Note note : noteList) {
                            if (note.getTitle().equals(originalTitle)) {
                                note.setTitle(updatedTitle);
                                note.setDescription(updatedDescription);
                                adapter.notifyDataSetChanged(); // Ändrat och uppdaterat gränssnittet
                                break;
                            }
                        }

                        // Spara anteckningar i SharedPreferences efter att ha redigerat en anteckning
                        NoteSharedPreferences.saveNotes(MainActivity.this, noteList);
                    }
                });
    }

    // Implementera metoder för att hantera borttagning och redigering av anteckningar
    @Override
    public void onNoteDelete(int position) {

        // Hantera borttagning av anteckningen vid den angivna positionen
        if (position >= 0 && position < noteList.size()) {
            noteList.remove(position);
            adapter.notifyDataSetChanged(); // Datan har ändrats

            // Spara anteckningar i SharedPreferences efter att ha tagit bort en anteckning
            NoteSharedPreferences.saveNotes(this, noteList);
        }
    }

    @Override
    public void onNoteEdit(int position) {

        // Hämta den valda anteckningen från listan baserat på positionen
        Note selectedNote = noteList.get(position);

        // Skapa en ny intent för att öppna redigeringsaktiviteten
        Intent editIntent = new Intent(MainActivity.this, MainActivityEditNote.class);

        // Skicka informationen om den valda anteckningen till redigeringsaktiviteten
        editIntent.putExtra("title", selectedNote.getTitle());
        editIntent.putExtra("description", selectedNote.getDescription());

        // Starta redigeringsaktiviteten
        editNoteLauncher.launch(editIntent);
    }
}
