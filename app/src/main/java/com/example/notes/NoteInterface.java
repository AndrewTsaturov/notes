package com.example.notes;

import android.os.Bundle;
import android.view.View;

/**
 * Created by Дом on 29.08.2017.
 */

 interface NoteInterface {

    void onNoteClick(int position);
    void onNoteLongClick(int position, View v);
}
