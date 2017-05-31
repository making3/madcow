package com.example.mattk.madcow.helpers;

import android.content.SharedPreferences;

import com.example.mattk.madcow.data.Lift;

public class LiftCalculator {
    private SharedPreferences _preferences;

    public LiftCalculator(SharedPreferences preferences) {
        _preferences = preferences;
    }

    public int getMaxWeight(int week, int day, Lift lift, double plate) {
        if (day != 2 && (lift == Lift.DEADLIFT || lift == Lift.PRESS)) {
            throw new IllegalArgumentException("deadlift and press must be on day 2.");
        }

        if (day == 2 && lift == Lift.SQUAT) {
            int maxWeight = getMaxWeight(week, 1, lift, plate);
            return getWarmupWeight(maxWeight, 2, plate);
        }

        double startingLift = 0;
        switch (lift) {
            case BENCH:
                startingLift = getStartingBench();
                break;
            case SQUAT:
                startingLift = getStartingSquat();
                break;
            case ROW:
                startingLift = getStartingRow();
                break;
            case PRESS:
                startingLift = getStartingPress();
                break;
            case DEADLIFT:
                startingLift = getStartingDeadlift();
                break;
        }

        if (week == 1 && (day == 1 || day == 2)) {
            return (int) startingLift;
        } else {
            if (day != 3) {
                week = week - 1;
            }
            double weekPower = Math.pow(1.025, week);
            int a = Math.round((float)(startingLift * weekPower / (2.0 * plate)));
            return (int)(a * 2.0 * plate);
        }
    }

    public int getWarmupWeight(int maxWeight, int warmupOffset, double plate) {
        // =         ROUND(        E14*(1-SQINT)/(2*PLATE),0)*2*PLATE
        int a = Math.round(maxWeight * (1- getSetInterval() * warmupOffset) / (float)(2.0 * plate));
        return (int)(a * 2.0 * plate);
    }

    private float getSetInterval() {
        return _preferences.getFloat("set_interval", 0.125f);
    }

    private int getStartingSquat() {
        return _preferences.getInt("squat", 230);
    }

    private int getStartingBench() {
        return _preferences.getInt("bench", 140);
    }

    private int getStartingRow() {
        return _preferences.getInt("row", 145);
    }

    private int getStartingPress() {
        return _preferences.getInt("press", 95);
    }

    private int getStartingDeadlift() {
        return _preferences.getInt("deadlift", 250);
    }
}
