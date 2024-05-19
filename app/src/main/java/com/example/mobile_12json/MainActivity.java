package com.example.mobile_12json;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String PROVIDER_NAME = "com.example.mobile_12json";
    private static final String URL = "content://" + PROVIDER_NAME + "/notes";
    private static final Uri CONTENT_URI = Uri.parse(URL);

    private ArrayAdapter<String> adapter;
    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        EditText noteEditText = findViewById(R.id.noteEditText);
        Button addButton = findViewById(R.id.addButton);

        notesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        listView.setAdapter(adapter);

        addButton.setOnClickListener(v -> {
            String note = noteEditText.getText().toString();
            if (!note.isEmpty()) {
                ContentValues values = new ContentValues();
                values.put("note", note);
                getContentResolver().insert(CONTENT_URI, values);
                noteEditText.setText("");
                loadNotes();
            }
        });

        loadNotes();
    }

    private void loadNotes() {
        notesList.clear();
        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                notesList.add(cursor.getString(cursor.getColumnIndex("note")));
            }
            cursor.close();
        }
        adapter.notifyDataSetChanged();
    }
}
