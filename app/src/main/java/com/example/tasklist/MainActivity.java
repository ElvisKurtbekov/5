package com.example.tasklist;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements NoteListFragment.OnNoteSelectedListener {

    private boolean isTablet;  // Флаг для определения планшета

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isTablet = findViewById(R.id.detailFragment) != null;  // Если detailFragment есть, это планшет
    }

    @Override
    public void onNoteSelected(Note note) {
        if (isTablet) {
            // Планшет: отображаем данные во втором фрагменте
            NoteDetailFragment detailFragment = (NoteDetailFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.detailFragment);
            if (detailFragment != null) {
                detailFragment.displayNote(note);
            }
        } else {
            // Телефон: запускаем новую активность
            Intent intent = new Intent(this, NoteDetailActivity.class);
            intent.putExtra("noteTitle", note.getTitle());
            intent.putExtra("noteDescription", note.getDescription());
            startActivity(intent);
        }
    }
}
