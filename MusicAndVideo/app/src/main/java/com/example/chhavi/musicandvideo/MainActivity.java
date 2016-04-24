package com.example.chhavi.musicandvideo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mp = MediaPlayer.create(this, R.raw.song);

        Button play_button = (Button)findViewById(R.id.play_button);

        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mp.isPlaying()){
                    mp.pause();
                }else if(mp!=null){
                    mp.start();
                }else{
                   // mp.start();
                }

            }
        });


    }

    protected void onStop(){

        mp.stop();
        mp.release();
        mp = null;
        super.onStop();
    }
}
