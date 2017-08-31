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

    boolean mDualPane;

    boolean editorIsWorking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO ==> Here: ButterKnife doesn't work and calls the app crush
        editorUI = (FrameLayout) findViewById(R.id.land_editor);

        initUI();
    }

    private void initUI(){

        list = new FragmentList();

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
//        ft = manager.beginTransaction();
//        ft.remove(list);
//        ft.remove(editor);
//        ft.commit();
    }

    @Override
    public void startEditor(int position) {
        editorIsWorking = true;

        editor = new FragmentEditor();

        editor.setCheckNote(position);

        ft = manager.beginTransaction();

        if(mDualPane){
            ft.add(R.id.land_editor, editor);
            ft.commit();
        }
        else {
            ft.replace(R.id.ui, editor);
            ft.commit();
        }
    }

    @Override
    public void stopEditor() {

        editorIsWorking = false;

        list = new FragmentList();

        ft = manager.beginTransaction();
        ft.replace(R.id.ui, list);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
}
