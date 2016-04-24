package com.example.chhavi.musicandvideo;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

/**
 * Created by chhavi on 23/4/16.
 */
public class SongFromUri extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    MediaPlayer mp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button play_button = (Button)findViewById(R.id.play_button);

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mp == null){
                    playSong();

                }else if(mp!=null && !mp.isPlaying()){
                    mp.start();
                }else if(mp.isPlaying()){
                    mp.pause();
                }


            }
        });

    }


    private void playSong(){

        Uri myUri = Uri.parse(Environment.getExternalStorageDirectory().getPath() + "/Music/addictedd.mp3");

        mp = new MediaPlayer();
        mp.setOnCompletionListener(this);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mp.setDataSource(this,myUri );
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            mp.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mp.start();
    }


    protected void onStop(){

        mp.stop();
        mp.release();
        mp = null;
        super.onStop();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }
}
