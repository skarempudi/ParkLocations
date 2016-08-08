package com.example.srikar.parklocations.model;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Handles list of parks and loading of data, alerts listeners when changes.
 * Created by Srikar on 8/7/2016.
 */
public class ParkList {
    private static final String TAG = "ParkList";
    List<ParkResponse> parks;
    Location location;

    ParkListChangeListener listener;

    public ParkList() {
        parks = null;
        location = null;
    }

    public ParkList(List<ParkResponse> parks) {
        this.parks = parks;
    }

    public void setParks(List<ParkResponse> newParks) {
        //if have location, sort
        if (location != null) {
            sortList(newParks);
        }

        //either way, set as new park list
        parks = newParks;
        if (listener != null) {
            listener.onParkListChange();
        }
    }

    public ParkResponse getPark(int i) {
        if (parks == null) {
            return null;
        }
        return parks.get(i);
    }

    public int size() {
        if (parks == null) {
            return 0;
        }
        return parks.size();
    }

    public void setLocation(Location location) {
        this.location = location;

        //if have park list, sort
        if (parks != null) {
            List<ParkResponse> newParks = new ArrayList<>(parks);
            sortList(newParks);
            parks = newParks;
            if (listener != null) {
                listener.onParkListChange();
            }
        }
    }

    public void setListChangeListener(ParkListChangeListener listener) {
        Log.d(TAG, "setListChangeListener: Listener set");
        this.listener = listener;
    }

    private void sortList(List<ParkResponse> parkList) {
        if (parkList == null) {
            return;
        }

        Collections.sort(parkList, new Comparator<ParkResponse>() {
            @Override
            public int compare(ParkResponse lhs, ParkResponse rhs) {
                //if don't have location, don't sort
                if (location == null) {
                    return 0;
                }

                double myLatitude = location.getLatitude();
                double myLongitude = location.getLongitude();

                //calculate square of distance between current location and lhs
                double lhsDistance = 0;
                if (lhs.getLocation() != null) {
                    double lhsLatitudeDif = lhs.getLocation().getLatitude() - myLatitude;
                    double lhsLongitudeDif = lhs.getLocation().getLongitude() - myLongitude;

                    lhsDistance = lhsLatitudeDif * lhsLatitudeDif + lhsLongitudeDif * lhsLongitudeDif;
                }

                //calculate square of distance between current location and rhs
                double rhsDistance = 0;
                if (rhs.getLocation() != null) {
                    double rhsLatitudeDif = rhs.getLocation().getLatitude() - myLatitude;
                    double rhsLongitudeDif = rhs.getLocation().getLongitude() - myLongitude;

                    rhsDistance = rhsLatitudeDif * rhsLatitudeDif + rhsLongitudeDif * rhsLongitudeDif;
                }

//                Log.d(TAG, "compare: " + lhs.getParkNameCapitalized() + " is " + lhsDistance + " away");
//                Log.d(TAG, "compare: " + rhs.getParkNameCapitalized() + " is " + rhsDistance + " away");

                if (lhsDistance < rhsDistance) {
                    return -1;
                }
                else if (lhsDistance > rhsDistance) {
                    return 1;
                }
                else {
                    return 0;
                }
            }
        });
    }

    public interface ParkListChangeListener {
        void onParkListChange();
    }
}
