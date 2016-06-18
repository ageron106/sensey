package com.github.nisrulz.sensey.accelerometer_gestures.gesture_strategies.gestures;

import android.hardware.SensorEvent;

import com.github.nisrulz.sensey.accelerometer_gestures.gesture_strategies.GestureStrategy;

/**
 * Created by dmitry on 18.06.16.
 */

public class TurnroundGestureStrategy extends GestureStrategy {


    @Override
    protected boolean processEventCore(SensorEvent event) {

        if(!resultIsValid(event)){
            return false;
        }

        if (isSatisfied(
                ProcessState.VERTICAL,
                ProcessState.TURN_LEFT,
                ProcessState.TURN_RIGHT,
                ProcessState.VERTICAL)) {
            clear();
            return true;
        }
        if (isSatisfied(
                ProcessState.VERTICAL,
                ProcessState.TURN_RIGHT,
                ProcessState.TURN_LEFT,
                ProcessState.VERTICAL)) {
            clear();
            return true;
        }

        if (isSatisfied(
                ProcessState.VERTICAL,
                ProcessState.TURN_RIGHT,
                ProcessState.TURN_LEFT,
                ProcessState.TURN_RIGHT)) {
            clear();
            return true;
        }

        if (isSatisfied(
                ProcessState.VERTICAL,
                ProcessState.TURN_LEFT,
                ProcessState.TURN_RIGHT,
                ProcessState.TURN_LEFT)) {
            clear();
            return true;
        }

        if (isSatisfied(
                ProcessState.VERTICAL,
                ProcessState.TURN_LEFT,
                ProcessState.VERTICAL)) {
            clear();
            return true;
        }
        if (isSatisfied(
                ProcessState.VERTICAL,
                ProcessState.TURN_RIGHT,
                ProcessState.VERTICAL)) {
            clear();
            return true;
        }

        return false;
    }


}