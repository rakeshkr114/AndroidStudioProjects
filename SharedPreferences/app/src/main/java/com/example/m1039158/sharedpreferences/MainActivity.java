package com.example.m1039158.sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    //use package name in the beginning of the file name to make sure the uniqueness of the file "Names"
    public static final String MY_PREFS_FILENAME="com.example.m1039158.sharedpreferences.Names";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvMessage=findViewById(R.id.tvMessage);
        final EditText etName=findViewById(R.id.etName);
        Button btnSubmit=findViewById(R.id.btnSubmit);

        SharedPreferences prefs=getSharedPreferences(MY_PREFS_FILENAME,MODE_PRIVATE);
        //get value where key="user" else return ""
        String user=prefs.getString("user","");

        tvMessage.setText("Welcome to my app "+user+"!");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etName.getText().toString().trim();
                tvMessage.setText("Welcome to my app "+name+"!");

                //SharedPreferences.Editor to create/open the file in private mode(accessible to this app only)
                SharedPreferences.Editor editor=getSharedPreferences(MY_PREFS_FILENAME,MODE_PRIVATE).edit();
                editor.putString("user",name);
                //Finally commit the changes to the file
                editor.commit();
            }
        });

    }
}
