package com.example.srikar.parklocations.model;

import com.google.gson.annotations.SerializedName;

/**
 * Used to hold location object in Park JSON object
 * Created by Srikar on 8/4/2016.
 */
public class ParkLocationResponse {
    @SerializedName("latitude")
    private Float latitude;
    @SerializedName("human_address")
    private String unformattedAddress;
    //ignore "needs_recording"
    @SerializedName("longitude")
    private Float longitude;

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getUnformattedAddress() {
        return unformattedAddress;
    }

    public void setUnformattedAddress(String unformattedAddress) {
        this.unformattedAddress = unformattedAddress;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}
