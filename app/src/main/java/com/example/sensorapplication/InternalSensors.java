package com.example.sensorapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class InternalSensors extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internal_sensors);

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    }
}