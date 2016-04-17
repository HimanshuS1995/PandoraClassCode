package com.chhavi.locationandsensors;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
, LocationListener{

    GoogleApiClient googleApiClient;
    Location lastLocation;
    boolean requestingLocationUpdates = false;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buildGoogleClient();
    }

    private void buildGoogleClient() {

        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        lastLocation = LocationServices.FusedLocationApi
                .getLastLocation(googleApiClient);
        Log.e("Longitude", lastLocation.getLongitude() + "");
        Log.e("Latitude", lastLocation.getLatitude() + "");
        if(!requestingLocationUpdates){
            startLocationUpdates();
        }
    }

    private void startLocationUpdates(){
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(2000);

        LocationServices.FusedLocationApi
                .requestLocationUpdates(googleApiClient, locationRequest,
                        this);

        requestingLocationUpdates = true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {


    }

    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    protected void onStop(){
        googleApiClient.disconnect();
        super.onStop();
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.e("Update", location.getLatitude() + " " + location.getLongitude());
    }


    protected void onPause() {
        stopLocationUpdates();
        super.onPause();
    }

    private void stopLocationUpdates(){
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        requestingLocationUpdates = false;
    }

    protected void onResume() {

        if(googleApiClient.isConnected() && !requestingLocationUpdates){
            startLocationUpdates();
        }
        super.onResume();

    }
}