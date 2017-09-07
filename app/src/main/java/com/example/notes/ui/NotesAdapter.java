package com.example.notes.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.notes.R;
import com.example.notes.data.Note;
import com.example.notes.ui.inerfaces.NoteInterface;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Дом on 29.08.2017.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private ArrayList<Note> list;

    private NoteInterface noteInterface;

    public void setOnItemInterface(NoteListFragment editor){
        noteInterface = editor;
    }

    public NotesAdapter(ArrayList<Note> list) {
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note itemNote = list.get(position);
        holder.header.setText(itemNote.getHeader());
        holder.body.setText(itemNote.getBody());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.note_header) public TextView header;
        @BindView(R.id.note_body) public TextView body;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            //TODO ==> Inject interface fo onItemClick

            view.setOnClickListener(v -> noteInterface.onNoteClick(getAdapterPosition()));
            view.setOnLongClickListener(v -> {
                noteInterface.onNoteLongClick(getAdapterPosition(), view);
                return true;
            });
        }
    }
}
