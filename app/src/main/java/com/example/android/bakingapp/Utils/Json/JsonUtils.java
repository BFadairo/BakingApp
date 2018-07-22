package com.example.android.bakingapp.Utils.Json;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;

public class JsonUtils {

    public final static String LOG_TAG = JsonUtils.class.getSimpleName();

    public static String getSampleData() {
        Log.v(LOG_TAG, "" + JsonUtils.class.getResource("app/src/main/sampledata/baking.json"));
        return readFile("app/src/main/sampledata/baking.json");
    }

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
