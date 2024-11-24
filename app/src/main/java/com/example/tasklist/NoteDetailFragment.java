package com.example.tasklist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NoteDetailFragment extends Fragment {

    private TextView noteTitle;
    private TextView noteDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_detail, container, false);

        noteTitle = view.findViewById(R.id.noteTitle);
        noteDescription = view.findViewById(R.id.noteDescription);

        return view;
    }

    public void displayNote(Note note) {
        if (note != null) {
            noteTitle.setText(note.getTitle());
            noteDescription.setText(note.getDescription());
        }
    }
}
