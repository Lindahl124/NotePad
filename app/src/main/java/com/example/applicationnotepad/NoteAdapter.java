package com.example.applicationnotepad;

// MVC - Controller

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {

private final int layoutResource;
private NoteDeleteListener deleteListener;
private NoteEditListener editListener;

// Konstruktor för NoteAdapter som används för att visa listan med anteckningar
public NoteAdapter(Context context, int resource, List<Note> notes) {
super(context, resource, notes);
layoutResource = resource; //Layout för anteckning

// Om den här aktiviteten kan hantera borttagning av anteckningar, koppla den till deleteListener
if (context instanceof NoteDeleteListener) {
deleteListener = (NoteDeleteListener) context;
}

// Om den här aktiviteten kan hantera redigering av anteckningar, koppla den till editListener
if (context instanceof NoteEditListener) {
editListener = (NoteEditListener) context;
}
}

// Metod som skapar utseendet för varje anteckning i listan
@Override
// Om det inte finns något visuellt element för anteckningen, skapa ett nytt från layoutResource
// Hämta den aktuella anteckningen baserat på dess position
public View getView(final int position, View convertView, ViewGroup parent) {

// Om det finns en anteckning, visa dess titel och sätt upp knappar för borttagning och redigering
if (convertView == null) {
convertView = LayoutInflater.from(getContext()).inflate(layoutResource, parent, false);
}

// Hämta den specifika anteckningen från listan baserat på position
final Note note = getItem(position);

// Kolla om anteckningen är giltig (inte null)
if (note != null) {
// Visa anteckningens titel
TextView titleTextView = convertView.findViewById(R.id.titleTextView);
titleTextView.setText(note.getTitle());

ImageButton deleteButton = convertView.findViewById(R.id.deleteNoteButton);
deleteButton.setOnClickListener(v -> {
    if (deleteListener != null) {
        deleteListener.onNoteDelete(position); // Vilken anteckning som ska raderas
    }
});

ImageButton editButton = convertView.findViewById(R.id.editNoteButton);
editButton.setOnClickListener(v -> {
    // När användaren klickar på knappen, meddela aktiviteten att redigera anteckningen
    if (editListener != null) {
        editListener.onNoteEdit(position); // Vilken anteckning som ska redigeras
    }
});
}

return convertView;
}

// Ett gränssnitt för att hantera borttagning av anteckningar, vilken anteckning som ska tas bort
public interface NoteDeleteListener {
void onNoteDelete(int position);
}

// Ett gränssnitt för att hantera redigering av anteckningar,  vilken anteckning som ska tas redigeras
public interface NoteEditListener {
void onNoteEdit(int position);
}
}
