package com.github.nisrulz.sensey.accelerometer_gestures.gesture_strategies;

import android.hardware.SensorEvent;


import com.github.nisrulz.sensey.accelerometer_gestures.gesture_strategies.utils.CircularBuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmitry on 17.01.16.
 */
public class GestureStrategy {
    private GestureHandlerSubscriber subscriber = new GestureHandlerSubscriber();

    public CircularBuffer<ProcessEventResult> buffer = new CircularBuffer<>(4);
    public ProcessEventResult result;
    public ProcessEventResult last;

    public void subscribe(GestureHandler handler) {
        subscriber.subscribe(handler);
    }

    public void processEvent(SensorEvent event) {
        if (processEventCore(event)) {
            subscriber.onOccurred();
        }
    }

    protected boolean processEventCore(SensorEvent event) {
        return false;
    }

    public interface GestureHandler {
        void onOccurred();
    }

    private class GestureHandlerSubscriber implements GestureHandler {
        private List<GestureHandler> handlers = new ArrayList<>();


        @Override
        public void onOccurred() {
            for (GestureHandler handler : handlers) {
                handler.onOccurred();
            }
        }

        public void subscribe(GestureHandler handler) {
            handlers.add(handler);
        }
    }

    public void clear() {
        buffer.clear();
    }

    public boolean isSatisfied(ProcessState... states) {

        if (buffer.getSize() < states.length) {
            return false;
        }

        int counter = buffer.getSize() - states.length;

        for (ProcessState state : states) {
            ProcessEventResult buffElem = buffer.get(counter);
            if (counter > buffer.getSize() - states.length) {
                ProcessEventResult prevBuffElem = buffer.get(counter - 1);
                ProcessEventResult firstBuffElem = buffer.get(0);
                if ((buffElem.timestamp - prevBuffElem.timestamp) < 100) {
                    return false;
                }
                if ((buffElem.timestamp - firstBuffElem.timestamp) < 300) {
                    return false;
                }
            }
            if (state != buffElem.state) {
                return false;
            }
            counter++;
        }

        return true;
    }

    private ProcessEventResult getResult(SensorEvent event) {

        long timestamp = System.currentTimeMillis();
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        if ((z > 12) && (y < -1)) {
            return new ProcessEventResult(ProcessState.TURNOVER, timestamp);
        }
        if (z > 8 && z < 11 && Math.abs(y) < 5 && Math.abs(x) < 5) {
            return new ProcessEventResult(ProcessState.HORIZONTAL, timestamp);
        }

        if (y > 8 && Math.abs(z) < 5 && Math.abs(x) < 5) {
            return new ProcessEventResult(ProcessState.VERTICAL, timestamp);
        }
        if (x > 8) {
            return new ProcessEventResult(ProcessState.TURN_RIGHT, timestamp);
        }
        if (x < -8) {
            return new ProcessEventResult(ProcessState.TURN_LEFT, timestamp);
        }
        return new ProcessEventResult(ProcessState.UNKNOWN, timestamp);
    }

    public enum ProcessState {
        TURNOVER,
        HORIZONTAL,
        VERTICAL,
        TURN_RIGHT,
        TURN_LEFT,
        UNKNOWN,
    }

    private class ProcessEventResult {
        public final long timestamp;
        public final ProcessState state;

        public ProcessEventResult(ProcessState state, long timestamp) {
            this.state = state;
            this.timestamp = timestamp;
        }
    }

    public boolean resultIsValid(SensorEvent event) {

        result = getResult(event);
        if (last == null) {
            last = result;
            return false;
        }
        if (last.equals(result)) {
            return false;
        }
        if (result.state == ProcessState.UNKNOWN) {
            last = result;
            return false;
        }
        if (last.state != result.state) {
            last = result;
            buffer.add(result);
        }
        if (buffer.isEmpty()) {
            return false;
        }
        return true;
    }

}