package com.example.sokol.myapplication;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by Sokol on 11.03.2017.
 */

public class Chronometr implements Runnable {

    public static final long MILLIS_TO_MINUTES = 60000;
    public static final long MILLIS_TO_HOURS = 3600000;

    private Context tvContext;
    private long mStartTime;
    private boolean mIsRunning;

    public Chronometr(Context tvContext) {
        this.tvContext = tvContext;
    }

    public void start(){

        mStartTime = System.currentTimeMillis();
        mIsRunning = true;
    }

    public void stop(){

        mIsRunning = false;
    }

    @Override
    public void run() {
        while (mIsRunning){
            long since = System.currentTimeMillis() - mStartTime;

            int seconds = (int) ((since / 1000) % 60);
            int minutes = (int) ((since / MILLIS_TO_MINUTES) % 60);
            int hours = (int) ((since / MILLIS_TO_HOURS) % 24);
            int millis = (int) since % 1000;

            ((MainActivity)tvContext).updateTimerText(String.format(String.format("%02d:%02d:%02d:%03d"
                    , hours, minutes, seconds, millis)));


        }

    }
}
