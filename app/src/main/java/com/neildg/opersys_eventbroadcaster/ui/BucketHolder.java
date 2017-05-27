package com.neildg.opersys_eventbroadcaster.ui;

import android.widget.TextView;

import com.neildg.opersys_eventbroadcaster.platformtools.core_application.RandomUtil;
import com.neildg.opersys_eventbroadcaster.platformtools.notifications.EventBroadcaster;
import com.neildg.opersys_eventbroadcaster.platformtools.notifications.EventListener;
import com.neildg.opersys_eventbroadcaster.platformtools.notifications.EventNames;
import com.neildg.opersys_eventbroadcaster.platformtools.notifications.Parameters;

/**
 * A class that holds number buckets associated with a UI
 * Created by NeilDG on 5/27/2017.
 */

public class BucketHolder implements EventListener {
    private final static String TAG = "BucketHolder";

    public final static int BUCKET_1 = 0;
    public final static int BUCKET_2 = 1;
    public final static int BUCKET_3 = 3;

    public final static String NUMBER_PRODUCE_KEY = "NUMBER_PRODUCE_KEY";

    private TextView[] bucketTextViews;
    private int[] bucketList;

    public BucketHolder(TextView[] bucketTextViews) {
        this.bucketTextViews = bucketTextViews;
        this.bucketList = new int[this.bucketTextViews.length];

        EventBroadcaster.getInstance().removeObserver(EventNames.ON_NUMBER_PRODUCED, this); //remove any attached listeners in case
        EventBroadcaster.getInstance().addObserver(EventNames.ON_NUMBER_PRODUCED, this);
    }

    public void putNumber(int number, int bucketIndex) {
        this.bucketList[bucketIndex] += number;
        this.bucketTextViews[bucketIndex].setText(Integer.toString(this.bucketList[bucketIndex]));
    }

    public void putNumberToRandomBucket(int number) {
        int randomIndex = RandomUtil.randomRangeExclusive(0, this.bucketList.length);
        this.putNumber(number, randomIndex);
    }

    @Override
    public void onNotify(String notificationString, Parameters params) {
        if(notificationString == EventNames.ON_NUMBER_PRODUCED) {
            int randomNumber = params.getIntExtra(NUMBER_PRODUCE_KEY, 0);
            this.putNumberToRandomBucket(randomNumber);
        }
    }
}
