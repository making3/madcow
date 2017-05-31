package com.example.mattk.madcow;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mattk.madcow.data.Lift;

import java.util.prefs.Preferences;

public class WorkoutActivity extends AppCompatActivity {
    private static final String WEEK = "week";
    private static final String DAY = "day";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        int week = getWeek();
        int day = getDay();
    }

    private int getWeek() {
        SharedPreferences prefs = getPreferences(0);
        return prefs.getInt(WEEK, 1);
    }

    private int getDay() {
        SharedPreferences prefs = getPreferences(0);
        return prefs.getInt(DAY, 1);
    }
}
