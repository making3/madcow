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
import com.example.mattk.madcow.helpers.LiftCalculator;
import com.example.mattk.madcow.helpers.Settings;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Settings settings = new Settings(this);

        int currentDay = settings.getDay();
        int currentWeek = settings.getWeek();

        int lastDay = currentDay - 1 == 0 ? (currentWeek == 1 ? 1 : 3) : currentDay - 1;
        int lastWeek = currentWeek == 1 ? 1 : (currentDay == 1 ? currentWeek - 1 : currentWeek);

        listPreviousWorkouts(lastWeek, lastDay, settings);
        listCurrentWorkouts(currentWeek, currentDay, settings);
    }

    public void nextWorkout(View v) {
        Intent workoutIntent = new Intent(MainActivity.this, WorkoutActivity.class);
        MainActivity.this.startActivity(workoutIntent);
    }

    public void previousWorkout(View v) {
        // TODO: Implement
    }

    private void listCurrentWorkouts(int week, int day, Settings settings) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.nextWorkouts);
        listWorkouts(week, day, layout, settings);
    }

    public void listPreviousWorkouts(int week, int day, Settings settings) {
        // TODO: Hide this if the current week/day is the first week/day
        LinearLayout layout = (LinearLayout) findViewById(R.id.lastWorkouts);
        listWorkouts(week, day, layout, settings);
    }

    private void listWorkouts(int week, int day, LinearLayout layout, Settings settings) {
        LiftCalculator calc = new LiftCalculator(settings);

        layout.addView(getWorkoutLayout(Lift.SQUAT, calc.getMaxWeight(week, day, Lift.SQUAT)));

        if (day == 1 || day == 3) {
            layout.addView(getWorkoutLayout(Lift.BENCH, calc.getMaxWeight(week, day, Lift.BENCH)));
            layout.addView(getWorkoutLayout(Lift.ROW, calc.getMaxWeight(week, day, Lift.ROW)));
        } else {
            layout.addView(getWorkoutLayout(Lift.PRESS, calc.getMaxWeight(week, day, Lift.PRESS)));
            layout.addView(getWorkoutLayout(Lift.DEADLIFT, calc.getMaxWeight(week, day, Lift.DEADLIFT)));
        }
    }

    private FrameLayout getWorkoutLayout(Lift lift, int weight) {
        // TODO: Add padding and stuff...
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        FrameLayout layout = new FrameLayout(this);
        layout.setLayoutParams(layoutParams);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView textViewLiftName = new TextView(this);
        textViewLiftName.setText(lift.toString());
        textViewLiftName.setLayoutParams(params);
        layout.addView(textViewLiftName);

        TextView textViewWeight = new TextView(this);
        textViewWeight.setText(Integer.toString(weight));
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


                /*<FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
                    <TextView
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:text="Squat"/>
                    <TextView
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="right"
        android:text="245"/>
                </FrameLayout>*/

}
