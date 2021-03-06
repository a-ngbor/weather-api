package com.hakoware.weatherinformation.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hakoware.weatherinformation.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer.
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start the app main activity
                Intent i = new Intent(SplashActivity.this, MainZipCodeActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 2000);
    }
}
