package com.neildg.opersys_eventbroadcaster.debugging;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.Log;

import com.hulab.debugkit.DevTool;

/**
 * Singleton console class for viewing of console logs.
 * Created by NeilDG on 5/27/2017.
 */

public class Console {
    private final static String TAG = "Console";

    private static Console sharedInstance = null;

    private Activity activity;
    private DevTool.Builder debugBuilder;
    private boolean shown = false;

    private Console() {

    }

    public static void initialize(Activity activity) {
        sharedInstance = new Console();
        sharedInstance.activity = activity;
        sharedInstance.debugBuilder = new DevTool.Builder(sharedInstance.activity);
    }

    public static void destroy() {
        if(sharedInstance == null) {
            sharedInstance.debugBuilder = null;
            sharedInstance = null;
        }
    }

    public static void show() {
        if(sharedInstance.debugBuilder != null) {
            Fragment fragment = sharedInstance.activity.getFragmentManager().findFragmentById(sharedInstance.debugBuilder.getTool().getId());
            if(fragment == null) {
                sharedInstance.debugBuilder.build();
                sharedInstance.shown = true;
            }
        }
    }

    public static void hide() {
        if(sharedInstance.debugBuilder != null && sharedInstance.debugBuilder.getTool() != null) {
            FragmentTransaction fragmentTransaction = sharedInstance.activity.getFragmentManager().beginTransaction();
            fragmentTransaction.remove(sharedInstance.debugBuilder.getTool());
            fragmentTransaction.commit();
            sharedInstance.shown = false;
        }
    }

    public static void log(String message) {
        if(sharedInstance != null) {
            sharedInstance.debugBuilder.getTool().log(message);
        }
        else {
            Log.e(TAG, "Console not yet initialized!");
        }
    }

    public static void log(final String tag, final String message) {
        //Not running this on a UI thread will cause the program to crash.
        sharedInstance.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                log("[" +tag+ "], " +message);
            }
        });

    }

    public static boolean isShown() {
        return sharedInstance.shown;
    }


}
