package com.example.srikar.parklocations.model;

import com.google.gson.annotations.SerializedName;

/**
 * Used to hold location object in Park JSON object
 * Created by Srikar on 8/4/2016.
 */
public class ParkLocationResponse {
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("human_address")
    private String unformattedAddress;
    //ignore "needs_recording"
    @SerializedName("longitude")
    private Double longitude;

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getUnformattedAddress() {
        return unformattedAddress;
    }

    public void setUnformattedAddress(String unformattedAddress) {
        this.unformattedAddress = unformattedAddress;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
