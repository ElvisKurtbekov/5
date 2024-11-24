package com.example.tasklist;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NoteListFragment extends Fragment {

    private OnNoteSelectedListener listener;

    public interface OnNoteSelectedListener {
        void onNoteSelected(Note note);  // Метод для обработки выбора заметки
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnNoteSelectedListener) {
            listener = (OnNoteSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnNoteSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        ListView listView = view.findViewById(R.id.noteListView);

        // Пример списка заметок
        List<Note> notes = Arrays.asList(
                new Note("Note 1", "Description 1"),
                new Note("Note 2", "Description 2"),
                new Note("Note 3", "Description 3")
        );

        List<String> noteTitles = new ArrayList<>();
        for (Note note : notes) {
            noteTitles.add(note.getTitle());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                noteTitles
        );




        listView.setAdapter(adapter);

        // Обработка нажатия на элемент
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            if (listener != null) {
                listener.onNoteSelected(notes.get(position)); // Передаем выбранную заметку в MainActivity
            }
        });

        return view;
    }
}
