package com.example.notes.ui.inerfaces;

import com.example.notes.data.Note;

/**
 * Created by Дом on 31.08.2017.
 */

public interface FragmentInterface {
    void showEditorFragment(int position);
    void hideEditorFragment();
    void saveNote(int position, Note note);
    void deleteNote(int position);
}
