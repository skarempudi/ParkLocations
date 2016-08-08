package com.example.srikar.parklocations;

import android.app.Application;
import android.util.Log;

import com.example.srikar.parklocations.json.RetrofitInterface;
import com.example.srikar.parklocations.model.ParkList;
import com.example.srikar.parklocations.model.ParkResponse;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Used to store singletons, since created before everything else.
 * Created by Srikar on 8/4/2016.
 */
public class ParkLocationsApp extends Application {
    private static final String TAG = "ParkLocationsApp";
    public static final String BASE_URL = "https://data.sfgov.org/";

    private static ParkLocationsApp INSTANCE;

    private Retrofit retrofit = null;
    private ParkList parkList = new ParkList();

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        //initialize
        initializeList();
    }

    public static ParkLocationsApp getInstance() {
        return INSTANCE;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public ParkList getParkList() {
        return parkList;
    }

    private void initializeList() {
        Log.d(TAG, "initializeList: ");
        
        RetrofitInterface retrofitService = ParkLocationsApp.getInstance()
                .getRetrofit()
                .create(RetrofitInterface.class);

        retrofitService.getParkList()
                .subscribeOn(Schedulers.newThread())
                .doOnError(t -> Log.e(TAG, "JSON server retrieval" , t))
                .onErrorResumeNext(t -> Observable.empty())
                .subscribe(this::onJSONRequestComplete);
    }

    private void onJSONRequestComplete(List<ParkResponse> parks) {
        Log.d(TAG, "onJSONRequestComplete: " + parks.size());
        parkList.setParks(parks);
    }
}
