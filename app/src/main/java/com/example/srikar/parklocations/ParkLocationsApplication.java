package com.example.srikar.parklocations;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Used to store singletons, since created before everything else.
 * Created by Srikar on 8/4/2016.
 */
public class ParkLocationsApplication extends Application {
    public static final String BASE_URL = "https://data.sfgov.org/";

    private static ParkLocationsApplication INSTANCE;

    private Retrofit retrofit = null;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static ParkLocationsApplication getInstance() {
        return INSTANCE;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
