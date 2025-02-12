package com.example.applicationnotepad;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class NoteSharedPreferences {
    private static final String PREF_NAME = "NotePrefs";
    private static final String NOTE_KEY = "notes";

    // Spara anteckningar i SharedPreferences
    public static void saveNotes(Context context, List<Note> notes) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String notesJson = new Gson().toJson(notes);
        editor.putString(NOTE_KEY, notesJson);
        editor.apply();
    }

    // Hämta anteckningar från SharedPreferences
    public static List<Note> getNotes(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String notesJson = sharedPreferences.getString(NOTE_KEY, null);
        List<Note> notes = new ArrayList<>();
        if (notesJson != null) {
            notes = new Gson().fromJson(notesJson, new TypeToken<List<Note>>() {}.getType());
        }
        return notes;
    }
}


