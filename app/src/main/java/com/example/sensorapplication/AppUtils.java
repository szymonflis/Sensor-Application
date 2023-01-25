package com.example.sensorapplication;

import java.text.DecimalFormat;

public class AppUtils {

    //This formats the huge numbers retrieved previously into a Kb, Mb, Gb format
    public static String FormatStorageValues(float storageVal){

        StringBuilder storageString = new StringBuilder();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
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
