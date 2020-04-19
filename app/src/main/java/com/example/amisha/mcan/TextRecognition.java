package com.example.amisha.mcan;

//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextDetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TextRecognition extends AppCompatActivity{

    private Button captureBtn, extractBtn;
    private TextView extractedText;
    private ImageView imgView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap imageBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);

        captureBtn = findViewById(R.id.capture_btn);
        extractBtn = findViewById(R.id.extract_btn);
        extractedText = findViewById(R.id.text_display);
        imgView = findViewById(R.id.text_img);
        imgView.setImageDrawable(getResources().getDrawable(R.drawable.ic_placeholder));
        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                extractedText.setText("");
            }
        });

        extractBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectTextFromImage();
            }
        });
    }

    public void btn_saveDialog(View view){

        final AlertDialog.Builder alert = new AlertDialog.Builder(TextRecognition.this);
        View mView = getLayoutInflater().inflate(R.layout.savedialog,null);
        final EditText text = mView.findViewById(R.id.text_input);
        Button btn_cancel = mView.findViewById(R.id.cancel_btn);
        Button btn_save = mView.findViewById(R.id.ok_btn);

        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = text.getText().toString();
                String content = extractedText.getText().toString();
                saveTextAsFile(filename,content);
                alertDialog.dismiss();
            }
        });
    }

    private void saveTextAsFile( String filename, String content){
        String fileName = filename+".txt";
        File folder = new File(Environment.getExternalStorageDirectory(),"Mcan");
        if(!folder.exists())
            folder.mkdir();
        File file = new File(folder, fileName);
        try{
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(this, "File "+fileName+" saved.", Toast.LENGTH_LONG).show();
        }catch(FileNotFoundException e){
            e.printStackTrace();
            Toast.makeText(this, "File Not Found", Toast.LENGTH_SHORT).show();
        }catch(IOException e){
            e.printStackTrace();
            Toast.makeText(this, "Error while saving!", Toast.LENGTH_SHORT).show();
        }


    }

    private void detectTextFromImage() {
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextDetector detector = FirebaseVision.getInstance().getVisionTextDetector();
        detector.detectInImage(firebaseVisionImage)
                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                            @Override
                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                                displayTextFromImage(firebaseVisionText);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TextRecognition.this,"Error :"+e.getMessage(),Toast.LENGTH_LONG).show();
                        Log.d("Error: ",e.getMessage());
                    }
                });
    }

    private void displayTextFromImage(FirebaseVisionText firebaseVisionText) {
        for(FirebaseVisionText.Block block: firebaseVisionText.getBlocks()){
            String text = block.getText();
            extractedText.setText(text);
        }
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imgView.setImageBitmap(imageBitmap);
        }
    }
}
