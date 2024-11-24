package com.example.tasklist;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class NoteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        NoteDetailFragment detailFragment = (NoteDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.detailFragment);

        if (detailFragment != null) {
            String title = getIntent().getStringExtra("noteTitle");
            String description = getIntent().getStringExtra("noteDescription");
            detailFragment.displayNote(new Note(title, description));
        }
    }
}

