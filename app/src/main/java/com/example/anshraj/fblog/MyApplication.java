package com.example.anshraj.fblog;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ansh raj on 08-May-16.
 */
public class MyApplication extends Application{
public final static String TAG="MyApplication";
public static String  core="foreground";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"tag initialzed");
       // Toast.makeText(this,"roll back",Toast.LENGTH_LONG).show();
        //core="hey der";
       // FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);
 }
}
