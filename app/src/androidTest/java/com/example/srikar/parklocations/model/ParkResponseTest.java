package com.example.srikar.parklocations.model;

import android.support.test.runner.AndroidJUnit4;

import com.example.srikar.parklocations.BaseParksTest;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Test that ParkResponse is properly populated by Gson.
 * In that sense, also tests ParkLocationResponse.
 * Testing Retrofit would require additional test libraries. My Retrofit configuration uses Gson to
 * parse the Json, so this should accurately test that aspect.
 * Created by Srikar on 8/8/2016.
 */
@RunWith(AndroidJUnit4.class)
public class ParkResponseTest extends BaseParksTest {
    public ParkResponseTest() {
        super();
    }

    /**
     * Load JSON from file.
     */
    @Test
    public void testLoadParks() {
        //load from file
        List<ParkResponse> parks = null;
        try {
            parks = loadParks();
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue("Opening assets failed",
                    false);
        }

        //assert that parks is not null
        assertTrue("Parks is null",
                parks != null);

        //assert that number of parks is 5
        int size = parks.size();
        assertTrue("Size is " + size + " instead of " + TEST_SIZE,
                size == TEST_SIZE);

        //confirm park names
        String names[] = {"ParkName",
                "10TH AVE/CLEMENT MINI PARK",
                "15TH AVENUE STEPS",
                "24TH/YORK MINI PARK",
                "29TH/DIAMOND OPEN SPACE"};

        for (int i = 0; i < TEST_SIZE; i++) {
            assertTrue("Wrong park name for " + i,
                    parks.get(i).getParkNameCapitalized().compareTo(names[i]) == 0);
        }

        //confirm that have locations, except for first
        assertTrue("First should not have location",
                parks.get(0).getLocation() == null);

        String addresses[] = {"",
                "{\"address\":\"351 9th Ave\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}",
                "{\"address\":\"15th Ave b w Kirkham\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}",
                "{\"address\":\"24th\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}",
                "{\"address\":\"Diamond\",\"city\":\"San Francisco\",\"state\":\"CA\",\"zip\":\"\"}"};

        for (int i = 1; i < TEST_SIZE; i++) {
            ParkLocationResponse location = parks.get(i).getLocation();
            assertTrue("Don't have location for " + i,
                    location != null);

            //assert that have correct address
            assertTrue("Wrong address for " + i,
                    location.getUnformattedAddress().compareTo(addresses[i]) == 0);
        }
    }
}
