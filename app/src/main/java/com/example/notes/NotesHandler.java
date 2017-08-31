package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Андрей on 24.05.2017.
 */

public class NotesHandler extends SQLiteOpenHelper {

    public NotesHandler(Context context) {
        super(context, SQLiteConsts.DATABASE_NAME, null, SQLiteConsts.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL(SQLiteConsts.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL(SQLiteConsts.UPDATE);
        onCreate(db);
    }


    public void saveNote(Note note){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();

        String whereClause = SQLiteConsts.KEY_ID + "=" + note.getId();

        cv.put(SQLiteConsts.HEADER_ID, note.getHeader());
        cv.put(SQLiteConsts.BODY_ID, note.getBody());

        db.update(SQLiteConsts.TABLE_NAME, cv, whereClause, null);
        db.close();
    }
    public void addNote(Note note){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(SQLiteConsts.HEADER_ID, note.getHeader());
        cv.put(SQLiteConsts.BODY_ID, note.getBody());

        db.insert(SQLiteConsts.TABLE_NAME, null, cv);
        db.close();
    }

    public void addNote(ContentValues cv){
        SQLiteDatabase db = getWritableDatabase();
        db.insert(SQLiteConsts.TABLE_NAME, null, cv);
    }

    public ArrayList<Note> loadNotes(){
        ArrayList<Note> notes = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(SQLiteConsts.GET_TABLE_FOR_CURSOR, null);
        while(cursor.moveToNext()){
            Note note = new Note();
            note.setId(cursor.getInt(0));
            note.setHeader(cursor.getString(1));
            note.setBody(cursor.getString(2));
            notes.add(note);
        }

        return notes;
    }
    public void deleteNote(Note note){
        Log.d("DB", "DELETING_NOTE_STARTED");
        SQLiteDatabase db = getWritableDatabase();
        int keySQL = note.getId();
        Log.d("DB", "DELETING_NOTE_ATTEMPT");
        db.delete(SQLiteConsts.TABLE_NAME, SQLiteConsts.KEY_ID + "=" + keySQL, null);
        db.close();
        Log.d("DB", "RESULT");
    }
    public void showItemsforLogs(){
        Log.d("DB", "NOTES;");
        Log.d("DB","..................................................");
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(SQLiteConsts.GET_TABLE_FOR_CURSOR, null);
        while (cursor.moveToNext()){
            Log.d("DB", "... " + cursor.getInt(0) + " " + cursor.getString(1) + " " + cursor.getString(2));
        }
    }
}
