package com.chhavi.locationandsensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

/**
 * Created by chhavi on 17/4/16.
 */
public class SensorActivity extends AppCompatActivity {

    SensorManager sensorManager;
    Sensor ourSensor;

    private SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {

            Log.e("Value Changed", event.values[0] + "");

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.e("Accuracy", sensor.toString() + " " + accuracy);

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);


        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor : deviceSensors){
            Log.e("Sensor Found", sensor.toString());
        }

        ourSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);



    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ourSensor!=null){
            sensorManager.registerListener(sensorListener,
                    ourSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }
}
