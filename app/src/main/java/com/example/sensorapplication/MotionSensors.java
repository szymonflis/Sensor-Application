package com.example.sensorapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.security.Timestamp;
import java.time.Instant;

public class MotionSensors extends AppCompatActivity implements SensorEventListener {

    private TextView accelX, accelY, accelZ, gyroX, gyroY, gyroZ;
    private Sensor accelerometer, gyroscope;
    private SensorManager sensorManager;
    private boolean accelPresent, gyroPresent, recording;
    private RadioGroup samplerates;
    private int chosenRate = 3;
    private Button viewLogs;
    private Switch recordLogs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_sensors);
        accelX = findViewById(R.id.textView11);
        accelY = findViewById(R.id.textView12);
        accelZ = findViewById(R.id.textView13);
        gyroX = findViewById(R.id.textView17);
        gyroY = findViewById(R.id.textView18);
        gyroZ = findViewById(R.id.textView19);
        viewLogs = findViewById(R.id.button2);
        recordLogs = (Switch) findViewById(R.id.switch1);

//      Identify radio group to see which sampling rate is selected
        samplerates = (RadioGroup) findViewById(R.id.radioGroup);

//      Assign sensor managers Check if the accelerometer is present and assign
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            accelPresent = true;
        }
//      check if gyroscope is present and assign
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) != null){
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            gyroPresent = true;
        }
        samplerates.check(R.id.radioButton);
        samplerates.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//              Check which button is selected for sampling rate of sensor, normal is the default
                int id = samplerates.getCheckedRadioButtonId();
                switch(id){
                    case R.id.radioButton:
                        chosenRate = SensorManager.SENSOR_DELAY_NORMAL;
                        break;
                    case R.id.radioButton3:
                        chosenRate = SensorManager.SENSOR_DELAY_FASTEST;
                        break;
                    case R.id.radioButton4:
                        chosenRate = SensorManager.SENSOR_DELAY_GAME;
                        break;
                    case R.id.radioButton5:
                        chosenRate = SensorManager.SENSOR_DELAY_UI;
                        break;
                }
            }
        });


        viewLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MotionSensors.this, LogDisplay.class));
            }
        });
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        Instant instant = Instant.now();
        recording = recordLogs.isChecked();
//      Check if the changed sensor is the accelerometer or gyroscope and write values
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            if(recording){
//              if the switch for recording logs is ticked then call the log builder helper class
                Helper.BuildLogs(instant, " Accelerometer Value changed: X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z: " + sensorEvent.values[2]);
            }
            accelX.setText("xValues: " + sensorEvent.values[0]);
            accelY.setText("yValues: " + sensorEvent.values[1]);
            accelZ.setText("zValues: " + sensorEvent.values[2]);
        }
        else if (sensor.getType() == Sensor.TYPE_GYROSCOPE){
            if(recording) {
                Helper.BuildLogs(instant, " Gyroscope Value changed: X: " + sensorEvent.values[0] + " Y: " + sensorEvent.values[1] + " Z: " + sensorEvent.values[2]);
            }
            gyroX.setText("xValues: " + sensorEvent.values[0]);
            gyroY.setText("yValues: " + sensorEvent.values[1]);
            gyroZ.setText("zValues: " + sensorEvent.values[2]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    protected void onResume() {
        super.onResume();
//      if accelerometer is present register the listener
        if (accelPresent){
            sensorManager.registerListener(this, accelerometer, chosenRate);
        }
        if (gyroPresent){
            sensorManager.registerListener(this, gyroscope, chosenRate);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//      On pause check if sensors are present and unregister listeners
        if (accelPresent) {
            sensorManager.unregisterListener(this);
        } else if (gyroPresent) {
            sensorManager.unregisterListener(this);
        }

    }
}