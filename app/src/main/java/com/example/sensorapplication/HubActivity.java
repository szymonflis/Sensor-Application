package com.example.sensorapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.util.List;

public class HubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher_round);
//      Assigning model and brand of the emulated phone to a variable then setting to textview
        final String phoneMakeAndModel = "Model & Brand: " + Build.MODEL + " " + Build.BRAND;
        TextView modelAndMake = findViewById(R.id.textView3);
        modelAndMake.setText(phoneMakeAndModel);
//      Making calls to functions below to calculate storage
        float totalStorage = TotalAvailableStorage();
        float storageAvailable = AvailableRemainingStorage();
        String formattedStorage = AppUtils.FormatStorageValues(storageAvailable);


//      I want to create a progress bar type object for displaying the storage as a bar to see how close to being full it is

        final ProgressBar storageBar = findViewById(R.id.progressBar);
        storageBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
        storageBar.setMax(100);


        storageBar.setProgress(40);

//      Button which gives a user the ability to view all sensors on phone
        final Button sensorsButton = findViewById(R.id.button);
        sensorsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HubActivity.this, SensorListActivity.class));
            }
        });

//      Image Button which displays a battery symbol for internal sensors
        final ImageButton batterySensors = findViewById(R.id.imageButton);
        batterySensors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HubActivity.this, InternalSensors.class));
            }
        });
    }
    // This function will calculate the amount of blocks on the phone and their size multiplying to find total storage in bits
    public float TotalAvailableStorage() {
        File phonePath = Environment.getDataDirectory();
        StatFs stats = new StatFs(phonePath.getPath());
        long blockSize = stats.getBlockSizeLong();
        long bitAmount = stats.getBlockCountLong();
        return bitAmount * blockSize;
    }

    // This function will calculate the amount of remaining blocks on the phone and their size multiplying to find total storage in bits
    public float AvailableRemainingStorage() {
        File phonePath = Environment.getDataDirectory();
        StatFs stats = new StatFs(phonePath.getPath());
        long blockSize = stats.getBlockSizeLong();
        long availableBlocks = stats.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }
}