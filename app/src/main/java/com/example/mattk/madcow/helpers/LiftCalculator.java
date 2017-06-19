package com.example.mattk.madcow.helpers;

import com.example.mattk.madcow.data.Lift;

public class LiftCalculator {
    private Settings _settings;

    public LiftCalculator(Settings settings) {
        _settings = settings;
    }

    public int getMaxWeight(int week, int day, Lift lift) {
        if (day != 2 && (lift == Lift.DEADLIFT || lift == Lift.PRESS)) {
            throw new IllegalArgumentException("deadlift and press must be on day 2.");
        }

        float plate = _settings.getSmallestPlate();
        if (day == 2 && lift == Lift.SQUAT) {
            int maxWeight = getMaxWeight(week, 1, lift);
            return getWarmupWeight(maxWeight, 2);
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

    public int getWarmupWeight(int maxWeight, int warmupOffset) {
        float plate = _settings.getSmallestPlate();

        // =         ROUND(        E14*(1-SQINT)/(2*PLATE),0)*2*PLATE
        int a = Math.round(maxWeight * (1- _settings.getSetInterval() * warmupOffset) / (float)(2.0 * plate));
        return (int)(a * 2.0 * plate);
    }

    public int getOneRepMax(int weight, int reps) {
        // 5RM =G9*(1.0278-(0.0278*5))
        // 1RM =(C9)/(1.0278-(0.0278*D9))
        //      Weight / (1.0278-(0.0278*reps))
        return Math.round(weight / (float) (1.0278 - (0.0278 * reps)));
    }

    public int getFiveRepMax(int oneRepMax) {
        return Math.round(oneRepMax * (float)(1.0278 - (0.0278 * 5)));
    }

    public int getStartingWeight(int fiveRepMax) {
        // Starting Weight =ROUND(H9*((1/1.025)^(PRWEEK-1))/(2*PLATE),0)*2*PLATE
        //                        ROUND(fiveRepMax * ((1 / 1.025) ^ (PRWEEK - 1)) / (2 * PLATE), 0) * 2 * PLATE
        //
        // TODO: Create a setting for the PRWEEK value
        int matchPrWeek = 3;
        float weekPower = (float)Math.pow((1 / 1.025), matchPrWeek);
        float smallestPlate = _settings.getSmallestPlate();
        float tempStarting = Math.round(fiveRepMax * weekPower / (2 * smallestPlate));
        float startingWeight = tempStarting * 2 * smallestPlate;
        return (int)startingWeight;
    }
}
