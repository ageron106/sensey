package com.github.nisrulz.sensey.accelerometer_gestures.gesture_strategies.gestures;

import android.hardware.SensorEvent;

import com.github.nisrulz.sensey.accelerometer_gestures.gesture_strategies.GestureStrategy;

/**
 * Created by dmitry on 18.06.16.
 */
public class TurnLeftGestureStrategy extends GestureStrategy {


    @Override
    protected boolean processEventCore(SensorEvent event) {

        if(!resultIsValid(event)){
            return false;
        }

        if (isSatisfied(
                ProcessState.VERTICAL,
                ProcessState.TURN_LEFT)) {
            clear();
            return true;
        }

        return false;
    }


}