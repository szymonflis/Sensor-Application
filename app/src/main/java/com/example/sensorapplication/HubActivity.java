package com.example.sensorapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

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
        float totalStorage = StorageHelper.TotalAvailableStorage();
        float storageAvailable = StorageHelper.AvailableRemainingStorage();
//      I am calculating the storage used as a percentage and using this as a plot on the Bar
//      There is an issue because this calculates storage available to the user
//      The emulated device has 8GB storage but 1.8GB is used by the System so only 6.2GB
//      Is available to the user and this is what i calculate and plot against.
        int storageUsed = (int) (((totalStorage - storageAvailable) / storageAvailable) * 100);

//      I want to create a progress bar type object for displaying the storage as a bar to see how close to being full it is

        final ProgressBar storageBar = findViewById(R.id.progressBar);
        final TextView storageLabel = findViewById(R.id.textView6);
        storageLabel.setText(StorageHelper.FormatStorageValues(totalStorage));
        storageBar.setProgressTintList(ColorStateList.valueOf(Color.RED));
//      Since this is a percentage calculated i can plot it out of 100
        storageBar.setMax(100);
        storageBar.setProgress(storageUsed);

//       Text underneath the progress bar

        final TextView progressBarText = findViewById(R.id.textView5);
        progressBarText.setText("^ Available storage used: " + storageUsed + "% ^");




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

        final ImageButton motionSensor = findViewById(R.id.imageButton6);
        motionSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HubActivity.this, MotionSensors.class));
            }
        });
    }

}