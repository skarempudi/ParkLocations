package com.example.srikar.parklocations.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.test.InstrumentationRegistry;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * Base class for tests based on model classes
 * Created by Srikar on 8/8/2016.
 */
public class BaseModelTest {
    protected static final String ASSET_PARKS_FILE = "parks.json";
    protected static final int TEST_SIZE = 5;
    protected final Context context;

    public BaseModelTest() {
        //gets test context
        context = InstrumentationRegistry.getContext();
    }

    /**
     * Load list of parks from assets JSON file
     * @return List of parks
     * @throws IOException
     */
    protected List<ParkResponse> loadParks() throws IOException {
        AssetManager assetManager = context.getAssets();
        Gson gson = new Gson();

        //load from file
        List<ParkResponse> parks = null;
        //by trying with resources, will close even if throw exception, won't have to do in finally
        try(InputStream in = assetManager.open(ASSET_PARKS_FILE);
            Reader reader = new InputStreamReader(in)) {

            Type listType = new TypeToken<List<ParkResponse>>(){}.getType();
            parks = gson.fromJson(reader, listType);
        }

        return parks;
    }
}
