package com.example.notes.ui.inerfaces;

import android.view.View;

public interface NoteInterface {

    void onNoteClick(int position);
    void onNoteLongClick(int position, View v);
}
