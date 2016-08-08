package com.example.srikar.parklocations.model;

import android.location.Location;
import android.support.test.runner.AndroidJUnit4;

import com.example.srikar.parklocations.BaseParksTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Tests aspects of ParkList not related to populating the list.
 * Created by Srikar on 8/8/2016.
 */
@RunWith(AndroidJUnit4.class)
public class ParkListTest extends BaseParksTest {
    private final ParkList parkList;

    public ParkListTest() {
        super();
        parkList = new ParkList();
    }

    @Before
    public void setUp() {
        //load parks from assets
        List<ParkResponse> parks = null;
        try {
            parks = loadParks();
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue("Opening assets failed",
                    false);
        }

        parkList.setParks(parks);
    }

    @After
    public void tearDown() {
        parkList.setParks(null);
        parkList.setLocation(null);
        parkList.setListChangeListener(null);
    }

    /**
     * Test size
     */
    @Test
    public void testSize() {
        int size = parkList.size();
        assertTrue("Wrong size, is " + size + " instead of " + TEST_SIZE,
                size == TEST_SIZE);
    }

    /**
     * Test sorting after add location
     */
    @Test
    public void testLocationSort() {
        //create location at (0,0)
        Location location = new Location("test");
        //this call will trigger the sort
        parkList.setLocation(location);

        testSortHelper();
    }

    /**
     * Test sorting after add parks
     */
    @Test
    public void testParksSort() {
        parkList.setParks(null);

        //create location at (0,0)
        Location location = new Location("test");
        //this call won't trigger sort with null park list
        parkList.setLocation(location);

        //load parks from assets
        List<ParkResponse> parks = null;
        try {
            parks = loadParks();
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue("Opening assets failed",
                    false);
        }

        //this call will trigger sort
        parkList.setParks(parks);

        testSortHelper();
    }

    private void testSortHelper() {
        //confirm park names
        //first will be the first entry, which has no location
        String names[] = {"ParkName",
                "24TH/YORK MINI PARK",
                "29TH/DIAMOND OPEN SPACE",
                "15TH AVENUE STEPS",
                "10TH AVE/CLEMENT MINI PARK"};

        for (int i = 0; i < TEST_SIZE; i++) {
            ParkResponse park = parkList.getPark(i);
            assertTrue("At " + i + ", have " + park.getParkNameCapitalized() + " instead of " + names[i],
                    park.getParkNameCapitalized().compareTo(names[i]) == 0);
        }
    }
}
