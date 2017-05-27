package com.neildg.opersys_eventbroadcaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hulab.debugkit.DebugFunction;
import com.hulab.debugkit.DevTool;
import com.neildg.opersys_eventbroadcaster.debugging.Console;
import com.neildg.opersys_eventbroadcaster.threads.WorkerThread;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Console.initialize(this);

        this.initializeButtons();

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

    @Override
    protected void onResume() {
        super.onResume();

        this.initiateDemo();
    }

    private void initiateDemo() {
        WorkerThread workerThread = new WorkerThread(1);
        workerThread.start();
    }
}
