package com.example.sensorapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SensorListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_list);
        final Spinner sensorsSpinner = findViewById(R.id.spinner);
        final TextView sensorInfo = findViewById(R.id.textView4);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        StringBuilder stringBuilder = new StringBuilder();
        // Initialising the sensor manager and assigning it the sensor service from the emulated phone
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Create a List which stores all sensors
        List<Sensor> sensorList = sensorManager.getSensorList(-1);
        // Call my method to get all of the names of the sensors into a list
        ArrayList<String> sensors = getSensorNameList(sensorList);
        // Create an array adapter which will be able to set the content of the spinner through java
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,sensors);arrayAdapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        sensorsSpinner.setAdapter(arrayAdapter);

        // Find out which item is currently selected by spinner
        sensorsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

//          Lop through the sensors and compare to selected spinner then print the information on this sensor
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                for (Sensor s : sensorList) {
                    String selectedSensor = sensorsSpinner.getSelectedItem().toString();
                    if (s.getName().equals(selectedSensor)){
                        stringBuilder.append("Max Delay: ").append(s.getMaxDelay()).append("\n");
                        stringBuilder.append("Min Delay: ").append(s.getMinDelay()).append("\n");
                        stringBuilder.append("Max Range: ").append(s.getMaximumRange()).append("\n");
                        stringBuilder.append("Name: ").append(s.getName()).append("\n");
                        stringBuilder.append("Power: ").append(s.getPower()).append("\n");
                        stringBuilder.append("Resolution: ").append(s.getResolution()).append("\n");
                        stringBuilder.append("Permission Required: ").append(s.getHighestDirectReportRateLevel());
                        sensorInfo.setText(stringBuilder);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public ArrayList<String> getSensorNameList(List<Sensor> sensorList){
        ArrayList<String> sensorNames = new ArrayList<>();
        // Creating a list which will store available sensors, -1 refers to sensor.TYPE_ALL
        // Loop through the list of sensors and add the name to an array
        for (int i = 0; i < sensorList.size(); i++) {
            sensorNames.add((sensorList.get(i).getName()));

        }
        return sensorNames;

    }
}