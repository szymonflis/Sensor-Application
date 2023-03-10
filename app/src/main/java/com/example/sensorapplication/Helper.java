package com.example.sensorapplication;

import android.os.Environment;
import android.os.StatFs;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;

public class Helper {
    public static ArrayList<String> logs = new ArrayList<>();
    private static StringBuilder stringBuilder = new StringBuilder();


    //This formats the huge numbers retrieved previously into a Kb, Mb, Gb format
    public static String FormatStorageValues(float storageVal){

        StringBuilder storageString = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.##");
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
        storageString.append(df.format(storageVal));
        storageString.append(label);
        return storageString.toString();
    }

    // This function will calculate the amount of blocks on the phone and their size multiplying to find total storage in bits
    public static float TotalAvailableStorage() {
        File phonePath = Environment.getDataDirectory();
        StatFs stats = new StatFs(phonePath.getPath());
        long blockSize = stats.getBlockSizeLong();
        long bitAmount = stats.getBlockCountLong();
        return bitAmount * blockSize;
    }

    // This function will calculate the amount of remaining blocks on the phone and their size multiplying to find total storage in bits
    public static float AvailableRemainingStorage() {
        File phonePath = Environment.getDataDirectory();
        StatFs stats = new StatFs(phonePath.getPath());
        long blockSize = stats.getBlockSizeLong();
        long availableBlocks = stats.getAvailableBlocksLong();
        return availableBlocks * blockSize;
    }

    public static void BuildLogs(Instant time, String writable){
//      I'm going to build a string which contains the time of the sensor change and the values then store in an array
        stringBuilder.append(time);
        stringBuilder.append(writable);
        logs.add(stringBuilder.toString());
        if (logs.size() > 10){
            logs.clear();
        }
    }
}
