package com.example.sensorapplication;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.text.DecimalFormat;

public class StorageHelper {

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



}
