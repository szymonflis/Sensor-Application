package com.example.sensorapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class HubActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher_round);
        final String phoneMakeAndModel = "Model & Brand: " + Build.MODEL + " " + Build.BRAND;
        TextView modelAndMake = findViewById(R.id.textView3);
        modelAndMake.setText(phoneMakeAndModel);
        final Button sensorsButton = findViewById(R.id.button);
        sensorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HubActivity.this, SensorListActivity.class));
            }
        });

    }



//    public int batteryPercentage(){
//
//
//        return int;
//    }
}