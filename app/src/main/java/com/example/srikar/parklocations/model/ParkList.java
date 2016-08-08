package com.example.srikar.parklocations.model;

import android.util.Log;

import java.util.List;

/**
 * Handles list of parks and loading of data, alerts listeners when changes.
 * Created by Srikar on 8/7/2016.
 */
public class ParkList {
    private static final String TAG = "ParkList";
    List<ParkResponse> parks;
    ParkListChangeListener listener;

    public ParkList() {
        parks = null;
    }

    public ParkList(List<ParkResponse> parks) {
        this.parks = parks;
    }

    public void setParks(List<ParkResponse> parks) {
        this.parks = parks;
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

    public void setListChangeListener(ParkListChangeListener listener) {
        Log.d(TAG, "setListChangeListener: Listener set");
        this.listener = listener;
    }

    public interface ParkListChangeListener {
        void onParkListChange();
    }
}
