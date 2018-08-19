package com.example.user.notetaker;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Note> notes) {
        super(context, resource, notes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_note,null);
        }

        Note note = getItem(position);

        if(note != null){
            TextView title = convertView.findViewById(R.id.note_item);
            TextView date = convertView.findViewById(R.id.note_item_date);
            TextView content = convertView.findViewById(R.id.note_item_content);

            title.setText(note.getnTitle());
            date.setText(note.getDateTimeFormatted(getContext()));
            if(note.getnContent().length()>50) content.setText(note.getnContent().substring(0,50));
            else content.setText(note.getnContent());
        }

        return convertView;
    }
}
