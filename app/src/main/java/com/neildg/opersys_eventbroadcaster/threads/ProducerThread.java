package com.neildg.opersys_eventbroadcaster.threads;

import android.util.Log;

import com.neildg.opersys_eventbroadcaster.debugging.Console;
import com.neildg.opersys_eventbroadcaster.platformtools.core_application.RandomUtil;
import com.neildg.opersys_eventbroadcaster.platformtools.notifications.EventBroadcaster;
import com.neildg.opersys_eventbroadcaster.platformtools.notifications.EventNames;
import com.neildg.opersys_eventbroadcaster.platformtools.notifications.Parameters;
import com.neildg.opersys_eventbroadcaster.ui.BucketHolder;

import java.util.concurrent.Semaphore;

/**
 * Thread that produces a random number after X seconds
 * Created by NeilDG on 5/27/2017.
 */

public class ProducerThread extends Thread {
    private final static String TAG = "ProducerThread";

    private int threadID;

    private boolean persistent = true;
    private boolean active = false;
    private Semaphore flag; //to be discussed in process synchronization

    public ProducerThread(int threadID) {
        this.threadID = threadID;
        this.flag = new Semaphore(1);
    }

    @Override
    public void run() {

        try {
            while(this.persistent) {
                this.flag.acquire();
                Log.d(TAG, "Thread " +this.threadID+ " setup!");

                while(this.active) {
                    int randomDelay = RandomUtil.randomRangeInclusive(500, 5000);
                    Console.log("Thread " +this.threadID+ " producing a number after " +randomDelay+ " ms.");

                    Thread.sleep(randomDelay);

                    int randomNumber = RandomUtil.randomRangeInclusive(0, 100000);
                    Console.log("Thread " +this.threadID+ " produced a number: " +randomNumber);

                    ///big question. How do we pass the randomnumber to the bucket holder without passing the bucket holder as reference to this thread?
                    //passing the bucket holder reference is a violation of an OOP concept.

                    //SOLUTION: use an eventbroadcaster to report to the bucket holder
                    Parameters parameters = new Parameters();
                    parameters.putExtra(BucketHolder.NUMBER_PRODUCE_KEY, randomNumber);
                    EventBroadcaster.getInstance().postNotification(EventNames.ON_NUMBER_PRODUCED, parameters);
                }

            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void setActive(boolean flag) {
        this.active = flag;
        this.flag.release();
    }

    public void terminateThread() {
        this.persistent = false;
    }
}
