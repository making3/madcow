package com.example.mattk.madcow;

import android.content.Intent;
import android.os.Bundle;
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
        layout.addView((getWeekDayLayout(week, day)));
        layout.addView(getWorkoutLayout(Lift.SQUAT, calc.getMaxWeight(week, day, Lift.SQUAT)));

        if (day == 1 || day == 3) {
            layout.addView(getWorkoutLayout(Lift.BENCH, calc.getMaxWeight(week, day, Lift.BENCH)));
            layout.addView(getWorkoutLayout(Lift.ROW, calc.getMaxWeight(week, day, Lift.ROW)));
        } else {
            layout.addView(getWorkoutLayout(Lift.PRESS, calc.getMaxWeight(week, day, Lift.PRESS)));
            layout.addView(getWorkoutLayout(Lift.DEADLIFT, calc.getMaxWeight(week, day, Lift.DEADLIFT)));
        }
    }

    private FrameLayout getWeekDayLayout(int week, int day) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        layoutParams.setMargins(0, 10, 0, 0);
        FrameLayout layout = new FrameLayout(this);
        layout.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textViewWeek = new TextView(this);
        textViewWeek.setText("Week " + Integer.toString(week));
        textViewWeek.setLayoutParams(params);
        textViewWeek.setTextColor(getResources().getColor(R.color.secondary_text));
        textViewWeek.setTextSize(getResources().getDimension(R.dimen.lift_preview_week_day_text));
        layout.addView(textViewWeek);

        TextView textViewDay = new TextView(this);
        textViewDay.setText(Workout.getDayString(day));
        textViewDay.setTextColor(getResources().getColor(R.color.secondary_text));
        textViewWeek.setTextSize(getResources().getDimension(R.dimen.lift_preview_week_day_text));
        textViewDay.setLayoutParams(params);

        LinearLayout.LayoutParams liftWeightParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout liftWeightLayout = new LinearLayout(this);
        liftWeightLayout.setGravity(Gravity.RIGHT);
        liftWeightLayout.setLayoutParams(liftWeightParams);
        liftWeightLayout.addView(textViewDay);

        layout.addView(liftWeightLayout);

        return layout;
    }

    private FrameLayout getWorkoutLayout(Lift lift, int weight) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);

        layoutParams.setMargins(0, 10, 0, 0);
        FrameLayout layout = new FrameLayout(this);
        layout.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textViewLiftName = new TextView(this);
        textViewLiftName.setText(lift.toString());
        textViewLiftName.setLayoutParams(params);
        textViewLiftName.setTextColor(getResources().getColor(R.color.primary_text));
        textViewLiftName.setTextSize(getResources().getDimension(R.dimen.lift_preview_text));
        layout.addView(textViewLiftName);

        TextView textViewWeight = new TextView(this);
        textViewWeight.setText(Integer.toString(weight));
        textViewWeight.setTextColor(getResources().getColor(R.color.primary_text));
        textViewWeight.setTextSize(getResources().getDimension(R.dimen.lift_preview_text));
        textViewWeight.setLayoutParams(params);

        LinearLayout.LayoutParams liftWeightParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        LinearLayout liftWeightLayout = new LinearLayout(this);
        liftWeightLayout.setGravity(Gravity.RIGHT);
        liftWeightLayout.setLayoutParams(liftWeightParams);
        liftWeightLayout.addView(textViewWeight);

        layout.addView(liftWeightLayout);

        return layout;
    }
}
