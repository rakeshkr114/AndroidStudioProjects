package com.example.m1039158.customedittext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView etFirstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstName=findViewById(R.id.etFirstName);

        String names[]={"James", "John", "Harry", "Jonas", "Jammy", "Jennifer", "Jack"};

        //using in built layout for dropdown list/options android.R.layout.simple_dropdown_item_1line
        //ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,names);

        //using custom layout custom_design_autocomplete for dropdown list/options
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, R.layout.custom_design_autocomplete,names);

        etFirstName.setAdapter(adapter);

        //display auto complete list after typing 1 character
        etFirstName.setThreshold(1);

    }
}
