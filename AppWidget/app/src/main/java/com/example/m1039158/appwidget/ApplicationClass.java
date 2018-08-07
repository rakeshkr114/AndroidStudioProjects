package com.example.m1039158.appwidget;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

//Runs right before the application starts. Even before the MainActivity (Note: Extending Application class )
public class ApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /* ApplicationClass will become available as soon as the application starts */
    //Checks for Network types Mobile/WIFI
    public static boolean connectionAvailable(Context context) {

        int[] networkTypes = {ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_WIFI};
        try {
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //iterating over network types
            for (int networkType : networkTypes) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                // If we have internet connection active return true
                if (activeNetworkInfo != null &&
                        activeNetworkInfo.getType() == networkType)
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
