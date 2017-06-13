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
    private int _day;
    private int _week;

    @Override
    protected void onResume() {
        super.onResume();
        loadWorkouts();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        _week = b.getInt("week");
        _day = b.getInt("day");

        setContentView(R.layout.activity_workout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        _settings = new Settings(this);

        loadWorkouts();
    }

    public void completeWorkoutClick(View v) {
        if (_day == 3) {
            int week = _settings.getWeek();
            _settings.setDay(1);
            _settings.setWeek(_week + 1);
        } else {
            _settings.setDay(_day + 1);
        }
        this.finish();
    }

    public void loadWorkouts() {
        TextView dayText = (TextView)findViewById(R.id.day);
        TextView weekText = (TextView)findViewById(R.id.week);
        weekText.setText("Week: " + _week);

        LiftCalculator calc = new LiftCalculator(_settings);

        if (_day == Workout.MONDAY) {
            setMondayWorkouts(_week, calc);
            dayText.setText("Day: Monday");
        } else if (_day == Workout.WEDNESDAY) {
            setWednesdayWorkouts(_week, calc);
            dayText.setText("Day: Wednesday");
        } else {
            setFridayWorkouts(_week, calc);
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
