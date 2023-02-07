package com.example.sensorapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class EnvironmentSensors extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor temperature;
    private Sensor humidity;
    private Sensor pressure;
    private Sensor light;
    private TextView temp;
    private TextView humid;
    private TextView pressureText;
    private TextView lightText;

    boolean tempSensPresent;
    boolean humidityPresent;
    boolean pressurePresent;
    boolean lightPresent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment_sensors);
        temp = findViewById(R.id.textView7);
        humid = findViewById(R.id.textView8);
        pressureText = findViewById(R.id.textView9);
        lightText = findViewById(R.id.textView10);
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
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//      Since there are multiple sensor registered i need to differentiate which one is changing and set text accordingly
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            temp.setText("Temperature = " + sensorEvent.values[0] + " °C");
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
            sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(humidityPresent){
            sensorManager.registerListener(this, humidity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(pressurePresent){
            sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(lightPresent){
            sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
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