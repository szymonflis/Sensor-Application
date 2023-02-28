package com.example.sensorapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.text.DecimalFormat;

public class EnvironmentSensors extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor temperature, humidity, pressure, light;
    private TextView temp, humid, pressureText, lightText;
    boolean tempSensPresent, humidityPresent, pressurePresent, lightPresent;
    boolean fahrenheit = false;
    private RadioGroup samplerates, unitChange;
    private int chosenRate = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment_sensors);
        temp = findViewById(R.id.textView7);
        humid = findViewById(R.id.textView8);
        pressureText = findViewById(R.id.textView9);
        lightText = findViewById(R.id.textView10);
        samplerates = (RadioGroup) findViewById(R.id.radioGroup);
        unitChange = (RadioGroup) findViewById(R.id.radioGroup2);


//      Get the sensor manager to check if sensors are present before assigning them
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
             temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
             tempSensPresent = true;
        }
        else{
            temp.setText("Temperature Unavailable");
            tempSensPresent = false;
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY) != null){
            humidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
            humidityPresent = true;
        }
        else {
            humid.setText("Humidity Unavailable");
            humidityPresent = false;
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE) != null){
            pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
            pressurePresent = true;
        }
        else {
            pressureText.setText("Pressure Unavailable");
            pressurePresent = false;
        }
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) != null){
            light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            lightPresent = true;
        }
        else {
            lightText.setText("Pressure Unavailable");
            lightPresent = false;
        }

        samplerates.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
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

        unitChange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = unitChange.getCheckedRadioButtonId();
                switch(id){
                    case R.id.radioButton12:
                        fahrenheit = false;
                        break;
                    case R.id.radioButton10:
                        fahrenheit = true;
                        break;

                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//      Since there are multiple sensor registered i need to differentiate which one is changing and set text accordingly
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMaximumFractionDigits(2);
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            if(fahrenheit){
                temp.setText("Temperature = " + df.format((sensorEvent.values[0] * 1.8 + 32)) + " °F");
            }
            else{temp.setText("Temperature = " + sensorEvent.values[0] + " °C");}
        }
        else if(sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY){
            humid.setText("Humidity = " + sensorEvent.values[0] + '%');
        }
        else if (sensor.getType() == Sensor.TYPE_PRESSURE){
            pressureText.setText("Pressure = " + sensorEvent.values[0] + "hPa");
        }
        else if (sensor.getType() == Sensor.TYPE_LIGHT){
            lightText.setText("Light = " + sensorEvent.values[0] + "lx");

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
//      Registering every listener for every environmental sensor
        super.onResume();
        if(tempSensPresent) {
            sensorManager.registerListener(this, temperature, chosenRate);
        }
        if(humidityPresent){
            sensorManager.registerListener(this, humidity, chosenRate);
        }
        if(pressurePresent){
            sensorManager.registerListener(this, pressure, chosenRate);
        }
        if(lightPresent){
            sensorManager.registerListener(this, light, chosenRate);
        }
    }

    //  On pause unregisters the battery receiver which will save power when the user is not on the battery screen
    @Override
    protected void onPause() {
        super.onPause();
        if(tempSensPresent) {
            sensorManager.unregisterListener(this);
        }
        if (humidityPresent) {
            sensorManager.unregisterListener(this);
        }
        if(pressurePresent){
            sensorManager.unregisterListener(this);
        }
        if(lightPresent){
            sensorManager.unregisterListener(this);
        }
    }



}