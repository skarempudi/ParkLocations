package com.example.srikar.parklocations.recyclerview;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.srikar.parklocations.ParkLocationsApp;
import com.example.srikar.parklocations.R;
import com.example.srikar.parklocations.model.ParkList;
import com.example.srikar.parklocations.model.ParkResponse;

/**
 * Adapter for RecyclerView that lists parks
 * Created by Srikar on 8/8/2016.
 */
public class ParkListAdapter extends RecyclerView.Adapter<ParkListAdapter.ViewHolder>
        implements ParkList.ParkListChangeListener {
    private static final String TAG = "ParkListAdapter";

    public ParkListAdapter() {
        //set adapter as listener for ParkList
        Log.d(TAG, "ParkListAdapter: Creating, setting self as listener");
        ParkLocationsApp.getInstance().getParkList().setListChangeListener(this);
    }

    /**
     * Contains the views used by each list entry in the RecyclerView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.list_entry_text);
        }
    }
    /**
     * Used to inflate each entry of the RecyclerView
     * @param parent ViewGroup holding each ViewHolder
     * @param viewType Type of view
     * @return Newly inflated ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_entry, parent, false);
        return new ViewHolder(v);
    }

    /**
     * What to do when ViewHolder is used to display data
     * @param holder ViewHolder that will modify
     * @param position Position in list
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get park list
        ParkList parkList = ParkLocationsApp.getInstance().getParkList();
        if (parkList != null) {
            //get park
            ParkResponse park = parkList.getPark(position);
            Log.d(TAG, "onBindViewHolder: " + park.getParkNameCapitalized());

            Resources res = ParkLocationsApp.getInstance().getResources();
            String formatted = String.format(res.getString(R.string.list_entry_unformatted),
                    park.getParkNameCapitalized(),
                    park.getPsaManager(),
                    park.getEmail(),
                    park.getPhoneNumber());
            holder.textView.setText(formatted);
        }
    }

    @Override
    public int getItemCount() {
        ParkList parkList = ParkLocationsApp.getInstance().getParkList();
        return parkList.size();
    }

    /**
     * When park list changes, refresh everything
     */
    @Override
    public void onParkListChange() {
        Log.d(TAG, "onParkListChange: Data set changed");
        notifyDataSetChanged();
    }
}
