package com.github.nisrulz.sensey.gestures;

import android.hardware.SensorEvent;

/**
 * Created by dmitry on 18.06.16.
 */
public class TurnRightGestureStrategy extends GestureStrategy {


    @Override
    protected boolean processEventCore(SensorEvent event) {

        if(!resultIsValid(event)){
            return false;
        }

        if (isSatisfied(
                GestureStrategy.ProcessState.VERTICAL,
                GestureStrategy.ProcessState.TURN_RIGHT)) {
            clear();
            return true;
        }

        return false;
    }

}
