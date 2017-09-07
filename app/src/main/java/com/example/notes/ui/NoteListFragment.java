package com.example.notes.ui;

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

import com.example.notes.AppNote;
import com.example.notes.R;
import com.example.notes.ui.inerfaces.FragmentInterface;
import com.example.notes.ui.inerfaces.NoteInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


//TODO--> почему fragmentList ???
public class NoteListFragment extends Fragment implements NoteInterface {

    private Unbinder unbinder;

    @BindView(R.id.notes_list) RecyclerView notes;

    boolean clearMenu;
    public NotesAdapter adapter;
    private FragmentInterface fragmentInterface;


    //TODO--> всё в кучу методы классы Callbacks очень по структуре плохо
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
        if (clearMenu) menu.clear();
        inflater.inflate(R.menu.menu_start, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_start_add) fragmentInterface.showEditorFragment(Integer.MIN_VALUE);
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        setupView();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //--> show popup
    public void showPopup(View v, final int position, final NotesAdapter adapter){
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.inflate(R.menu.menu_one);
        popupMenu.setOnMenuItemClickListener(item -> {

            int a = item.getItemId();
            switch (a){
                case R.id.menu_one_item_delete:
                    showDeleteDialog(position, adapter);
                    break;
                case R.id.menu_one_item_edit:
                    fragmentInterface.showEditorFragment(position);
                    break;
            }
            return false;
        });

        popupMenu.show();
    }

    protected void showDeleteDialog(int pos, final NotesAdapter adapter){

        AlertDialog.Builder delete = new AlertDialog.Builder(getContext());
        delete.setTitle(null);
        String a = getString(R.string.alrt_delete_dialog) + " \"" + AppNote.listNotes.get(pos).getHeader() +"\"" + " ?";
        delete.setMessage(a);
        delete.setNegativeButton(R.string.alrt_delete_dialog_neg, (dialog, which) -> {

        });
        delete.setPositiveButton(R.string.alrt_delete_dialog_pos, (dialog, which) -> {
            fragmentInterface.deleteNote(pos);
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
        fragmentInterface.showEditorFragment(position);
    }

    @Override
    public void onNoteLongClick(int position, View view) {
        showPopup(view, position, adapter);
    }


    public void setClearMenu(boolean clearMenu) {
        this.clearMenu = clearMenu;
    }

    public void setOnItemInterface(StartActivity startActivity){
        fragmentInterface = startActivity;
    }
}
