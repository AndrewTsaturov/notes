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
import android.widget.ListView;
import java.util.ArrayList;


public class StartActivity extends AppCompatActivity {
    ListView notes;
    public static int noteflag = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentList list = new FragmentList();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.ui, list);
        ft.commit();
        Log.d("....", "Activity Created");
        NotesHandler h = new NotesHandler(this);
        h.showItemsforLogs();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    //костыль с финишом
    @Override
    public void onBackPressed() {
        this.finish();
    }

}
