package com.example.notes;

/**
 * Created by Андрей on 24.05.2017.
 */

public interface SQLiteConsts {
    int DB_VERSION = 1;
    String DATABASE_NAME = "NOTES_DB";
    String TABLE_NAME = "notes";
    String KEY_ID = "id";
    String HEADER_ID = "header";
    String BODY_ID = "body";
    String CREATE_TABLE = "CREATE TABLE notes (id INTEGER PRIMARY KEY, header TEXT, body TEXT)";
    String UPDATE = "DROP TABLE IF EXISTS notes";
    String GET_TABLE_FOR_CURSOR = "SELECT * FROM notes";
    String PATH_TO_ID = "notes/id";
    String PATH_TO_HEADER = "notes/header";
    String PATH_TO_BODY = "notes/body";


}
