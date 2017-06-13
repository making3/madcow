package com.example.mattk.madcow.data;

import com.example.mattk.madcow.helpers.LiftCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Workout {
    public static final int MONDAY = 1;
    public static final int WEDNESDAY = 2;
    public static final int FIRST = 1;
    public static final int SECOND = 2;
    public static final int THIRD = 3;

    private int _maxLift;
    private Lift _lift;
    private List<WorkoutSet> _sets;
    private LiftCalculator _calc;

    public Workout(Lift lift, int maxLift, LiftCalculator calc) {
        _lift = lift;
        _maxLift = maxLift;
        _calc = calc;
        _sets = new ArrayList<>();
    }

    public void addWarmup(int offset) {
        addWarmup(offset, _maxLift);
    }

    public void addWarmup(int offset, int warmupToWeight) {
        addWarmup(offset, warmupToWeight, 5);
    }

    public void addWarmup(int offset, int warmupToWeight, int reps) {
        int warmup = _calc.getWarmupWeight(warmupToWeight, offset);
        addSet(warmup, reps);
    }

    public void addMaxLift() {
        addMaxLift(5);
    }
    public void addMaxLift(int reps) {
        addSet(_maxLift, reps);
    }

    public void addSet(int weight, int reps) {
        _sets.add(new WorkoutSet(weight, reps, weight == _maxLift));
    }

    public List<WorkoutSet> getSets() {
        return Collections.unmodifiableList(_sets);
    }

    public String getLiftName() {
        return _lift.toString();
    }

    public static String getDayString(int day){
        switch (day){
            case MONDAY:
                return "Monday";
            case WEDNESDAY:
                return "Wednesday";
            default:
                return "Friday";
        }
    }
}
