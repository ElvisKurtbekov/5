package com.example.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {
    private EditText editNoteTitle, editNoteDescription;
    private Button saveButton;

    private String noteTitle, noteDescription;
    private boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editNoteTitle = findViewById(R.id.editNoteTitle);
        editNoteDescription = findViewById(R.id.editNoteDescription);
        saveButton = findViewById(R.id.saveButton);

        if (savedInstanceState != null) {
            noteTitle = savedInstanceState.getString("noteTitle", "");
            noteDescription = savedInstanceState.getString("noteDescription", "");
        } else {
            Intent intent = getIntent();
            TaskModel taskModel = intent.getParcelableExtra("task_model");
            if (taskModel != null) {
                noteTitle = taskModel.getName();
                noteDescription = taskModel.getDescription();
                isEdit = intent.getBooleanExtra("isEdit", false);
            }
        }

        editNoteTitle.setText(noteTitle);
        editNoteDescription.setText(noteDescription);

        saveButton.setOnClickListener(v -> {
            noteTitle = editNoteTitle.getText().toString();
            noteDescription = editNoteDescription.getText().toString();

            TaskModel updatedTaskModel = new TaskModel(1, noteTitle, noteDescription);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("task_model", updatedTaskModel);
            resultIntent.putExtra("isEdit", isEdit);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("noteTitle", editNoteTitle.getText().toString());
        outState.putString("noteDescription", editNoteDescription.getText().toString());
    }
}
