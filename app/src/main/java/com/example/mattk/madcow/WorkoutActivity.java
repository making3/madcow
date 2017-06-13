package com.example.mattk.madcow;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mattk.madcow.data.Lift;
import com.example.mattk.madcow.data.Workout;
import com.example.mattk.madcow.helpers.LiftCalculator;
import com.example.mattk.madcow.helpers.Settings;

public class WorkoutActivity extends BaseActivity {
    private Settings _settings;

    @Override
    protected void onResume() {
        super.onResume();
        loadWorkouts();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        _settings = new Settings(this);

        loadWorkouts();
    }

    public void nextDayOnClick(View v) {
        int day = _settings.getDay();
        if (day == 3) {
            int week = _settings.getWeek();
            _settings.setDay(1);
            _settings.setWeek(week + 1);
        } else {
            _settings.setDay(day + 1);
        }
        loadWorkouts();
    }

    public void prevDayOnClick(View v) {
        int day = _settings.getDay();
        if (day == 1) {
            int week = _settings.getWeek();
            if (week != 1) { // temporarily prevent from going to week 0 day 3 (does not exist)
                _settings.setWeek(week - 1);
                _settings.setDay(3);
            }
        } else {
            _settings.setDay(day - 1);
        }
        loadWorkouts();
    }

    public void loadWorkouts() {
        int week = _settings.getWeek();
        int day = _settings.getDay();

        TextView dayText = (TextView)findViewById(R.id.day);
        TextView weekText = (TextView)findViewById(R.id.week);
        weekText.setText("Week: " + week);

        LiftCalculator calc = new LiftCalculator(_settings);

        if (day == Workout.MONDAY) {
            setMondayWorkouts(week, calc);
            dayText.setText("Day: Monday");
        } else if (day == Workout.WEDNESDAY) {
            setWednesdayWorkouts(week, calc);
            dayText.setText("Day: Wednesday");
        } else {
            setFridayWorkouts(week, calc);
            dayText.setText("Day: Friday");
        }
    }

    private void setMondayWorkouts(int week, LiftCalculator calc) {
        addMondayLift(Workout.FIRST, week, Lift.SQUAT, calc);
        addMondayLift(Workout.SECOND, week, Lift.BENCH, calc);
        addMondayLift(Workout.THIRD, week, Lift.ROW, calc);
    }

    private void addMondayLift(int workoutNumber, int week, Lift lift, LiftCalculator calc) {
        int maxLift = calc.getMaxWeight(week, 1, lift);
        Workout workout = new Workout(lift, maxLift, calc);
        workout.addWarmup(4);
        workout.addWarmup(3);
        workout.addWarmup(2);
        workout.addWarmup(1);
        workout.addMaxLift();

        setLifts(workoutNumber, workout);
    }

    private void setWednesdayWorkouts(int week, LiftCalculator calc) {
        addWednesdayLift(Workout.FIRST, week, Lift.SQUAT, calc);
        addWednesdayLift(Workout.SECOND, week, Lift.PRESS, calc);
        addWednesdayLift(Workout.THIRD, week, Lift.DEADLIFT, calc);
    }

    private void addWednesdayLift(int workoutNumber, int week, Lift lift, LiftCalculator calc) {
        int maxLift = calc.getMaxWeight(week, 2, lift);
        Workout workout = new Workout(lift, maxLift, calc);

        if (workoutNumber == Workout.FIRST) {
            int warmupToWeight = calc.getMaxWeight(week, 1, lift);
            workout.addWarmup(4, warmupToWeight);
            workout.addWarmup(3, warmupToWeight);
            workout.addMaxLift();
        } else {
            workout.addWarmup(3);
            workout.addWarmup(2);
            workout.addWarmup(1);
        }
        workout.addMaxLift();

        setLifts(workoutNumber, workout);
    }

    private void setFridayWorkouts(int week, LiftCalculator calc) {
        addFridayLift(Workout.FIRST, week, Lift.SQUAT, calc);
        addFridayLift(Workout.SECOND, week, Lift.BENCH, calc);
        addFridayLift(Workout.THIRD, week, Lift.ROW, calc);
    }

    private void addFridayLift(int workoutNumber, int week, Lift lift, LiftCalculator calc) {
        int maxLift = calc.getMaxWeight(week, 3, lift);

        Workout workout = new Workout(lift, maxLift, calc);
        workout.addWarmup(4);
        workout.addWarmup(3);
        workout.addWarmup(2);
        workout.addWarmup(1);
        workout.addMaxLift();

        int warmupToWeight = calc.getMaxWeight(week, 1, lift);
        workout.addWarmup(2, warmupToWeight);

        setLifts(workoutNumber, workout);
    }

    private void setLifts(int workoutNumber, Workout workout) {
        WorkoutRow row = getWorkoutRow(workoutNumber);
        row.SetLifts(this, workout);
    }

    private WorkoutRow getWorkoutRow(int workoutNumber) {
        if (workoutNumber == Workout.FIRST) {
            return (WorkoutRow) findViewById(R.id.firstWorkout);
        }
        if (workoutNumber == Workout.SECOND) {
            return (WorkoutRow) findViewById(R.id.secondWorkout);
        }
        return (WorkoutRow) findViewById(R.id.thirdWorkout);
    }
}
