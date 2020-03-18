package com.example.amisha.mcan;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class note_taking extends AppCompatActivity {
    boolean listening = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taking);
        final EditText editText = findViewById(R.id.noteText);

        final SpeechRecognizer mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        final Intent mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault());
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle bundle) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float v) {

            }

            @Override
            public void onBufferReceived(byte[] bytes) {

            }

            @Override
            public void onEndOfSpeech() {
                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                Toast.makeText(getApplicationContext(),"EoSpeech", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onResults(Bundle bundle) {
                //getting all the matches
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null)
                    editText.setText(editText.getText()+matches.get(0));
//                mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
//                Toast.makeText(getApplicationContext(),"EoSpeech", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPartialResults(Bundle bundle) {
                ArrayList<String> matches = bundle
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                //displaying the first match
                if (matches != null)
                    editText.setText(editText.getText()+matches.get(0));

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        });
        findViewById(R.id.noteButton).setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!listening){
                    mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                    editText.setText("");
                    editText.setHint("Listening...");
                    listening=true;
                }else{
                    mSpeechRecognizer.stopListening();
                    editText.setHint("Long press the button below & speak.");
                    listening=false;
                }
            }
        });
//        findViewById(R.id.noteButton).setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//                    case MotionEvent.ACTION_UP:
//                        mSpeechRecognizer.stopListening();
//                        editText.setHint("Long press the button below & speak.");
//                        break;
//
//                    case MotionEvent.ACTION_DOWN:
//                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
//                        editText.setText("");
//                        editText.setHint("Listening...");
//                        break;
//                }
//                return false;
//            }
//        });
    }
}
