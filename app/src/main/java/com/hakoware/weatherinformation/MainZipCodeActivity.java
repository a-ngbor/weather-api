package com.hakoware.weatherinformation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainZipCodeActivity extends AppCompatActivity {

    String[] zipCodes = new String[] {"75081", "78704", "78752" };
    // string constants
    private final String API_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private final String OPEN_MAP_API_KEY = "7cee2c704077ec90541dc7a890a34e7d";
    private final long UPDATE_INTERVAL = 5000;
    private final long FASTEST_INTERVAL = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_zip_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, zipCodes);

        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_SHORT)
                        .show();
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainZipCodeActivity.this);
                alertDialogBuilder.setTitle("Add New Zip Code");
                alertDialogBuilder.setPositiveButton("Confirm", null);
                alertDialogBuilder.setNegativeButton("Cancel", null);
                AlertDialog dialog2 = alertDialogBuilder.create();
                dialog2.show();
            }
        });
    }

}
