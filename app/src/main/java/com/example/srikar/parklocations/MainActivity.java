package com.example.srikar.parklocations;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.srikar.parklocations.json.JsonLoader;
import com.example.srikar.parklocations.model.ParkResponse;
import com.example.srikar.parklocations.json.RetrofitInterface;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";

    private static final int PERMISSION_REQUEST_CODE = 12345;

    private GoogleApiClient mGoogleApiClient;
    private boolean hasPermission = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test JSON call
        RetrofitInterface retrofitService = ParkLocationsApplication.getInstance()
                .getRetrofit()
                .create(RetrofitInterface.class);

        retrofitService.getParkList()
                .subscribeOn(Schedulers.newThread())
                .doOnError(t -> Log.e(TAG, "JSON server retrieval" , t))
                .onErrorResumeNext(t -> Observable.empty())
                .subscribe(this::onComplete);

        //get permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //no explanation
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_CODE);
        } else {
            hasPermission = true;
            Log.d(TAG, "onCreate: Have location permission");
        }

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void onComplete(List<ParkResponse> parks) {
        Log.d(TAG, "onResponse: Number of parks received is " + parks.size());
        //see first real park
        String address = parks.get(1).getLocation().getUnformattedAddress();
        Log.d(TAG, "onResponse: " + address);

        Log.d(TAG, "onResponse: Doing file stuff");

        //save to file
        JsonLoader.saveToFile(parks);

        //read from file
        List<ParkResponse> parks2 = JsonLoader.readFromFile();
        Log.d(TAG, "onResponse: Number of parks received is " + parks2.size());
        //see first real park
        address = parks2.get(1).getLocation().getUnformattedAddress();
        Log.d(TAG, "onResponse: " + address);
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (!hasPermission ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationAvailability availability = LocationServices.FusedLocationApi
                .getLocationAvailability(mGoogleApiClient);

        Log.d(TAG, "onConnected: Available " + availability.isLocationAvailable());


        Location location = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        Log.d(TAG, "onConnected: Latitude is " + location.getLatitude());
        Log.d(TAG, "onConnected: Longitude is " + location.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: API connection failed");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                hasPermission = true;
                Log.d(TAG, "onRequestPermissionsResult: Got permission");
            }
            else {
                hasPermission = false;
                Log.d(TAG, "onRequestPermissionsResult: Permission denied");
            }
        }
    }

}
