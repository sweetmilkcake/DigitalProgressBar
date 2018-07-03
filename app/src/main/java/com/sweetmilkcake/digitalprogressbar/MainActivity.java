package com.sweetmilkcake.digitalprogressbar;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    DigitalProgressBar mDigitalProgressBar;
    private int mProgress = 0;
    private final static int MSG_UPDATE = 1;
    private final static int UPDATE_INTERVAL = 100;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_UPDATE:
                    mProgress++;
                    if (mProgress > 100) {
                        mProgress = 0;
                    }
                    mDigitalProgressBar.setProgress(mProgress);
                    sendEmptyMessageDelayed(MSG_UPDATE, UPDATE_INTERVAL);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDigitalProgressBar = (DigitalProgressBar) findViewById(R.id.digital_progress_bar);

        mHandler.sendEmptyMessageDelayed(MSG_UPDATE, UPDATE_INTERVAL);
    }

}
