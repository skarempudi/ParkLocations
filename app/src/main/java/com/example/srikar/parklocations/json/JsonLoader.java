package com.example.srikar.parklocations.json;

import android.util.Log;

import com.example.srikar.parklocations.ParkLocationsApplication;
import com.example.srikar.parklocations.model.ParkResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * Used to fetch JSON, either from saved file or from server
 * Created by Srikar on 8/7/2016.
 */
public class JsonLoader {
    private static final String TAG = "JsonLoader";

    public static final String FILE_NAME = "parks.json";

    public static List<ParkResponse> getListOnStart() {
        //check if already have parks saved as file
        //if do, return that

        //regardless of if saved as file, start fetching from server

        //if retrieve, save to file
        return null;
    }

    /**
     * Read list of parks saved to file.
     * @return List of parks, or null if not saved to file yet.
     */
    public static List<ParkResponse> readFromFile() {
        return readFromFile(FILE_NAME);
    }

    /**
     * Separated for testing
     * @param fileName Name of file to read from, don't include directory
     * @return List of parks from file
     */
    protected static List<ParkResponse> readFromFile(String fileName) {
        File dir = ParkLocationsApplication.getInstance().getFilesDir();
        File file = new File(dir, fileName);

        Gson gson = new Gson();
        List<ParkResponse> parks = null;
        try (Reader reader = new FileReader(file)) {
            parks = gson.fromJson(reader, new TypeToken<List<ParkResponse>>(){}.getType());
        }
        catch (FileNotFoundException e) {
            Log.d(TAG, "readFromFile: File not found");
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return parks;
    }

    /**
     * Save the given lists to file, overriding whatever is saved currently.
     * @param parks The list of parks to save, won't save if null or empty
     */
    public static void saveToFile(List<ParkResponse> parks) {
        saveToFile(FILE_NAME, parks);
    }

    /**
     * Separated for testing
     * @param fileName Name of file to read from, don't include directory
     * @param parks The list of parks to save, won't save if null or empty
     */
    public static void saveToFile(String fileName, List<ParkResponse> parks) {
        //check if parks is null or empty
        if (parks == null || parks.isEmpty()) {
            Log.d(TAG, "saveToFile: List of parks is empty, didn't save");
            return;
        }

        File dir = ParkLocationsApplication.getInstance().getFilesDir();
        File file = new File(dir, fileName);
        try (Writer writer = new FileWriter(file)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(parks, writer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
