package com.example.notes;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Андрей on 30.05.2017.
 */

public class NoteProvider extends ContentProvider {

    private NotesHandler handler;

    private SQLiteDatabase db;

    private static final String AUTHORITY = "com.example.notes.provider";

    private static final UriMatcher sUriMatcher;

    private static final int MODE_NOTE_TABLE = 0;

    private static final int MODE_NOTE_ID = 1;

    private static final int MODE_NOTE_HEADER = 2;

    private static final int MODE_NOTE_BODY = 3;

    //private static final Uri uri;


    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, SQLiteConsts.TABLE_NAME, MODE_NOTE_TABLE);
        sUriMatcher.addURI(AUTHORITY, SQLiteConsts.PATH_TO_ID, MODE_NOTE_ID);
        sUriMatcher.addURI(AUTHORITY, SQLiteConsts.PATH_TO_HEADER, MODE_NOTE_HEADER);
        sUriMatcher.addURI(AUTHORITY, SQLiteConsts.PATH_TO_BODY, MODE_NOTE_BODY);

        //uri
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        handler.addNote(values);
        return null;
    }

    @Override
    public boolean onCreate() {
        handler = new NotesHandler(getContext());
        return true;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        NotesHandler handler = new NotesHandler(getContext());
        return null;
    }
}
