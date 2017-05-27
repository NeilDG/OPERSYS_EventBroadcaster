package com.neildg.opersys_eventbroadcaster.threads;

import android.util.Log;

import com.neildg.opersys_eventbroadcaster.debugging.Console;

import java.util.Random;

/**
 * Sample thread that performs something and then reports to the UI.
 * Created by NeilDG on 5/27/2017.
 */

public class WorkerThread extends Thread {
    private final static String TAG = "WorkerThread";

    private int threadID;

    public WorkerThread(int ID) {
        this.threadID = ID;
    }

    @Override
    public void run() {
        try {

            while(Console.isShown() == false) {
                Log.i(TAG, "Console is not yet shown. Waiting Thread ID: " +this.threadID);
                Thread.sleep(1000); //pause for 1 seconds
            }

            int randomDelay = randomRange(100, 5000);
            Console.log(TAG + "_"+threadID, "I'm going to do something after "+randomDelay+ " seconds!");
            Thread.sleep(randomDelay);
            Console.log(TAG + "_"+threadID, "Yehey! I did nothing!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int randomRange(int min, int max) {
        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
