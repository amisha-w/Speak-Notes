package com.example.amisha.mcan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NoteText extends AppCompatActivity {
    private TextView textView;
    private TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_text);
        textView = findViewById(R.id.note_text);
        textView2 = findViewById(R.id.note_text2);
        Intent i = getIntent();
        String content = i.getStringExtra("content");
        String fname = i.getStringExtra("name");
        textView.setText(content);
        textView2.setText(fname);

    }
}
