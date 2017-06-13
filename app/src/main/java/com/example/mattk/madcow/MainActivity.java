package com.example.mattk.madcow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mattk.madcow.data.Lift;
import com.example.mattk.madcow.data.Workout;
import com.example.mattk.madcow.helpers.LiftCalculator;
import com.example.mattk.madcow.helpers.Settings;

public class MainActivity extends BaseActivity {
    @Override
    protected void onResume() {
        super.onResume();
        loadWorkouts();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadWorkouts();
    }

    private void loadWorkouts() {
        Settings settings = new Settings(this);

        int currentDay = settings.getDay();
        int currentWeek = settings.getWeek();

        int lastDay = getLastDay(currentDay, currentWeek);
        int lastWeek = getLastWeek(currentDay, currentWeek);

        listPreviousWorkouts(lastWeek, lastDay, settings);
        listCurrentWorkouts(currentWeek, currentDay, settings);
    }

    private int getLastDay(int currentDay, int currentWeek) {
        return currentDay - 1 == 0 ? (currentWeek == 1 ? 1 : 3) : currentDay - 1;
    }

    private int getLastWeek(int currentDay, int currentWeek) {
        return currentWeek == 1 ? (currentDay == 1 ? 0 : 1) : (currentDay == 1 ? currentWeek - 1 : currentWeek);
    }

    public void nextWorkout(View v) {
        Settings settings = new Settings(this);

        Intent workoutIntent = new Intent(MainActivity.this, WorkoutActivity.class);
        workoutIntent.putExtra("day", settings.getDay());
        workoutIntent.putExtra("week", settings.getWeek());
        MainActivity.this.startActivity(workoutIntent);
    }

    public void previousWorkout(View v) {
        Settings settings = new Settings(this);
        int currentDay = settings.getDay();
        int currentWeek = settings.getWeek();

        int lastDay = getLastDay(currentDay, currentWeek);
        int lastWeek = getLastWeek(currentDay, currentWeek);
        Intent workoutIntent = new Intent(MainActivity.this, WorkoutActivity.class);
        workoutIntent.putExtra("day", lastDay);
        workoutIntent.putExtra("week", lastWeek);
        MainActivity.this.startActivity(workoutIntent);
    }

    private void listCurrentWorkouts(int week, int day, Settings settings) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.nextWorkouts);

        int count = layout.getChildCount();
        if (count > 1) {
            layout.removeViews(1, count - 1);
        }
        listWorkouts(week, day, layout, settings);
    }

    public void listPreviousWorkouts(int week, int day, Settings settings) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.lastWorkouts);
        int count = layout.getChildCount();
        if (count > 1) {
            layout.removeViews(1, count - 1);
        }
        if (week != 0) {
            listWorkouts(week, day, layout, settings);
        }
    }

    private void listWorkouts(int week, int day, LinearLayout layout, Settings settings) {
        LiftCalculator calc = new LiftCalculator(settings);
        layout.addView(getWorkoutRow("Week " + Integer.toString(week),
                Workout.getDayString(day),
                ContextCompat.getColor(this, R.color.secondary_text),
                getResources().getDimension(R.dimen.lift_preview_week_day_text)));

        int workoutColor = ContextCompat.getColor(this, R.color.primary_text);
        float workoutDimensions = getResources().getDimension(R.dimen.lift_preview_text);
        String squatWeight = Integer.toString(calc.getMaxWeight(week, day, Lift.SQUAT));
        layout.addView(getWorkoutRow(Lift.SQUAT.toString(), squatWeight, workoutColor, workoutDimensions));

        if (day == 1 || day == 3) {
            String benchWeight = Integer.toString(calc.getMaxWeight(week, day, Lift.BENCH));
            layout.addView(getWorkoutRow(Lift.BENCH.toString(), benchWeight, workoutColor, workoutDimensions));

            String rowWeight = Integer.toString(calc.getMaxWeight(week, day, Lift.ROW));
            layout.addView(getWorkoutRow(Lift.ROW.toString(), rowWeight, workoutColor, workoutDimensions));
        } else {
            String pressWeight = Integer.toString(calc.getMaxWeight(week, day, Lift.PRESS));
            layout.addView(getWorkoutRow(Lift.PRESS.toString(), pressWeight, workoutColor, workoutDimensions));

            String deadliftWeight = Integer.toString(calc.getMaxWeight(week, day, Lift.DEADLIFT));
            layout.addView(getWorkoutRow(Lift.DEADLIFT.toString(), deadliftWeight, workoutColor, workoutDimensions));
        }
    }

    private FrameLayout getWorkoutRow(String leftText, String rightText, int color, float dimensions) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        layoutParams.setMargins(0, 10, 0, 0);
        FrameLayout layout = new FrameLayout(this);
        layout.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textViewLeft = new TextView(this);
        textViewLeft.setText(leftText);
        textViewLeft.setLayoutParams(params);
        textViewLeft.setTextColor(color);
        textViewLeft.setTextSize(dimensions);
        layout.addView(textViewLeft);

        TextView textViewRight = new TextView(this);
        textViewRight.setText(rightText);
        textViewRight.setTextColor(color);
        textViewRight.setTextSize(dimensions);
        textViewRight.setLayoutParams(params);

        LinearLayout.LayoutParams liftWeightParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout liftWeightLayout = new LinearLayout(this);
        liftWeightLayout.setGravity(Gravity.RIGHT);
        liftWeightLayout.setLayoutParams(liftWeightParams);
        liftWeightLayout.addView(textViewRight);

        layout.addView(liftWeightLayout);

        return layout;
    }
}
