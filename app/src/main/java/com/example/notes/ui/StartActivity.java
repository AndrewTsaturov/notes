package com.example.notes.ui;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.notes.AppNote;
import com.example.notes.R;
import com.example.notes.data.Note;
import com.example.notes.ui.inerfaces.FragmentInterface;
import com.example.notes.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

//TODO--> вверху была куча неиспользуемых импортов - это плохо, порой очень
public class StartActivity extends AppCompatActivity implements FragmentInterface {

    //TODO--> используй private для локальных переменных класса или ButterKnife

    @Nullable @BindView(R.id.land_editor) FrameLayout editorUI;

    private AppNote ap;
    private NoteListFragment list;
    private EditorNoteFragment editor;
    private FragmentTransaction ft;

    boolean mDualPane, isEditorWorking;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //--> В верстке всё очень плохо, без комментариев */
        setContentView(R.layout.activity_main);


        ap = ((AppNote) getApplicationContext());

        //--> Это хороший тон чтоб не заливать onCreate
        initUI();
    }

    //--> а это уже очень плохо init это инициализация и всё, остальное это сеттинг или что-то другое
    private void initUI(){
        ButterKnife.bind(this);


        //--->
        attachNoteListFragment();
        setFragmentEditor();
        //<---

        LogUtils.E("UI initialized");

        attachDualPlane();
    }

    private void attachDualPlane() {

        mDualPane = editorUI != null && editorUI.getVisibility() == View.VISIBLE;

        if(mDualPane){
            ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.land_editor, editor);
            ft.commit();
        }
    }

    private void setFragmentEditor() {
        editor = new EditorNoteFragment();
    }

    private void attachNoteListFragment() {

        list = new NoteListFragment();
        list.setClearMenu(true);

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.ui, list);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if(!mDualPane && isEditorWorking){
            list.setClearMenu(true);
            hideEditorFragment();
        }
        else this.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void showEditorFragment(int position) {
        isEditorWorking = true;

        editor.setCheckNote(position);

        if(mDualPane){
            landscapeEditorLaunch(position);
        }
        else {
            editor = new EditorNoteFragment();
            editor.setClearMenu(true);
            editor.setCheckNote(position);

            ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.ui, editor);
            ft.commit();
        }
    }

    @Override
    public void hideEditorFragment() {

        isEditorWorking = false;

        if(mDualPane){
            list.adapter.notifyDataSetChanged();
        }
        else {
            list = new NoteListFragment();

            ft = getSupportFragmentManager().beginTransaction();
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
        else hideEditorFragment();
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
