package com.example.mattk.madcow.data;

public enum Lift {
    SQUAT("Squat"),
    BENCH("Bench"),
    PRESS("Press"),
    ROW("Row"),
    DEADLIFT("Deadlift");

    private final String _liftDescription;

    Lift(String value) {
        _liftDescription = value;
    }

    @Override
    public String toString() {
        return _liftDescription;
    }
}
