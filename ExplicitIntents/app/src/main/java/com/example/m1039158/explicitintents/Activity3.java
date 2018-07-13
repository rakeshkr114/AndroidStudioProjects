package com.example.m1039158.explicitintents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity3 extends AppCompatActivity {

    EditText etSurname;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);

        etSurname=findViewById(R.id.etSurname);
        btnSubmit=findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String surname=etSurname.getText().toString().trim();
                Intent intent = new Intent();

                //set data in intent
                intent.putExtra("surname",surname);

                //Must use setResult() --> As this activity was started by startActivityForResult()
                setResult(RESULT_OK,intent);

                // Finish the current activity
                Activity3.this.finish();
            }
        });
    }
}
