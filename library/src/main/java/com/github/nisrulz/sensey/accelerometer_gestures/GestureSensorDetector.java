package com.github.nisrulz.sensey.accelerometer_gestures;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.PowerManager;


import com.github.nisrulz.sensey.accelerometer_gestures.gesture_strategies.GestureStrategy;
import com.github.nisrulz.sensey.accelerometer_gestures.gesture_strategies.utils.PeriodicalRunner;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Created by dmitry on 17.01.16.
 */
public class GestureSensorDetector{
    //    private List<GestureStrategy> strategies = new ArrayList<>();
    private AtomicReference<ArrayList<GestureStrategy>> strategiesA = new AtomicReference<>(new ArrayList<GestureStrategy>());
    private SensorManager sensorManager;
    private int samplingPeriodsInMs = 100;

    public SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            ArrayList<GestureStrategy> gestureStrategies = new ArrayList<>(strategiesA.get());
            for (GestureStrategy strategy : gestureStrategies) {
                strategy.processEvent(event);
            }
        }
        @Override public void onAccuracyChanged(Sensor sensor, int i) {
            // do nothing
        }
    };

    public GestureSensorDetector(GestureStrategy ... strategies) {

    }

    public void register(GestureStrategy strategy) {
        ArrayList<GestureStrategy> gestureStrategies = new ArrayList<>(strategiesA.get());
        gestureStrategies.add(strategy);
        strategiesA.set(gestureStrategies);
    }

    public void unregister(GestureStrategy strategy) {
        ArrayList<GestureStrategy> gestureStrategies = new ArrayList<>(strategiesA.get());
        gestureStrategies.remove(strategy);
        strategiesA.set(gestureStrategies);
    }



}