package com.example.mattk.madcow.data;

import com.example.mattk.madcow.helpers.LiftCalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Workout {
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
        int warmup = _calc.getWarmupWeight(warmupToWeight, offset);
        addSet(warmup);
    }

    public void addMaxLift() {
        addSet(_maxLift);
    }

    public void addSet(int weight) {
        addSet(weight, 5);
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
}
