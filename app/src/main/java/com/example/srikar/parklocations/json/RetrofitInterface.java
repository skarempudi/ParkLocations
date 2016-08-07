package com.example.srikar.parklocations.json;

import com.example.srikar.parklocations.model.ParkResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Retrofit interface for Park JSON
 * Created by Srikar on 8/4/2016.
 */
public interface RetrofitInterface {
    @GET("resource/z76i-7s65.json")
    Observable<List<ParkResponse>> getParkList();
}
