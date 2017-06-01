package com.example.mattk.madcow;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mattk.madcow.data.Lift;
import com.example.mattk.madcow.helpers.LiftCalculator;

import java.util.prefs.Preferences;

public class WorkoutActivity extends AppCompatActivity {
    private final double tempPlate = 2.5;
    private static final String WEEK = "week";
    private static final String DAY = "day";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Context context = getApplicationContext();

        int week = getWeek();
        int day = getDay();

        LiftCalculator calc = new LiftCalculator(getPreferences(0));

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
        int day = getDay();
        if (day == 3) {
            int week = getWeek();
            setDay(1);
            setWeek(week + 1);
        } else {
            setDay(day + 1);
        }
        reload();
    }

    public void prevDayOnClick(View v) {
        int day = getDay();
        if (day == 1) {
            int week = getWeek();
            if (week != 1) { // temporarily prevent from going to week 0 day 3 (does not exist)
                setWeek(week - 1);
                setDay(3);
            }
        } else {
            setDay(day - 1);
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
        int maxLift = calc.getMaxWeight(week, 1, lift, tempPlate);

        int[] lifts = new int[5];
        lifts[0] = calc.getWarmupWeight(maxLift, 4, tempPlate);
        lifts[1] = calc.getWarmupWeight(maxLift, 3, tempPlate);
        lifts[2] = calc.getWarmupWeight(maxLift, 2, tempPlate);
        lifts[3] = calc.getWarmupWeight(maxLift, 1, tempPlate);
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
        int maxLift = calc.getMaxWeight(week, 2, lift, tempPlate);
        int[] lifts = new int[4];
        lifts[0] = calc.getWarmupWeight(maxLift, 3, tempPlate);
        lifts[1] = calc.getWarmupWeight(maxLift, 2, tempPlate);
        lifts[2] = calc.getWarmupWeight(maxLift, 1, tempPlate);
        lifts[3] = maxLift;

        WorkoutRow row = getWorkout(workoutNumber);
        row.SetLiftName(lift);
        row.SetLifts(context, lifts);
    }

    private void addWednesdaySquat(Context context, int week, LiftCalculator calc) {
        int maxSquat = calc.getMaxWeight(week, 2, Lift.SQUAT, tempPlate);

        int[] lifts = new int[4];
        lifts[0] = calc.getWarmupWeight(maxSquat, 4, tempPlate);
        lifts[1] = calc.getWarmupWeight(maxSquat, 3, tempPlate);
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
        int maxLift = calc.getMaxWeight(week, 3, lift, tempPlate);
        int[] lifts = new int[6];
        lifts[0] = calc.getWarmupWeight(maxLift, 4, tempPlate);
        lifts[1] = calc.getWarmupWeight(maxLift, 3, tempPlate);
        lifts[2] = calc.getWarmupWeight(maxLift, 2, tempPlate);
        lifts[3] = calc.getWarmupWeight(maxLift, 1, tempPlate);
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

    private void setWeek(int week){
        SharedPreferences prefs = getPreferences(0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(WEEK, week);
        editor.commit();
    }

    private int getWeek() {
        SharedPreferences prefs = getPreferences(0);
        return prefs.getInt(WEEK, 1);
    }

    private void setDay(int day){
        SharedPreferences prefs = getPreferences(0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(DAY, day);
        editor.commit();
    }

    private int getDay() {
        SharedPreferences prefs = getPreferences(0);
        return prefs.getInt(DAY, 1);
    }
}
