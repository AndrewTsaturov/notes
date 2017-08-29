package com.example.notes;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Андрей on 13.04.2017.
 */

public class NotesAdapterOne extends BaseAdapter {
    private ArrayList<Note> notes = new ArrayList<>();

    public NotesAdapterOne(ArrayList<Note> notes) {
        this.notes = notes;
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rootView = convertView;
        if(rootView == null){
            rootView = View.inflate(parent.getContext(),R.layout.list_item, null);}
            TextView header = (TextView) rootView.findViewById(R.id.note_header);
            TextView body = (TextView) rootView.findViewById(R.id.note_body);
            header.setText(notes.get(position).getHeader());
            body.setText(notes.get(position).getBody());
            return rootView;
    }
}
