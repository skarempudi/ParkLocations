package com.example.srikar.parklocations.json;

import android.support.test.runner.AndroidJUnit4;

import com.example.srikar.parklocations.BaseParksTest;
import com.example.srikar.parklocations.model.ParkResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Test methods for loading from and saving to file, even though not implemented in main code
 * Created by Srikar on 8/8/2016.
 */
@RunWith(AndroidJUnit4.class)
public class JsonLoaderTest extends BaseParksTest {
    public static final String FILE_NAME = "parks_test.json";
    private List<ParkResponse> parks;

    public JsonLoaderTest() {
        super();
    }

    @Before
    public void setUp() {
        //load parks from assets
        try {
            parks = loadParks();
        } catch (IOException e) {
            e.printStackTrace();
            assertTrue("Opening assets failed",
                    false);
        }
    }

    @After
    public void tearDown() {
        parks = null;
        //delete file
        File dir = context.getFilesDir();
        File file = new File(dir, FILE_NAME);
        file.delete();
    }

    /**
     * Save to test file, then load from it and see if matches
     */
    @Test
    public void testSaveAndLoad() {
        //save to file
        JsonLoader.saveToFile(FILE_NAME, parks);

        //read from that file
        List<ParkResponse> fileList = JsonLoader.readFromFile(FILE_NAME);

        //assert that same length
        assertTrue("Are not same length, are " + parks.size() + " and " + fileList.size(),
                parks.size() == fileList.size());

        //compare initial list and list from file
        for (int i = 0; i < TEST_SIZE; i++) {
            ParkResponse original = parks.get(i);
            ParkResponse copy = fileList.get(i);

            //compare names
            assertTrue("Park names are not the same, are " + original.getParkNameCapitalized() + " and "
                    + copy.getParkNameCapitalized(),
                    original.getParkNameCapitalized().compareTo(copy.getParkNameCapitalized()) == 0);
        }
    }
}
