package com.example.m1039158.textfilesandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EditText etName,etSurname;
    TextView tvResults;
    Button btnAdd,btnSave;

    ArrayList<Person> persons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName=findViewById(R.id.etName);
        etSurname=findViewById(R.id.etSurname);
        tvResults=findViewById(R.id.tvResults);

        persons=new ArrayList<>();

        //load data from the text file onCreate i.e, in the beginning
        loadData();
    }

    // Note: outside the onCreate fn. of main activity;
    // To link this fn. with button, choose "btnAddData" in activity_main.xml design under "onClick" dropdown option
    public void btnAddData(View v){
        String name=etName.getText().toString().trim();
        String surname=etSurname.getText().toString().trim();
        persons.add(new Person(name,surname));

        setTextToTextView();
    }

    //Set array list data te text view
    private void setTextToTextView() {
        String text="";
        for (Person person:persons)
            text=text + person.getFirstName()+" "+person.getSurName()+"\n";

        tvResults.setText(text);
    }

    public void btnSaveData(View v){
        try {
            FileOutputStream file=openFileOutput("Data.txt",MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(file);

            for (Person person:persons) {
                outputStreamWriter.write(person.getFirstName()+","+person.getSurName()+"\n");
            }

            //OutputStreamWriter.flush() --> To make sure all the bytes has been written to the file
            outputStreamWriter.flush();
            //close the file
            outputStreamWriter.close();

            Toast.makeText(this, "Successfully saved!", Toast.LENGTH_SHORT).show();

        } catch (java.io.IOException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void loadData(){
        //empty the list.
        persons.clear();

        //getApplicationContext().getFileStreamPath() --> Returns the absolute path on the filesystem where a file created with openFileOutput(String, int) is stored.
        File file=getApplicationContext().getFileStreamPath("Data.txt");
        String lineFromFile;

        if(file.exists()){
            try{
                BufferedReader reader=new BufferedReader(new InputStreamReader(openFileInput("Data.txt")));

                while((lineFromFile=reader.readLine())!=null){
                    StringTokenizer tokens=new StringTokenizer(lineFromFile,",");
                    persons.add(new Person(tokens.nextToken(),tokens.nextToken()));
                }

                reader.close();
                setTextToTextView();

            } catch (java.io.IOException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
