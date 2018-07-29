package com.example.m1039158.servicevideo;

import android.app.Application;
import android.content.Context;

/*set android:name=".ApplicationClass" in AndroidManifest.xml to Run ApplicationClass.class at the start of the application
 for setting up static variables...*/
public class ApplicationClass extends Application{

    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
