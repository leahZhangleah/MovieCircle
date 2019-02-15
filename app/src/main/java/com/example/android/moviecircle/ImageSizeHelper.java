package com.example.android.moviecircle;

import android.content.Context;
import android.util.DisplayMetrics;

public class ImageSizeHelper {
    private Context context;

    public ImageSizeHelper(Context context) {
        this.context = context;
    }

    private int checkDeviceDensityForImageWidth(){
        int BASE_PX = 100;
        double multiply = 1.0;
        int density = context.getResources().getDisplayMetrics().densityDpi;
        switch (density){
            case DisplayMetrics.DENSITY_LOW:
                multiply = 0.75;
            case DisplayMetrics.DENSITY_MEDIUM:
                multiply = 1.0;
            case DisplayMetrics.DENSITY_TV:
            case DisplayMetrics.DENSITY_HIGH:
                multiply = 1.5;
            case DisplayMetrics.DENSITY_260:
            case DisplayMetrics.DENSITY_280:
            case DisplayMetrics.DENSITY_300:
            case DisplayMetrics.DENSITY_XHIGH:
                multiply = 2.0;
            case DisplayMetrics.DENSITY_340:
            case DisplayMetrics.DENSITY_360:
            case DisplayMetrics.DENSITY_400:
            case DisplayMetrics.DENSITY_420:
            case DisplayMetrics.DENSITY_440:
            case DisplayMetrics.DENSITY_XXHIGH:
                multiply = 3.0;
            case DisplayMetrics.DENSITY_560:
            case DisplayMetrics.DENSITY_XXXHIGH:
                multiply = 4.0;
        }
        return (int)(BASE_PX * multiply);
    }

    public String getPosterSize(){
        int imageWidth = checkDeviceDensityForImageWidth();
        String sizeOnTMDB = "w92";
        switch (imageWidth){
            case 75:
            case 100:
                sizeOnTMDB =  "w92";
            case 150:
                sizeOnTMDB = "w154";
            case 200:
                sizeOnTMDB = "w185";
            case 300:
            case 400:
                sizeOnTMDB = "w342";
        }
        return sizeOnTMDB;
    }
}
