package com.example.notes;

/**
 * Created by Дом on 31.08.2017.
 */

public interface FragmentInterface {
    void startEditor(int position);
    void stopEditor();
    void saveNote(int position, Note note);
    void deleteNote(int position);
}
