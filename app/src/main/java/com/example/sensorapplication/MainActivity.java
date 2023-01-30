package com.example.sensorapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
//I am Hiding the action bar for the splash screen to have a clean unobstructed splash screen

        actionBar.hide();
        final TextView versionCodeField = findViewById(R.id.textView);
        final TextView versionNameField = findViewById(R.id.textView2);
        versionCodeField.setText("Version Code: " + String.valueOf(versionCode));
        versionNameField.setText("Version Name: " + versionName);

//For the splash screen appearing then disappearing i'm using a thread which opens a screen then sleeps for
// 3350 milliseconds before changing the intent to my other screen which will contain sensors



        Thread thread = new Thread() {
            @Override
            public void run() {

                try {
                    sleep(3350);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(MainActivity.this, HubActivity.class));
                }
            }
        };
        thread.start();


    }
}