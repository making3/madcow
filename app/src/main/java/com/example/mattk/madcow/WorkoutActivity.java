package com.example.mattk.madcow;

import android.app.Activity;
import android.content.Context;
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
    private Settings _settings;

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
            case R.id.reload: // Temporary reload until it's not required.
                reload();
                return true;
            case R.id.reset:
                _settings.setWeek(1);
                _settings.setDay(1);
                reload();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Context context = getApplicationContext();

        _settings = new Settings(this);

        int week = _settings.getWeek();
        int day = _settings.getDay();

        LiftCalculator calc = new LiftCalculator(_settings);

        if (day == 1) {
            setMondayWorkouts(context, week, calc);
        } else if (day == 2) {
            setWednesdayWorkouts(context, week, calc);
        } else {
            setFridayWorkouts(context, week, calc);
        }
        TextView dayText = (TextView)findViewById(R.id.day);
        TextView weekText = (TextView)findViewById(R.id.week);
        weekText.setText("Week: " + week);
        dayText.setText("Day: " + day);
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
        reload();
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
        reload();
    }

    public void reload() {
        finish();
        startActivity(getIntent());
    }

    private void setMondayWorkouts(Context context, int week, LiftCalculator calc) {
        addMondayLift(1, context, week, Lift.SQUAT, calc);
        addMondayLift(2, context, week, Lift.BENCH, calc);
        addMondayLift(3, context, week, Lift.ROW, calc);
    }

    private void addMondayLift(int workoutNumber, Context context, int week, Lift lift, LiftCalculator calc) {
        int maxLift = calc.getMaxWeight(week, 1, lift);

        int[] lifts = new int[5];
        lifts[0] = calc.getWarmupWeight(maxLift, 4);
        lifts[1] = calc.getWarmupWeight(maxLift, 3);
        lifts[2] = calc.getWarmupWeight(maxLift, 2);
        lifts[3] = calc.getWarmupWeight(maxLift, 1);
        lifts[4] = maxLift;

        WorkoutRow row = getWorkout(workoutNumber);
        row.SetLiftName(lift);
        row.SetLifts(context, lifts);
    }

    private void setWednesdayWorkouts(Context context, int week, LiftCalculator calc) {
        addWednesdaySquat(context, week, calc);
        addWednesdayLift(2, context, week, Lift.PRESS, calc);
        addWednesdayLift(3, context, week, Lift.DEADLIFT, calc);
    }

    private void addWednesdayLift(int workoutNumber, Context context, int week, Lift lift, LiftCalculator calc) {
        int maxLift = calc.getMaxWeight(week, 2, lift);
        int[] lifts = new int[4];
        lifts[0] = calc.getWarmupWeight(maxLift, 3);
        lifts[1] = calc.getWarmupWeight(maxLift, 2);
        lifts[2] = calc.getWarmupWeight(maxLift, 1);
        lifts[3] = maxLift;

        WorkoutRow row = getWorkout(workoutNumber);
        row.SetLiftName(lift);
        row.SetLifts(context, lifts);
    }

    private void addWednesdaySquat(Context context, int week, LiftCalculator calc) {
        int maxSquat = calc.getMaxWeight(week, 2, Lift.SQUAT);

        int[] lifts = new int[4];
        lifts[0] = calc.getWarmupWeight(maxSquat, 4);
        lifts[1] = calc.getWarmupWeight(maxSquat, 3);
        lifts[2] = maxSquat;
        lifts[3] = maxSquat;

        getWorkout(1).SetLifts(context, lifts);
    }

    private void setFridayWorkouts(Context context, int week, LiftCalculator calc) {
        addFridayLift(1, context, week, Lift.SQUAT, calc);
        addFridayLift(2, context, week, Lift.BENCH, calc);
        addFridayLift(3, context, week, Lift.ROW, calc);
    }

    private void addFridayLift(int workoutNumber, Context context, int week, Lift lift, LiftCalculator calc) {
        int maxLift = calc.getMaxWeight(week, 3, lift);
        int[] lifts = new int[6];
        lifts[0] = calc.getWarmupWeight(maxLift, 4);
        lifts[1] = calc.getWarmupWeight(maxLift, 3);
        lifts[2] = calc.getWarmupWeight(maxLift, 2);
        lifts[3] = calc.getWarmupWeight(maxLift, 1);
        lifts[4] = maxLift;
        lifts[5] = lifts[2];

        WorkoutRow row = getWorkout(workoutNumber);
        row.SetLiftName(lift);
        row.SetLifts(context, lifts);
    }

    private WorkoutRow getWorkout(int workoutNumber) {
        if (workoutNumber == 1) {
            return (WorkoutRow) findViewById(R.id.firstWorkout);
        }
        if (workoutNumber == 2) {
            return (WorkoutRow) findViewById(R.id.secondWorkout);
        }
        return (WorkoutRow) findViewById(R.id.thirdWorkout);
    }
}
