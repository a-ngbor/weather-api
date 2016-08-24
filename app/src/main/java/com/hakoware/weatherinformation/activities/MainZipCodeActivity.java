package com.hakoware.weatherinformation.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.hakoware.weatherinformation.R;
import com.hakoware.weatherinformation.helpers.CallApi;

import java.util.ArrayList;

public class MainZipCodeActivity extends AppCompatActivity {

    public String zipCodeValue;
    public final ArrayList<String> zipCodes = new ArrayList<String>();
    public AlertDialog deleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zipCodes.add("75081");
        zipCodes.add("78704");
        zipCodes.add("78752");
        setContentView(R.layout.activity_main_zip_code);
        final View activtyView = findViewById(android.R.id.content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, zipCodes);

        final ListView listView = (ListView) findViewById(R.id.mobile_list);
        adapter.notifyDataSetChanged();
        listView.setLongClickable(true);
        listView.setAdapter(adapter);

        // call api and start new activity with details on short press
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                new CallApi(MainZipCodeActivity.this).execute((String) listView.getItemAtPosition(position));
            }
        });

        // option to remove from the list on long press
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> l, View v, final int position, long id) {
                // Dialog for remove zip code long press
                AlertDialog.Builder deleteDialogBuilder = new AlertDialog.Builder(MainZipCodeActivity.this);
                deleteDialogBuilder.setTitle("Remove Zip Code?");
                deleteDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        zipCodes.remove(position);
                        adapter.notifyDataSetChanged();
                    }});
                deleteDialogBuilder.setNegativeButton("Cancel", null);
                deleteDialog = deleteDialogBuilder.create();
                deleteDialog.show();
                return true;
            }
        });

        // Dialog for add zip code button
        LayoutInflater inflater = MainZipCodeActivity.this.getLayoutInflater();
        final View addZipDialog = inflater.inflate(R.layout.zip_code_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainZipCodeActivity.this);
        alertDialogBuilder.setView(addZipDialog);
        alertDialogBuilder.setTitle("Add New Zip Code");
        alertDialogBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText zipCodeInput = (EditText) addZipDialog.findViewById(R.id.zipCodeInput);
                zipCodeValue = zipCodeInput.getText().toString();
                if (zipCodes.contains(zipCodeValue)) {
                    Snackbar.make(activtyView, "Duplicate " + zipCodeValue + " not added.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    zipCodes.add(zipCodeValue);
                    Snackbar.make(activtyView, "Added " + zipCodeValue + " to list.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                new CallApi(MainZipCodeActivity.this).execute(zipCodeValue);
            }});
        alertDialogBuilder.setNegativeButton("Cancel", null);
        final AlertDialog alertDialog = alertDialogBuilder.create();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                alertDialog.show();
            }
        });
    }

}
