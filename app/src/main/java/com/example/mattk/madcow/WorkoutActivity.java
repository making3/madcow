package com.example.mattk.madcow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.mattk.madcow.data.Lift;
import com.example.mattk.madcow.helpers.LiftCalculator;
import com.example.mattk.madcow.helpers.Settings;

public class WorkoutActivity extends Activity {
    final int WORKOUT_MONDAY = 1;
    final int WORKOUT_WEDNESDAY = 2;
    final int FIRST_WORKOUT = 1;
    final int SECOND_WORKOUT = 2;
    final int THIRD_WORKOUT = 3;
    private Settings _settings;

    @Override
    protected void onResume() {
        super.onResume();
        loadWorkouts();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent settingsIntent = new Intent(WorkoutActivity.this, SettingsActivity.class);
                WorkoutActivity.this.startActivity(settingsIntent);
                return true;
            case R.id.reset:
                _settings.setWeek(1);
                _settings.setDay(1);
                loadWorkouts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
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

        LiftCalculator calc = new LiftCalculator(_settings);

        if (day == WORKOUT_MONDAY) {
            setMondayWorkouts(week, calc);
        } else if (day == WORKOUT_WEDNESDAY) {
            setWednesdayWorkouts(week, calc);
        } else {
            setFridayWorkouts(week, calc);
        }
        TextView dayText = (TextView)findViewById(R.id.day);
        TextView weekText = (TextView)findViewById(R.id.week);
        weekText.setText("Week: " + week);
        dayText.setText("Day: " + day);
    }

    private void setMondayWorkouts(int week, LiftCalculator calc) {
        addMondayLift(FIRST_WORKOUT, week, Lift.SQUAT, calc);
        addMondayLift(SECOND_WORKOUT, week, Lift.BENCH, calc);
        addMondayLift(THIRD_WORKOUT, week, Lift.ROW, calc);
    }

    private void addMondayLift(int workoutNumber, int week, Lift lift, LiftCalculator calc) {
        int maxLift = calc.getMaxWeight(week, 1, lift);
        int[] lifts = {
            calc.getWarmupWeight(maxLift, 4),
            calc.getWarmupWeight(maxLift, 3),
            calc.getWarmupWeight(maxLift, 2),
            calc.getWarmupWeight(maxLift, 1),
            maxLift
        };
        setLifts(workoutNumber, lift, lifts);
    }

    private void setWednesdayWorkouts(int week, LiftCalculator calc) {
        addWednesdayLift(FIRST_WORKOUT, week, Lift.SQUAT, calc);
        addWednesdayLift(SECOND_WORKOUT, week, Lift.PRESS, calc);
        addWednesdayLift(THIRD_WORKOUT, week, Lift.DEADLIFT, calc);
    }

    private void addWednesdayLift(int workoutNumber, int week, Lift lift, LiftCalculator calc) {
        int maxLift = calc.getMaxWeight(week, 2, lift);

        int[] lifts = {
            calc.getWarmupWeight(maxLift, 3),
            calc.getWarmupWeight(maxLift, 2),
            calc.getWarmupWeight(maxLift, 1),
            maxLift
        };

        if (workoutNumber == FIRST_WORKOUT) {
            int maxSquat = calc.getMaxWeight(week, 1, lift);
            lifts[0] = calc.getWarmupWeight(maxSquat, 4);
            lifts[1] = calc.getWarmupWeight(maxSquat, 3);
            lifts[2] = maxLift;
        }

        setLifts(workoutNumber, lift, lifts);
    }

    private void setFridayWorkouts(int week, LiftCalculator calc) {
        addFridayLift(FIRST_WORKOUT, week, Lift.SQUAT, calc);
        addFridayLift(SECOND_WORKOUT, week, Lift.BENCH, calc);
        addFridayLift(THIRD_WORKOUT, week, Lift.ROW, calc);
    }

    private void addFridayLift(int workoutNumber, int week, Lift lift, LiftCalculator calc) {
        int maxLift = calc.getMaxWeight(week, 3, lift);
        int thirdWarmup = calc.getWarmupWeight(maxLift, 2);

        int[] lifts = {
            calc.getWarmupWeight(maxLift, 4),
            calc.getWarmupWeight(maxLift, 3),
            thirdWarmup,
            calc.getWarmupWeight(maxLift, 1),
            maxLift,
            thirdWarmup
        };
        setLifts(workoutNumber, lift, lifts);
    }

    private void setLifts(int workoutNumber, Lift lift, int[] lifts) {
        WorkoutRow row = getWorkoutRow(workoutNumber);
        row.SetLiftName(lift);
        row.SetLifts(this, lifts);
    }

    private WorkoutRow getWorkoutRow(int workoutNumber) {
        if (workoutNumber == FIRST_WORKOUT) {
            return (WorkoutRow) findViewById(R.id.firstWorkout);
        }
        if (workoutNumber == SECOND_WORKOUT) {
            return (WorkoutRow) findViewById(R.id.secondWorkout);
        }
        return (WorkoutRow) findViewById(R.id.thirdWorkout);
    }
}
