package com.example.chhavi.musicandvideo;

import android.content.pm.ActivityInfo;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

/**
 * Created by chhavi on 23/4/16.
 */
public class VideoCapture extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    MediaRecorder recorder;
    SurfaceHolder holder;
    boolean recording = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        recorder = new MediaRecorder();
        initRecorder();
        setContentView(R.layout.video_layout);

        SurfaceView cameraView = (SurfaceView)findViewById(R.id.surface_view);
        holder = cameraView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        cameraView.setClickable(true);
        cameraView.setOnClickListener(this);


    }

    private void initRecorder(){
        //these two parametres are a must for recorder to know where to get the audio and video from
        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);

        //these are optional properties to set quality of recorded video
        CamcorderProfile cpHigh = CamcorderProfile
                .get(CamcorderProfile.QUALITY_HIGH);
        recorder.setProfile(cpHigh);
        //can be used to set the location of where the file will be saved
        recorder.setOutputFile("/sdcard/user_video.mp4");
        recorder.setMaxDuration(50000); // 50 seconds
        recorder.setMaxFileSize(5000000); // Approximately 5 megabytes
    }

    private void prepareRecorder(){

        recorder.setPreviewDisplay(holder.getSurface());

        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecorder();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        if(recording){
            recorder.stop();
            recording = false;
        }
        recorder.release();

    }

    @Override
    public void onClick(View v) {

        if(recording){
            recorder.stop();
            recording = false;
            initRecorder();
            prepareRecorder();
        }else {

            recorder.start();
            recording = true;
        }
    }
}
