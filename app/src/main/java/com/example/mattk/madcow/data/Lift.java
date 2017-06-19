package com.example.mattk.madcow.data;

import java.util.LinkedList;
import java.util.Queue;

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

    public static Queue<Lift> getLiftsQueue() {
        Queue<Lift> lifts = new LinkedList<>();
        lifts.add(Lift.SQUAT);
        lifts.add(Lift.BENCH);
        lifts.add(Lift.ROW);
        lifts.add(Lift.PRESS);
        lifts.add(Lift.DEADLIFT);

        return lifts;
    }
}
