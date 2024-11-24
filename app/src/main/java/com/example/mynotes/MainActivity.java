package com.example.mynotes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.temporal.TemporalQueries;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int EDIT_NOTE_REQUEST = 1;
    private TextView noteTitle, noteDescription;
    private Button addButton, editButton, showNoteButton;
    private ImageButton prevNoteButton, nextNoteButton;

    private ArrayList<TaskModel> notes;
    private int currentIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteTitle = findViewById(R.id.noteTitle);
        noteDescription = findViewById(R.id.noteDescription);
        addButton = findViewById(R.id.addButton);
        editButton = findViewById(R.id.editButton);
        showNoteButton = findViewById(R.id.showNoteButton);
        prevNoteButton = findViewById(R.id.prevNoteButton);
        nextNoteButton = findViewById(R.id.nextNoteButton);

        notes = new ArrayList<>();

        addButton.setOnClickListener(v -> addNote());
        editButton.setOnClickListener(v -> editNote());
        showNoteButton.setOnClickListener(v -> showLastNote());
        prevNoteButton.setOnClickListener(v -> showPrevNote());
        nextNoteButton.setOnClickListener(v -> showNextNote());

        if (savedInstanceState != null) {
            notes = (ArrayList<TaskModel>) savedInstanceState.getSerializable("notes");
            currentIndex = savedInstanceState.getInt("currentIndex", -1);
            if (currentIndex >= 0 && currentIndex < notes.size()) {
                displayNote();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("notes", notes);
        outState.putInt("currentIndex", currentIndex);
    }

    private void addNote() {
        String title = "Заметка " + (notes.size() + 1);
        String description = "Описание заметки";

        TaskModel newNote = new TaskModel(notes.size() + 1, title, description);

        Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
        intent.putExtra("task_model", newNote);
        intent.putExtra("isEdit", false);
        startActivityForResult(intent, EDIT_NOTE_REQUEST);
    }


    private void editNote() {
        if (currentIndex >= 0 && currentIndex < notes.size()) {
            TaskModel currentNote = notes.get(currentIndex);
            Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
            intent.putExtra("task_model", currentNote);
            intent.putExtra("isEdit", true);
            startActivityForResult(intent, EDIT_NOTE_REQUEST);
        } else {
            Toast.makeText(this, "Выберите заметку для редактирования", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            TaskModel taskModel = data.getParcelableExtra("task_model");
            boolean isEdit = data.getBooleanExtra("isEdit", false);

            if (taskModel != null) {
                if (!isEdit) {
                    taskModel.setId(notes.size() + 1);
                    notes.add(taskModel);
                    Toast.makeText(this, "Заметка добавлена", Toast.LENGTH_SHORT).show();
                } else {
                    TaskModel currentNote = notes.get(currentIndex);
                    currentNote.setName(taskModel.getName());
                    currentNote.setDescription(taskModel.getDescription());
                    Toast.makeText(this, "Заметка обновлена", Toast.LENGTH_SHORT).show();
                }


                displayNote();
            }
        }
    }




    private void showLastNote() {
        if (!notes.isEmpty()) {
            currentIndex = notes.size() - 1;
            displayNote();
        } else {
            Toast.makeText(this, "Нет заметок для отображения", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPrevNote() {
        if (currentIndex > 0) {
            currentIndex--;
            displayNote();
        } else {
            Toast.makeText(this, "Это первая заметка", Toast.LENGTH_SHORT).show();
        }
    }

    private void showNextNote() {
        if (currentIndex < notes.size() - 1) {
            currentIndex++;
            displayNote();
        } else {
            Toast.makeText(this, "Это последняя заметка", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayNote() {
        if (currentIndex >= 0 && currentIndex < notes.size()) {
            TaskModel currentNote = notes.get(currentIndex);
            noteTitle.setText(currentNote.getName());
            noteDescription.setText(currentNote.getDescription());
        }
    }
}

