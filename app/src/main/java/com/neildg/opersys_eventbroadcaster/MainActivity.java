package com.neildg.opersys_eventbroadcaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.neildg.opersys_eventbroadcaster.debugging.Console;
import com.neildg.opersys_eventbroadcaster.platformtools.notifications.EventBroadcaster;
import com.neildg.opersys_eventbroadcaster.threads.ThreadManager;
import com.neildg.opersys_eventbroadcaster.ui.BucketHolder;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    private BucketHolder bucketHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Console.initialize(this);
        Console.show();
        EventBroadcaster.getInstance().attachActivity(this);

        this.initializeButtons();
        this.initializeViews();

    }

    private void initializeButtons() {
        Button debugButton = (Button) this.findViewById(R.id.debug_button);
        debugButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Console.show();
            }
        });

    }

    private void initializeViews() {
        //initialize bucket holder
        TextView[] bucketTextViews = new TextView[3];
        bucketTextViews[0] = (TextView) this.findViewById(R.id.bucket_text_view_1);
        bucketTextViews[1] = (TextView) this.findViewById(R.id.bucket_text_view_2);
        bucketTextViews[2] = (TextView) this.findViewById(R.id.bucket_text_view_3);

        this.bucketHolder = new BucketHolder(bucketTextViews);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.initializeLogicProper();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ThreadManager.destroy();
    }

    private void initializeLogicProper() {
        ThreadManager.initialize();
        ThreadManager.getInstance().setupThreads();

        //link toggle button actions to activate/deactivate producer threads
        ToggleButton toggleButton = (ToggleButton) this.findViewById(R.id.thread_toggle_1);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ThreadManager.getInstance().setThreadActive(0, b);
            }
        });

        toggleButton = (ToggleButton) this.findViewById(R.id.thread_toggle_2);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ThreadManager.getInstance().setThreadActive(1, b);
            }
        });

        toggleButton = (ToggleButton) this.findViewById(R.id.thread_toggle_3);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ThreadManager.getInstance().setThreadActive(2, b);
            }
        });

        toggleButton = (ToggleButton) this.findViewById(R.id.thread_toggle_4);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ThreadManager.getInstance().setThreadActive(3, b);
            }
        });
    }
}
