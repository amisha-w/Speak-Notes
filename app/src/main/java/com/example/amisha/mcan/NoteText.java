package com.example.amisha.mcan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NoteText extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_text);
        textView = findViewById(R.id.note_text);
        Intent i = getIntent();
        String content = i.getStringExtra("EXTRA_MESSAGE");
        textView.setText(content);

    }
}
