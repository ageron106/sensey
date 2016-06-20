package com.github.nisrulz.sensey;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


import com.github.nisrulz.sensey.gestures.GestureStrategy;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Created by dmitry on 17.01.16.
 */
public class GestureSensorDetector{
    private AtomicReference<ArrayList<GestureStrategy>> strategies = new AtomicReference<>(new ArrayList<GestureStrategy>());

    public SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            ArrayList<GestureStrategy> gestureStrategies = new ArrayList<>(strategies.get());
            for (GestureStrategy strategy : gestureStrategies) {
                strategy.processEvent(event);
            }
        }
        @Override public void onAccuracyChanged(Sensor sensor, int i) {
        }
    };

    public GestureSensorDetector(GestureStrategy ... strategies) {

    }

    public void register(GestureStrategy strategy) {
        ArrayList<GestureStrategy> gestureStrategies = new ArrayList<>(strategies.get());
        gestureStrategies.add(strategy);
        strategies.set(gestureStrategies);
    }

    public void unregister(GestureStrategy strategy) {
        ArrayList<GestureStrategy> gestureStrategies = new ArrayList<>(strategies.get());
        gestureStrategies.remove(strategy);
        strategies.set(gestureStrategies);
    }

    public void unregisterAll(){
        strategies.get().clear();
    }

}