package com.example.notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Андрей on 22.05.2017.
 */

public class FragmentList extends Fragment implements NoteInterface {

    View ui;

    private Unbinder unbinder;

    @BindView(R.id.notes_list)
    RecyclerView notes;

    NotesAdapter adapter;

    FragmentInterface fragmentInterface;

    public void setOnItemInterface(StartActivity startActivity){
        fragmentInterface = startActivity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnItemInterface((StartActivity) getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_start, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_start_add) fragmentInterface.startEditor(Integer.MIN_VALUE);
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        ui = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, ui);

        setupView();

        return ui;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void itemPopup(View v, final int position, final NotesAdapter adapter){
        final int pos = position;

        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.inflate(R.menu.menu_one);
        popupMenu.setOnMenuItemClickListener(item -> {

            int a = item.getItemId();
            switch (a){
                case R.id.menu_one_item_delete:
                    deleteDialog(pos, adapter);
                    break;
                case R.id.menu_one_item_edit:
                    fragmentInterface.startEditor(pos);
                    break;
            }
            return false;
        });

        popupMenu.show();
    }

    protected void deleteDialog(int pos, final NotesAdapter adapter){
        final int p = pos;

        AlertDialog.Builder delete = new AlertDialog.Builder(getContext());
        delete.setTitle(null);
        String a = getString(R.string.alrt_delete_dialog) + " \"" + AppNote.listNotes.get(pos).getHeader() +"\"" + " ?";
        delete.setMessage(a);
        delete.setNegativeButton(R.string.alrt_delete_dialog_neg, (dialog, which) -> {

        });
        delete.setPositiveButton(R.string.alrt_delete_dialog_pos, (dialog, which) -> {
            AppNote ap = ((AppNote) getContext().getApplicationContext());
            ap.deleteNote(p);
            adapter.notifyDataSetChanged();
        });
        delete.show();
    }

    public void setupView(){
        adapter = new NotesAdapter(AppNote.listNotes);
        adapter.setOnItemInterface(this);

        notes.setLayoutManager(new LinearLayoutManager(getContext()));
        notes.setAdapter(adapter);
    }

    @Override
    public void onNoteClick(int position) {
        fragmentInterface.startEditor(position);
    }

    @Override
    public void onNoteLongClick(int position, View view) {
        itemPopup(view, position, adapter);
    }

}
