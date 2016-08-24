package com.hakoware.weatherinformation.helpers;

/**
 * Created by aaron on 8/23/16.
 */

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.hakoware.weatherinformation.activities.ZipCodeInfoActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 */
public class CallApi extends AsyncTask<String, String, String> {

    Exception mException = null;

    private Context mContext;
    public String mZipCode;
    // string constants
    private final String API_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private final String OPEN_MAP_API_KEY = "7cee2c704077ec90541dc7a890a34e7d";


    public CallApi (Context context){
        mContext = context;
    }

    /**
     * Method to make the URL connection,get the resulting JSON, and disconnect the URL connection
     *
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        mZipCode = params[0];

        String urlString = API_URL + "zip=" + mZipCode + "&units=imperial&appid=" + OPEN_MAP_API_KEY;
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
    }

    protected void onPreExecute() {
        super.onPreExecute();
    }

    protected void onPostExecute(String response) {
        if (response == null) {
            return;
        } else {
            Intent weatherResults = new Intent(mContext, ZipCodeInfoActivity.class);
            weatherResults.putExtra("result_json_set", response);
            weatherResults.putExtra("zipCode", mZipCode);
            mContext.startActivity(weatherResults);
        }
    }
}