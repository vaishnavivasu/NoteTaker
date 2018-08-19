package com.example.user.notetaker;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NotesActivity extends AppCompatActivity {

    private EditText eTitle;
    private EditText eContent;

    private String nFileName;

    private Note nLoadedNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        eTitle = findViewById(R.id.notes_title);
        eContent = findViewById(R.id.notes_text);
        nFileName = getIntent().getStringExtra("NOTE_FILE");
        if(nFileName != null){
            nLoadedNote = Utilities.getNoteByName(getBaseContext(),nFileName);
            eTitle.setText(nLoadedNote.getnTitle());
            eContent.setText(nLoadedNote.getnContent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_new,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note :
                saveNotes();
                break;
            case R.id.opt_note :
                deleteNotes();
                break;
        }

        return true;
    }

    private void deleteNotes() {
        if(nLoadedNote == null){
            finish();
        }
        else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this).setTitle("delete")
                    .setMessage("Are you sure to delete "+eTitle.getText().toString()+"?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Utilities.deleteNote(getApplicationContext(),nLoadedNote.getnDateTime()+Utilities.FILE_EXTENSION);
                            Toast.makeText(getApplicationContext(),"Deleted",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).setNegativeButton("NO",null).setCancelable(false);
            dialog.show();


        }
    }

    private void saveNotes(){
        Note note;
        if(nLoadedNote == null) {
            note = new Note(System.currentTimeMillis(), eTitle.getText().toString(), eContent.getText().toString());
        }
        else{
            note = new Note(nLoadedNote.getnDateTime(), eTitle.getText().toString(), eContent.getText().toString());
        }
        if(Utilities.saveNote(this,note))
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Error in saving",Toast.LENGTH_SHORT).show();
        finish();
    }
}
