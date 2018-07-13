package com.example.m1039158.explicitintents;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    Button btnActivity2, btnActivity3;
    TextView tvMessage;

    final int ACTIVITY3=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName =findViewById(R.id.etName);
        tvMessage=findViewById(R.id.tvMassage);


        btnActivity2 = findViewById(R.id.btnActivity2);
        btnActivity3 = findViewById(R.id.btnActivity3);

        btnActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, com.example.m1039158.explicitintents.Activity2.class);

                // To pass key value pair to the new activity
                intent.putExtra("username", etName.getText().toString());

                //start 2nd activity
                startActivity(intent);


//                 Just to show quick messages
//                Toast.makeText(MainActivity.this, "Clicked Activity 2", Toast.LENGTH_SHORT).show();

            }
        });

        btnActivity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, com.example.m1039158.explicitintents.Activity3.class);

                //Starting activity 3 for result --> expecting a result from activity3
                //Note: ACTIVITY3 final variable (unique) is the "requestCode" to uniquely identify the activity3
                startActivityForResult(intent,ACTIVITY3);
            }
        });
    }  //end of onCreate()

    //Once result is available:
    // generate Override method onActivityResult --> Perform actions once result is back, Use requestCode identify which activity sent the result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String surname;
        String name;

        if(requestCode==ACTIVITY3 && resultCode==RESULT_OK){
            //get result returned from the activity
            surname=data.getStringExtra("surname");
            name=etName.getText().toString().trim();

            //display in text view
            tvMessage.setText(name+" "+surname);
        }
        else if(resultCode==RESULT_CANCELED){
            Toast.makeText(MainActivity.this, "The user did not enter anything!", Toast.LENGTH_SHORT).show();
        }
    }
}
