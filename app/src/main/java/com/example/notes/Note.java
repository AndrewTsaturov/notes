package com.example.notes;

import java.util.ArrayList;

/**
 * Created by Андрей on 13.04.2017.
 */

public class Note {
    int id;

    public Note(int id, String header, String body) {
        this.id = id;
        this.header = header;
        this.body = body;
    }

    String header, body;

    public Note(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public Note() {
    }

    public int getId() {
        return id;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static String parseToString(Note note){
        String parcedNote = note.getHeader() + "\n" + note.getBody();
        return parcedNote;
    }

    public static String parseToString(Note[] notes){
        String parsedNote = "";
        for (int i = 0; i < notes.length; i++)
            parsedNote = parsedNote + notes[i].getHeader() + "\n" + notes[i].getBody() + "\n";
        return parsedNote;
    }

    public static String parseToString(String a, String b){
        String parsedString = a + "\n" + b;
        return parsedString;
    }
}
