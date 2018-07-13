package com.example.m1039158.idinformation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText etID;
    TextView tvMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etID = findViewById(R.id.etID);
        tvMessage=findViewById(R.id.tvMessage);
    }

    public void btnSubmit_OnClick(View v){

        //Get data
        String data=etID.getText().toString();

        //Get date of birth
        String DOB=data.substring(0,6);


        //Get gender
        int gender=Integer.parseInt(data.charAt(6)+"");
        String sGender;

        if (gender>=5)
            sGender="Male";
        else
            sGender="Female";


        //Get Nationality
        int nationality=Integer.parseInt(data.charAt(10)+"");

        String sNationality;

        if (nationality==0)
            sNationality="South African";
        else
            sNationality="Permanent Resident";


        tvMessage.setText("Your information from ID number: \n"+
                            "Date of Birth: "+DOB+"\n"+
                            "Gender: "+sGender+"\n"+
                            "Nationality: "+sNationality);
    }
}
