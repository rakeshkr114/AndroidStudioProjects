package com.example.m1039158.servicevideo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;

public class MyPlayService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener
{

    private MediaPlayer mediaPlayer;
    String link;
    private MusicStoppedListener musicStoppedListener; // Instance variable of the interface, Note: Can't create object of an Interface

    public MyPlayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer=new MediaPlayer();
        // Go to onCompletion() method
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);

    }

    //When service starts, runs after onCreate()
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //key-value pair("AudioLink-") passed with new intent from MainActivity
        link=intent.getStringExtra("AudioLink");
        //reset mediaPlayer tio uninitialized state
        mediaPlayer.reset();

        //Note: Context flow: ApplicationClass.context is 1st linked to MainActivity's context (see onCreate() of MainActivity.java), Now
        // assigning the same context to musicStoppedListener and casting
        //MainActivity implements the MusicStoppedListener thus casting possible;
        musicStoppedListener=(MusicStoppedListener) ApplicationClass.context;

        //mediaPlayer not playing?
        if(!mediaPlayer.isPlaying()){
            try{
                mediaPlayer.setDataSource(link);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        /* START_STICKY ==> If this service's process is killed while it is started, then leave it in Started state --> Service performing
        * background music which requires explicit start and stop
        */
        return START_STICKY;
    }


    //Play the music, executed after onStartCommand()
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }


    //On music completion
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if(mediaPlayer.isPlaying()){
            //stop the player on completion
            mediaPlayer.stop();
        }

        //Change the icon.image onCompletion automatically
        musicStoppedListener.onMusicStopped();

        //Stop the service itself, It will automatically got to onDestroy()
        stopSelf();
    }


    //When service is stopped
    @Override
    public void onDestroy() {
        super.onDestroy();

        //Check if mediaPlayer is still active
        if(mediaPlayer!=null){
            if(mediaPlayer.isPlaying()){
                mediaPlayer.stop();
            }

            //Releases the resources()e.g, memory used associated with the MediaPlayer object
            mediaPlayer.release();
        }
    }


    //If error occurs
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {

        switch (i){
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(this, "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK", Toast.LENGTH_SHORT).show();break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Toast.makeText(this, "MEDIA_ERROR_SERVER_DIED", Toast.LENGTH_SHORT).show();break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(this, "MEDIA_ERROR_UNKNOWN", Toast.LENGTH_SHORT).show();break;
        }
        return false;
    }


    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }


    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }


    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {

    }
}
