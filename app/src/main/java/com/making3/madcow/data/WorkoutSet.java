package com.making3.madcow.data;

public class WorkoutSet {
    private int _weight;
    private int _reps;
    private boolean _isMaxLift;

    public WorkoutSet(int weight) {
        _weight = weight;
        _reps = 5;
        _isMaxLift = false;
    }

    public WorkoutSet(int weight, int reps) {
        _weight = weight;
        _reps = reps;
        _isMaxLift = false;
    }

    public WorkoutSet(int weight, int reps, boolean isMaxLift) {
        _weight = weight;
        _reps = reps;
        _isMaxLift = isMaxLift;
    }

    public int getWeight() {
        return _weight;
    }

    public int getReps() {
        return _reps;
    }

    public boolean isMaxLift() {
        return _isMaxLift;
    }
}
