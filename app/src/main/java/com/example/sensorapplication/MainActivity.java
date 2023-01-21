package com.example.sensorapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3250);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(MainActivity.this, HubActivity.class));
                }
            }
        };
        thread.start();

//
//        Handler handler = new Handler({});
//        handler.postDelayed({startActivity(new Intent(MainActivity.this, HubActivity.class))}, 3000);
//


    }
}