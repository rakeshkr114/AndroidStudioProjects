package com.example.m1039158.sendsms;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etMessage;
    EditText etTelNo;
    private static  final int SEND_SMS_UNIQUE_CODE=1;

    String SENT="SMS_SENT";
    String DELIVERED="SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;

    //https://www.udemy.com/learn-android-application-development-y/learn/v4/t/lecture/7811350?start=0
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage=findViewById(R.id.etMessage);
        etTelNo=findViewById(R.id.etTelNo);

        //shout out SENT, which will be received/listened by registerReceiver of SENT
        sentPI=PendingIntent.getBroadcast(this,0,new Intent(SENT),0);
        //shout out DELIVERED, which will be received/listened by registerReceiver of DELIVERED
        deliveredPI=PendingIntent.getBroadcast(this,0,new Intent(DELIVERED),0);

    }

    public void btnSend_OnClick(View v){
        String message=etMessage.getText().toString();
        String telNo=etTelNo.getText().toString();

        //If permissions for SEND_SMS is not granted, then ask for it
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},SEND_SMS_UNIQUE_CODE);
        }
        else{
            SmsManager smsManager=SmsManager.getDefault();

            /* Send Workflow: If the sent part of the message is done, activate the SENT pending Intent(sentPI), The sentPI then broadcast
            the message "SENT", which will be listned by the smsSentReciever and run onReceive() method
             */
            smsManager.sendTextMessage(telNo,null,message,sentPI,deliveredPI);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        smsSentReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Show Toast based on the result
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(MainActivity.this, "SMS Sent!", Toast.LENGTH_SHORT).show(); break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(MainActivity.this, "Generic failure!", Toast.LENGTH_SHORT).show(); break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(MainActivity.this, "No Service!", Toast.LENGTH_SHORT).show(); break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(MainActivity.this, "Null PDU!", Toast.LENGTH_SHORT).show(); break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(MainActivity.this, "Radio Off!", Toast.LENGTH_SHORT).show(); break;
                }
            }
        };


        smsDeliveredReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Show Toast based on the result
                switch (getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(MainActivity.this, "SMS delivered!", Toast.LENGTH_SHORT).show(); break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(MainActivity.this, "SMS not delivered!", Toast.LENGTH_SHORT).show(); break;
                }
            }
        };

        //Listen for the message SENT, go to method smsSentReceiver decleared above
        registerReceiver(smsSentReceiver,new IntentFilter(SENT));
        // Listen for the message DELIVERED, go to method smsDeliveredReceiver decleared above
        registerReceiver(smsDeliveredReceiver,new IntentFilter(DELIVERED));
    }

    @Override
    protected void onPause() {
        super.onPause();
        
        // Unregister receivers when moving out of the activity
        unregisterReceiver(smsDeliveredReceiver);
        unregisterReceiver(smsSentReceiver);
    }
}
