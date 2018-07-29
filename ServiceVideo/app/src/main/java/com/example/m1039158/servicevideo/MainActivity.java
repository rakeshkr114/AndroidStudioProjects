package com.example.m1039158.servicevideo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
//https://www.udemy.com/learn-android-application-development-y/learn/v4/t/lecture/7844204?start=300
public class MainActivity extends AppCompatActivity implements MusicStoppedListener{

    ImageView ivPlayStop;
    String audioLink="https://dl.dropbox.com/s/1lpcv470mt9rfza/Rude?dl=0"; //Note: the dl in begining for direct link
    boolean musicPlaying=false;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPlayStop=findViewById(R.id.ivPlayStop);
        ivPlayStop.setImageResource(R.drawable.play);
        serviceIntent=new Intent(this,MyPlayService.class);

        //ApplicationClass.context has now link to MainActivity
        ApplicationClass.context=MainActivity.this;

        //Note: Make the imageView clickable in activity_main.xml, then only one can set OnClickListener on the image
        ivPlayStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If music is not playing, play it and change the image view to Stop.png
                if(!musicPlaying){
                    playAudio();
                    ivPlayStop.setImageResource(R.drawable.stop);
                    musicPlaying=true;
                }
                else{
                    stopPlayService();
                    ivPlayStop.setImageResource(R.drawable.play);
                    musicPlaying=false;
                }
            }
        });
    }

    private void playAudio(){
        //passing key-value pair in intent to MyPlayService
        serviceIntent.putExtra("AudioLink",audioLink);

        try{
            //Note: startService NOT StartActivity!!!
            startService(serviceIntent);
        }catch (SecurityException e){
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlayService(){
        try{
            //stop the service
            stopService(serviceIntent);
        }catch (SecurityException e){
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMusicStopped() {
        ivPlayStop.setImageResource(R.drawable.play);
        musicPlaying=false;
    }
}
