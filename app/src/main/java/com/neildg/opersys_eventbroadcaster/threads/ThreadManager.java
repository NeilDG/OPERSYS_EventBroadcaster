package com.neildg.opersys_eventbroadcaster.threads;

import java.util.ArrayList;

/**
 * Manager for worker threads
 * Created by NeilDG on 5/27/2017.
 */

public class ThreadManager {
    private final static String TAG = "ThreadManager";

    private static ThreadManager sharedInstance = null;
    public static ThreadManager getInstance() {
        return sharedInstance;
    }

    public static void initialize() {
        sharedInstance = new ThreadManager();
    }

    public static void destroy() {
        sharedInstance.terminateThreads();
        sharedInstance = null;
    }

    private ArrayList<ProducerThread> producerThreads = new ArrayList<>();

    private ThreadManager() {

    }

    public void setupThreads() {
        for(int i = 0; i < 4; i++) {
            ProducerThread producerThread = new ProducerThread(i);
            this.producerThreads.add(producerThread);
            producerThread.start();
        }
    }

    public void setThreadActive(int index, boolean active) {
        this.producerThreads.get(index).setActive(active);
    }

    private void terminateThreads() {
        for(int i = 0; i < this.producerThreads.size(); i++) {
            this.producerThreads.get(i).terminateThread();
        }

        this.producerThreads.clear();
    }
}
