package com.example.notes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

    int checkNote = Integer.MIN_VALUE;

    String header;
    String body;

    boolean clearMenu;

    @BindView(R.id.note_edit_header) EditText headerEdit;
    @BindView(R.id.note_edit_body) EditText bodyEdit;

    Unbinder unbinder;

    FragmentInterface fragmentInterface;


    public void setCheckNote(int checkNote) {
        this.checkNote = checkNote;
    }

    public void setClearMenu(boolean clearMenu) {
        this.clearMenu = clearMenu;
    }

    public void setOnItemInterface(StartActivity startActivity){
        fragmentInterface = startActivity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setOnItemInterface((StartActivity) getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View ui = inflater.inflate(R.layout.fragment_editor, container, false);

        unbinder = ButterKnife.bind(this, ui);

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
        if(clearMenu) menu.clear();

        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        header = headerEdit.getText().toString();
        body = bodyEdit.getText().toString();

        boolean emptyTextFields;

        switch (item.getItemId()){

            case R.id.menu_edit_save:
                emptyTextFields = header.matches("") || body.matches("");

                if(emptyTextFields) cancelDialog();

                else {
                    saveDialog(checkNote, header, body);
                }
                break;

            case R.id.menu_edit_delete:
                emptyTextFields = header.matches("") || body.matches("");

                if (AppNote.listNotes.size() != 0 && !emptyTextFields)
                    deleteDialog(checkNote);
                else cancelDialog();

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cancelDialog(){
        AlertDialog.Builder cancel = new AlertDialog.Builder(getContext());
        cancel.setTitle(null);
        cancel.setMessage(R.string.dialog_cancel_message);
        cancel.setNegativeButton(R.string.dialog_cancel_neg, (dialog, which) -> fragmentInterface.stopEditor());
        cancel.setPositiveButton(R.string.dialog_cancel_pos, (dialog, which) -> {
        });
        cancel.show();
    }

    protected void deleteDialog(int pos){
        final int p = pos;
        AlertDialog.Builder delete = new AlertDialog.Builder(getContext());
        delete.setTitle(null);

        String msg = getString(R.string.alrt_delete_dialog) + " \"" +
                AppNote.listNotes.get(pos).getHeader() +"\"" + " ?";

        delete.setMessage(msg);
        delete.setNegativeButton(R.string.alrt_delete_dialog_neg, (dialog, which) -> {
        });
        delete.setPositiveButton(R.string.alrt_delete_dialog_pos, (dialog, which) -> {
            fragmentInterface.deleteNote(p);
            fragmentInterface.stopEditor();
        });

        delete.show();
    }

    protected void saveDialog(int pos, final String header, final String body){
        final int p = pos;
        AlertDialog.Builder save = new AlertDialog.Builder(getContext());
        save.setTitle(null);

        String msg = getString(R.string.dialog_save_msg) + "\"" + header +"\"" + " ?";

        save.setMessage(msg);
        save.setNegativeButton(R.string.dialog_save_neg, (dialog, which) -> {

        });
        save.setPositiveButton(R.string.dialog_save_pos, (dialog, which) -> {
            fragmentInterface.saveNote(p, new Note(header, body));
        });
        save.show();
    }

}
