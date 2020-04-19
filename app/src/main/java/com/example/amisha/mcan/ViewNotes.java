package com.example.amisha.mcan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViewNotes extends Activity {

    private ListView list;
    private File dir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        list = findViewById(R.id.list);
        dir =  new File(Environment.getExternalStorageDirectory(),"Mcan");
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
                Toast.makeText(ViewNotes.this, ""+name+"", Toast.LENGTH_LONG).show();
                String content = readText(name);
                Intent intent = new Intent(ViewNotes.this,NoteText.class);
                intent.putExtra("EXTRA_MESSAGE", content);
                startActivity(intent);
            }
        });
    }

    private String readText(String filename){
        File file = new File(dir,filename);
        StringBuilder text = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line=br.readLine())!=null){
                text.append(line);
                text.append("\n");
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return text.toString();
    }
}
