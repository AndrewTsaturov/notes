package com.example.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StartActivity extends AppCompatActivity implements FragmentInterface {

    FrameLayout editorUI;

    FragmentList list;

    FragmentEditor editor;

    FragmentManager manager;

    FragmentTransaction ft;

    boolean mDualPane, editorIsWorking;

    AppNote ap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO ==> Here: ButterKnife doesn't work and calls the app crush
        editorUI = (FrameLayout) findViewById(R.id.land_editor);

        ap = ((AppNote) getApplicationContext());

        initUI();
    }

    private void initUI(){

        list = new FragmentList();
        list.setClearMenu(true);

        editor = new FragmentEditor();

        manager = getSupportFragmentManager();

        ft = manager.beginTransaction();
        ft.add(R.id.ui, list);
        ft.commit();


        mDualPane = editorUI != null && editorUI.getVisibility() == View.VISIBLE;

        if(mDualPane){
            ft = manager.beginTransaction();
            ft.add(R.id.land_editor, editor);
            ft.commit();
            Log.d("Проверка", "сработало");
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    //костыль с финишом
    @Override
    public void onBackPressed() {
        if(!mDualPane && editorIsWorking)
            stopEditor();
        else this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void startEditor(int position) {
        editorIsWorking = true;

        editor.setCheckNote(position);

        if(mDualPane){
            landscapeEditorLaunch(position);
        }
        else {
            editor = new FragmentEditor();
            editor.setCheckNote(position);

            ft = manager.beginTransaction();
            ft.replace(R.id.ui, editor);
            ft.commit();
        }
    }

    @Override
    public void stopEditor() {

        editorIsWorking = false;

        if(mDualPane){
            list.adapter.notifyDataSetChanged();
        }
        else {
            list = new FragmentList();

            ft = manager.beginTransaction();
            ft.replace(R.id.ui, list);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }

    @Override
    public void saveNote(int position, Note note) {
        ap.saveData(position, note);
        if (editor.checkNote == Integer.MIN_VALUE && mDualPane){
            editor.setCheckNote(AppNote.listNotes.size() - 1);
            list.adapter.notifyDataSetChanged();
        }
        else stopEditor();
    }

    @Override
    public void deleteNote(int position) {
        ap.deleteNote(position);

        if(mDualPane){
            position = position - 1;

            if(position >= 0){
            editor.setCheckNote(position);

            landscapeEditorLaunch(position);
            }
            else landscapeEditorLaunch(Integer.MIN_VALUE);
        }
    }

    private void landscapeEditorLaunch(int position){
        if(position != Integer.MIN_VALUE){
            editor.headerEdit.setText(AppNote.listNotes.get(position).getHeader());
            editor.bodyEdit.setText(AppNote.listNotes.get(position).getBody());
        }
        else {
            editor.headerEdit.setText("");
            editor.bodyEdit.setText("");
        }
    }
}
