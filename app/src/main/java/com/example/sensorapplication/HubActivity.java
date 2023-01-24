package com.example.sensorapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
        float totalStorage = TotalAvailableStorage();
        float storageLeft = AvailableRemainingStorage();

        String formattedStorage = FormatStorageValues(storageLeft);
        System.out.println(formattedStorage);

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


//This formats the huge numbers retrieved previously into a Kb, Mb, Gb format
    public String FormatStorageValues(float storageVal) {
        StringBuilder storageString = new StringBuilder();
        String label;
        if (storageVal >= 1024) {
            storageVal = storageVal / 1024;
                label = "KB";
            if (storageVal >= 1024) {
                storageVal = storageVal / 1024;
                label = "MB";
                if (storageVal >= 1024) {
                    storageVal = storageVal / 1024;
                    label = "GB";
                }
            }
        }
        else{
            label = "";
        }
        storageString.append(storageVal);
        storageString.append(label);
        return storageString.toString();
    }
}