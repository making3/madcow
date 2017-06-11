package com.example.mattk.madcow.helpers;

import com.example.mattk.madcow.data.Lift;

public class LiftCalculator {
    private Settings _settings;

    public LiftCalculator(Settings settings) {
        _settings = settings;
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
                startingLift = _settings.getStartingBench();
                break;
            case SQUAT:
                startingLift = _settings.getStartingSquat();
                break;
            case ROW:
                startingLift = _settings.getStartingRow();
                break;
            case PRESS:
                startingLift = _settings.getStartingPress();
                break;
            case DEADLIFT:
                startingLift = _settings.getStartingDeadlift();
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
        int a = Math.round(maxWeight * (1- _settings.getSetInterval() * warmupOffset) / (float)(2.0 * plate));
        return (int)(a * 2.0 * plate);
    }
}
