package com.example.m1039158.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnCall, btnCallFriend, btnMap, btnWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get views
        btnCall=findViewById(R.id.btnCall);
        btnCallFriend=findViewById(R.id.btnCallFriend);
        btnMap=findViewById(R.id.btnMap);
        btnWeb=findViewById(R.id.btnWeb);

        //Place a call button
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent.ACTION_DIAL  --> To launch dialer installed on the phione
                Intent intent=new Intent(Intent.ACTION_DIAL);
                startActivity(intent);
            }
        });

        //Call best friend
        btnCallFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Launch the dialer with the telephone number typed on it
                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+918101896291"));
                startActivity(intent);
            }
        });

        //Show map button
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open Map with specified query
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q=Way to fitness Krishna Garden"));
                startActivity(intent);
            }
        });

        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open a site
                Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.facebook.com"));
                startActivity(intent);
            }
        });
    }
}
