package com.hakoware.weatherinformation.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.hakoware.weatherinformation.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class ZipCodeInfoActivity extends AppCompatActivity {

    private String dataSet = "";
    private TextView cityName;
    private TextView currentTemp;
    private TextView windInfo;
    private TextView cloudinessInfo;
    private TextView pressureInfo;
    private TextView humidityInfo;
    private TextView sunriseInfo;
    private TextView sunsetInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final AppCompatActivity activity = this;
        setContentView(R.layout.activity_zip_code_info);
        cityName = (TextView) findViewById(R.id.cityName);
        currentTemp = (TextView) findViewById(R.id.currentTemp);
        windInfo = (TextView) findViewById(R.id.windInfo);
        cloudinessInfo = (TextView) findViewById(R.id.cloudinessInfo);
        pressureInfo = (TextView) findViewById(R.id.pressureInfo);
        humidityInfo = (TextView) findViewById(R.id.humidityInfo);
        sunriseInfo = (TextView) findViewById(R.id.sunriseInfo);
        sunsetInfo = (TextView) findViewById(R.id.sunsetInfo);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                dataSet = "null";
            }
            else {
                dataSet = extras.getString("result_json_set");
                activity.setTitle("Weather Details - " + extras.getString("zipCode"));
            }
        }
        else {
            dataSet = (String) savedInstanceState.getSerializable("result_json_set");
        }
        // only try to update the default values if JSON is retrieved
        if(!dataSet.equals("") && !dataSet.contains("Error") && !dataSet.contains("404")) {
            updateDisplayFromJSON();
        }
        else {
            // alert the user that something went wrong
            Toast.makeText(this, "There was an error retrieving the data. Please try again or contact the developer, Aaron.",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private void updateDisplayFromJSON() {
        try {
            // get JSON for various weather attributes
            JSONObject weatherJSON = new JSONObject(dataSet);
            JSONObject mainInfoJSON = weatherJSON.getJSONObject("main");
            JSONObject windJSON = weatherJSON.getJSONObject("wind");
            JSONObject cloudJSON = weatherJSON.getJSONObject("clouds");
            JSONObject sunJSON = weatherJSON.getJSONObject("sys");

            // Set text based on results
            cityName.setText(weatherJSON.getString("name"));
            currentTemp.setText("Temperature is " + mainInfoJSON.getString("temp") + "\u00b0" + "F");
            windInfo.setText(windJSON.getString("speed") + " miles per hour at " + windJSON.getString("deg") + "\u00b0");
            cloudinessInfo.setText(cloudJSON.getString("all") + "%");
            pressureInfo.setText(mainInfoJSON.getString("pressure") + " hPa");
            humidityInfo.setText(mainInfoJSON.getString("humidity") + "%");
            Date sunriseDate = new java.util.Date(Long.parseLong(sunJSON.getString("sunrise")) * 1000L);
            Date sunsetDate = new java.util.Date(Long.parseLong(sunJSON.getString("sunset")) * 1000L);
            sunriseInfo.setText(sunriseDate.toString());
            sunsetInfo.setText(sunsetDate.toString());



        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
