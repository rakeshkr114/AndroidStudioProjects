package com.example.m1039158.dummyphone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tvMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage=findViewById(R.id.tvMessage);

        if(getIntent().getData()!=null){
            tvMessage.setText(getIntent().getData().toString());
        }
        else{
            tvMessage.setText("No number given!");
        }
    }
}
