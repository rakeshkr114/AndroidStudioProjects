package com.example.m1039158.permissionsandroid6;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnPermission;
    private static final int UNIQUE_REEQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPermission=findViewById(R.id.btnPermission);

        btnPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Note: Add  <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>  tag in AndroidManifest.xml to ask for permissions
                //If permission to WRITE_EXTERNAL_STORAGE is not GRANTED
                if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                        PackageManager.PERMISSION_GRANTED){
                    //Ask for permission and then onRequestPermissionsResult() will be called automaticllay
                    ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},UNIQUE_REEQUEST_CODE);
                }
                else{
                    Toast.makeText(MainActivity.this, "Permission has been granted already. Thank You!:)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //onRequestPermissionsResult will be called whenever user decide to allow or deny the requested permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Permission result came with UNIQUE_REEQUEST_CODE =>  WRITE_EXTERNAL_STORAGE; Note: Results can be Allow or Deny
        if(requestCode==UNIQUE_REEQUEST_CODE){
            //If permission granted
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Thank You! Permission Granted!", Toast.LENGTH_SHORT).show();
            }
            // if permission denied
            else if(grantResults[0]==PackageManager.PERMISSION_DENIED){

                //If user does not check "Don't ask again" then show dialog showing warning
                if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                    //Alert dialog to confirm the deny decision or warn
                    AlertDialog.Builder dialog=new AlertDialog.Builder(this);

                    dialog.setMessage("This permission is important to save a file to the phone! Please permit it!");
                    dialog.setTitle("Important Permission Required");

                    //Ask permission again, when clicked on OK
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //Asking Permission again and then onRequestPermissionsResult() will be called automaticllay
                            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},UNIQUE_REEQUEST_CODE);
                        }
                    });

                    //Show toast message when clicked on "NO THANKS!" in alert dialog
                    dialog.setNegativeButton("NO THANKS!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(MainActivity.this, "Cannot be done!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    dialog.show(); // show dialog
                }
                //If user checked "Don't ask again" then show toast
                else{
                    Toast.makeText(this, "We will never show this to you again!", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
