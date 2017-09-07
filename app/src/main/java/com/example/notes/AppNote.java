package com.example.notes;

import android.app.Application;
import android.content.Context;

import com.example.notes.data.Note;
import com.example.notes.data.NotesDbHandler;
import com.example.notes.utils.LogUtils;

import java.util.ArrayList;

public class AppNote extends Application {

    /** Handler это классы менеджеры, здесь у тебя не NoteHandler а скорей всего NoteDbHandler
     * иначе думаешь что это менеджер над моделью Note
     */
    private NotesDbHandler notesDbHandler;
    public static volatile Context appContext;
    //TODO--> static здесь плохо, но для примера пойдет
    public static ArrayList<Note> listNotes = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();

        LogUtils.E("AppNote onCreate");

        appContext = getApplicationContext();
        if (notesDbHandler == null) notesDbHandler = new NotesDbHandler(this);
        listNotes = notesDbHandler.loadNotes();
    }

    //TODO--> группируй код в блоки, расставляй брейсы(скобки) правильно
    //TODO--> int p; именуй переменные правильно, переменная должна говорить сама за себя
    public void saveData(int position, Note note){

        if (position != Integer.MIN_VALUE && listNotes.size() > 0) {
            listNotes.set(position, note);
            //используй geter и setter это старые уроки
            Note newNote = new Note(AppNote.listNotes.get(position).getId(), note.getHeader(), note.getBody());
            notesDbHandler.saveNote(newNote);
        }

        else {
            listNotes.add(note);
            notesDbHandler.addNote(note);
        }

        notesDbHandler.showItemsForLogs();
    }

    //TODO--> int p; именуй переменные правильно, переменная должна говорить сама за себя
    public void deleteNote(int position){
        notesDbHandler.deleteNote(listNotes.get(position));
        listNotes.remove(position);
        notesDbHandler.showItemsForLogs();
    }
}
