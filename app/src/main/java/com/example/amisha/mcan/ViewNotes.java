package com.example.amisha.mcan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ArrayAdapter;

import java.io.File;

public class ViewNotes extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        File dir =  new File(Environment.getExternalStorageDirectory(),"Mcan");
        File[] filelist = dir.listFiles();
        String[] theNamesOfFiles = new String[filelist.length];
        for (int i = 0; i < theNamesOfFiles.length; i++) {
            theNamesOfFiles[i] = filelist[i].getName();
        }
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.activity_list_item, theNamesOfFiles));
    }
}
