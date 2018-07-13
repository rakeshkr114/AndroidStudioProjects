package com.example.m1039158.explicitintents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    TextView tvWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        tvWelcome=findViewById(R.id.tvWelcome);

        //getIntent() to get the intent that started this activity
        String name=getIntent().getStringExtra("username");

        tvWelcome.setText(name+" Welcome to Activity 2!");

    }
}
