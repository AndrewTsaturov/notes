package com.example.notes.data;


public class Note {

    int id;
    String header, body;

    public Note() {
    }

    public Note(int id, String header, String body) {
        this.id = id;
        this.header = header;
        this.body = body;
    }


    public Note(String header, String body) {
        this.header = header;
        this.body = body;
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
}
