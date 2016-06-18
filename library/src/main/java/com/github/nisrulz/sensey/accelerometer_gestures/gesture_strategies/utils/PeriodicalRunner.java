package com.github.nisrulz.sensey.accelerometer_gestures.gesture_strategies.utils;

/**
 * Created by dmitry on 18.06.16.
 */
public class PeriodicalRunner {
    Thread intervalThread;


    public void start(final PeriodListener listener, final int interval) {
        intervalThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(interval);
                        listener.onIntervalFinished();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        intervalThread.run();
        intervalThread.start();
    }

    public interface PeriodListener {
        void onIntervalFinished();
    }
}
