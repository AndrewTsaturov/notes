package com.example.notes;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Андрей on 15.04.2017.
 */

public class AppNote extends Application {

    public static ArrayList<Note> listNotes = new ArrayList<>();
    private NotesHandler notesHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("AppNOTE", "AppNote Стартовал");
        if (notesHandler == null) notesHandler = new NotesHandler(this);
        listNotes = notesHandler.loadNotes();
        if (listNotes.size() == 0) listNotes.add(new Note(getString(R.string.first_start_header),
                getString(R.string.first_start_body)));
    }

    public void saveData(int p, Note note){
        if (p != Integer.MIN_VALUE){
            listNotes.set(p, note);
            Note newNote = new Note(AppNote.listNotes.get(p).getId(), note.header, note.body);
            notesHandler.saveNote(newNote);}
        else {listNotes.add(note);
            notesHandler.addNote(note);
        }

        notesHandler.showItemsforLogs();
    }

    public void deleteNote(int p){
        notesHandler.deleteNote(listNotes.get(p));
        listNotes.remove(p);
        notesHandler.showItemsforLogs();
    }
}
