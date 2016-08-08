package com.example.srikar.parklocations.model;

import com.google.gson.annotations.SerializedName;

/**
 * Used to store each Park object in the JSON array.
 * Created by Srikar on 8/4/2016.
 */
public class ParkResponse {
    @SerializedName("zipcode")
    private String zipcode;
    @SerializedName("number")
    private String phoneNumber;

    //location is its own object
    @SerializedName("location_1")
    private ParkLocationResponse location;

    @SerializedName("psamanager")
    private String psaManager;
    @SerializedName("parktype")
    private String parkType;
    @SerializedName("acreage")
    private String acreage;

    //not sure what supdist is, not using

    @SerializedName("parkname")
    private String parkNameCapitalized;
    @SerializedName("parkservicearea")
    private String parkServiceArea;
    @SerializedName("email")
    private String email;
    @SerializedName("parkid")
    private String parkID;

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ParkLocationResponse getLocation() {
        return location;
    }

    public void setLocation(ParkLocationResponse location) {
        this.location = location;
    }

    public String getPsaManager() {
        return psaManager;
    }

    public void setPsaManager(String psaManager) {
        this.psaManager = psaManager;
    }

    public String getParkType() {
        return parkType;
    }

    public void setParkType(String parkType) {
        this.parkType = parkType;
    }

    public String getAcreage() {
        return acreage;
    }

    public void setAcreage(String acreage) {
        this.acreage = acreage;
    }

    public String getParkNameCapitalized() {
        return parkNameCapitalized;
    }

    public void setParkNameCapitalized(String parkNameCapitalized) {
        this.parkNameCapitalized = parkNameCapitalized;
    }

    public String getParkServiceArea() {
        return parkServiceArea;
    }

    public void setParkServiceArea(String parkServiceArea) {
        this.parkServiceArea = parkServiceArea;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getParkID() {
        return parkID;
    }

    public void setParkID(String parkID) {
        this.parkID = parkID;
    }
}
