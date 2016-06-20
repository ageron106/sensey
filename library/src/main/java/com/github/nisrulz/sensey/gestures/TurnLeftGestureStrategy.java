package com.github.nisrulz.sensey.gestures;

import android.hardware.SensorEvent;

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