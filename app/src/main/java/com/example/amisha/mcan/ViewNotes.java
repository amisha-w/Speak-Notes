package com.example.amisha.mcan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewNotes extends Activity {

    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        list = findViewById(R.id.list);
        File dir =  new File(Environment.getExternalStorageDirectory(),"Mcan");
        File[] filelist = dir.listFiles();
        List listNames = new ArrayList();
        listNames.clear();
        for (File f: dir.listFiles()){
//            Date d = new Date(f.lastModified());
            listNames.add(f.getName());
        }
        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listNames));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = (String) adapterView.getItemAtPosition(i);
                Toast.makeText(ViewNotes.this, ""+name+" was clicked.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
