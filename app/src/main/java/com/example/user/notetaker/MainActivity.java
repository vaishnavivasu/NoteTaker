package com.example.user.notetaker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView nNotesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nNotesList = findViewById(R.id.note_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notes,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_new_note:
                startActivity(new Intent(this,NotesActivity.class));
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        nNotesList.setAdapter(null);
        ArrayList<Note> notes = Utilities.getAllSaved(this);

        if(notes == null || notes.size()==0){
            Toast.makeText(getApplicationContext(),"No notes to show",Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            NoteAdapter na = new NoteAdapter(this, R.id.note_item,notes);
            nNotesList.setAdapter(na);

            nNotesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String fileName = ((Note)nNotesList.getItemAtPosition(i)).getnDateTime() + Utilities.FILE_EXTENSION;
                    Intent intent = new Intent(getApplicationContext(),NotesActivity.class);
                    intent.putExtra("NOTE_FILE",fileName);
                    startActivity(intent);
                }

            });
        }
    }
}