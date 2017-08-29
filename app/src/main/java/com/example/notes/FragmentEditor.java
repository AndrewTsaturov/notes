package com.example.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Андрей on 22.05.2017.
 */

public class FragmentEditor extends Fragment {
    boolean mDualPane;

    int checkNote = Integer.MIN_VALUE;

    String header;
    String body;

    @BindView(R.id.note_edit_header) EditText headerEdit;
    @BindView(R.id.note_edit_body) EditText bodyEdit;

    Unbinder unbinder;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View listOfNotes = getActivity().findViewById(R.id.ui);

        mDualPane = listOfNotes != null && listOfNotes.getVisibility() == View.VISIBLE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ui = inflater.inflate(R.layout.fragment_editor, container, false);

        unbinder = ButterKnife.bind(this, ui);

        checkNote = getNoteIndex();
        if (checkNote != Integer.MIN_VALUE){
            headerEdit.setText(AppNote.listNotes.get(checkNote).getHeader());
            bodyEdit.setText(AppNote.listNotes.get(checkNote).getBody());
        }

        return ui;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        unbinder.unbind();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_edit_save:
                header = headerEdit.getText().toString();
                body = bodyEdit.getText().toString();
                if(header.matches("") || body.matches("")) cacelDialog();
                else {
                    saveDialog(checkNote, header, body);
                }
                break;
            case R.id.menu_edit_delete:
                if (AppNote.listNotes.size() != 0)
                    deleteDialog(checkNote);
                else cacelDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cacelDialog(){
        AlertDialog.Builder cancel = new AlertDialog.Builder(getContext());
        cancel.setTitle(null);
        cancel.setMessage(R.string.dialog_cancel_message);
        cancel.setNegativeButton(R.string.dialog_cancel_neg, (dialog, which) -> stopEditor());
        cancel.setPositiveButton(R.string.dialog_cancel_pos, (dialog, which) -> {
        });
        cancel.show();
    }

    protected void deleteDialog(int pos){
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
            stopEditor();
        });
        delete.show();
    }

    protected void saveDialog(int pos, final String header, final String body){
        final int p = pos;
        AlertDialog.Builder save = new AlertDialog.Builder(getContext());
        save.setTitle(null);
        String a = getString(R.string.dialog_save_msg) + "\"" + header +"\"" + " ?";
        save.setMessage(a);
        save.setNegativeButton(R.string.dialog_save_neg, (dialog, which) -> {

        });
        save.setPositiveButton(R.string.dialog_save_pos, (dialog, which) -> {
            AppNote ap = ((AppNote) getContext().getApplicationContext());
            ap.saveData(p, new Note(header, body));
            stopEditor();
        });
        save.show();
    }
    public void stopEditor(){
        if(mDualPane){
            FragmentList list = new FragmentList();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.ui, list);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
        else {
            Intent intent = new Intent();
            intent.setClass(getContext(), StartActivity.class);
            startActivity(intent);
        }
    }
    public static FragmentEditor newInstance(int index){
        FragmentEditor f = new FragmentEditor();
        Bundle args = new Bundle();
        args.putInt(Constants.COUNT, index);
        f.setArguments(args);

        return f;
    }
    public int getNoteIndex(){
        return getArguments().getInt(Constants.COUNT);
    }


}
