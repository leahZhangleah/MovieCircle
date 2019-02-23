package com.example.android.moviecircle.moviedetail;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

public class ImageSizeHelper {
    private Context context;
    private static final String TAG = "ImageSizeHelper";

    public ImageSizeHelper(Context context) {
        this.context = context;
    }

    public int checkDeviceScreenWidth(){
        /*
        //return float: 0.75,1.0,1.5,2.0,3.0,4.0
        float multiply = context.getResources().getDisplayMetrics().density;
        //return LANDSCAPE,PORTRAIT
        int orientation = context.getResources().getConfiguration().orientation;
        //return 0,90,180,270
        int rotation = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();*/

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        Log.d(TAG,"screen width is: "+width);
        return width;
    }

    public String getMovieBackdropSize(){
        int screenWidth = checkDeviceScreenWidth();
        String sizeOnTMDB;
        if(screenWidth <= 320){
            sizeOnTMDB = "w300";
        }else if(screenWidth <= 480){
            sizeOnTMDB = "w342";
        }else if (screenWidth  <= 640){
            sizeOnTMDB = "h632";
        }else if(screenWidth <= 720){
            sizeOnTMDB = "w500";
        }else if(screenWidth <= 800){
            sizeOnTMDB = "w780";
        }else{
            sizeOnTMDB = "w1280";
        }

        /*
        if(screenWidth <92){
            sizeOnTMDB = "w45";
        } else if(screenWidth < 154){
            sizeOnTMDB = "w92";
        } else if( screenWidth < 185){
            sizeOnTMDB = "w154";
        } else if(screenWidth < 300){
            sizeOnTMDB = "w185";
        }else if(screenWidth < 342){
            sizeOnTMDB = "w300";
        }else if(screenWidth < 421){
            sizeOnTMDB = "w342";
        }else if(screenWidth < 500){
            sizeOnTMDB = "w421";
        }else if(screenWidth < 780){
            sizeOnTMDB = "w500";
        }else if (screenWidth < 1280){
            sizeOnTMDB = "w780";
        } else{
            sizeOnTMDB = "w1280";
        }*/
        Log.d(TAG,"backdrop size is: "+sizeOnTMDB);
        return sizeOnTMDB;
    }


    public String getMoviePosterSize(){
        int imageWidth = getImageViewWidth();
        String sizeOnTMDB;
        if(imageWidth <= 154){
            sizeOnTMDB = "w92";
        }else if(imageWidth <= 185){
            sizeOnTMDB = "w154";
        } else if(imageWidth <= 342){
            sizeOnTMDB = "w185";
        }else if(imageWidth <= 500){
            sizeOnTMDB = "w342";
        }else {
            sizeOnTMDB = "w500";
        }
        Log.d(TAG,"poster size is: "+sizeOnTMDB);
        return sizeOnTMDB;
    }



    public String getCastProfileSize(){
        int imageWidth = getImageViewWidth();
        String profileSize;
        /*
        int screenWidth = checkDeviceScreenWidth();
        if(screenWidth / 3 <= 45){
            profileSize = "w45";
        }else if(screenWidth /3 <= 92){
            profileSize = "w92";
        }else if(screenWidth /3 <= 185){
            profileSize = "w185";
        }else if(screenWidth /3 <= 300){
            profileSize = "w300";
        }else{
            profileSize = "h632";
        }*/
        if(imageWidth <= 92){
            profileSize = "w45";
        }else if(imageWidth <= 185){
            profileSize = "w92";
        }else if(imageWidth <= 300){
            profileSize = "w185";
        }else {
            profileSize = "w300";
        }
        Log.d(TAG,"profile size is: "+profileSize);
        return profileSize;
    }


    public int getImageViewWidth(){
        int screenWidth = checkDeviceScreenWidth();
        int imageWidth;
        /*
        if(screenWidth <= 320){
            imageWidth = screenWidth / 2;
        }else if(screenWidth <= 480){
            imageWidth = screenWidth / 2;
        }else if (screenWidth  <= 640){
            imageWidth = screenWidth / 4;
        }else if(screenWidth <= 720){
            imageWidth = screenWidth / 3;
        }
        else if(screenWidth <= 800){
            imageWidth = screenWidth / 5;
        }else if(screenWidth <= 960){
            imageWidth = screenWidth / 6;
        } else if(screenWidth <= 1080){
            imageWidth = screenWidth / 8;
        }else if(screenWidth <= 1440){
            imageWidth = screenWidth / 9;
        }else if(screenWidth <= 1600){
            imageWidth = screenWidth / 10;
        }else if(screenWidth <= 1920){
            imageWidth = screenWidth / 12;
        }else{
            imageWidth = screenWidth / 16;
        }*/
        if(screenWidth <= 480){
            imageWidth = screenWidth / 2;
        }else if(screenWidth <= 720){
            imageWidth = screenWidth / 3;
        } else if(screenWidth <= 960){
            imageWidth = screenWidth / 4;
        } else if(screenWidth <= 1200){
            imageWidth = screenWidth / 5;
        }else if(screenWidth <= 1440){
            imageWidth = screenWidth / 6;
        }else if(screenWidth <= 1680){
            imageWidth = screenWidth / 7;
        }else if(screenWidth <= 1920){
            imageWidth = screenWidth / 8;
        }else{
            imageWidth = screenWidth / 9;
        }
        Log.d(TAG,"image size is: "+imageWidth);
        return imageWidth;
    }

}
