package com.example.mattk.madcow.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by mattk on 6/4/2017.
 */

public class Settings {
    final String SET_INTERVAL = "set_interval";
    final String SQUAT = "squat";
    final String BENCH = "bench";
    final String ROW = "row";
    final String PRESS = "press";
    final String DEADLIFT = "deadlift";

    private SharedPreferences _preferences;

    public Settings(Context context) {
        _preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public float getSetInterval() {
        return _preferences.getFloat(SET_INTERVAL, 0.125f);
    }

    public int getStartingSquat() {
        return _preferences.getInt(SQUAT, 230);
    }

    public void setStartingSquat(int weight) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putInt(SQUAT, weight);
        editor.commit();
    }

    public int getStartingBench() {
        return _preferences.getInt(BENCH, 140);
    }

    public void setStartingBench(int weight) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putInt(BENCH, weight);
        editor.commit();
    }

    public int getStartingRow() {
        return _preferences.getInt(ROW, 145);
    }

    public void setStartingRow(int weight) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putInt(ROW, weight);
        editor.commit();
    }

    public int getStartingPress() {
        return _preferences.getInt(PRESS, 95);
    }

    public void setStartingPress(int weight) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putInt(PRESS, weight);
        editor.commit();
    }

    public int getStartingDeadlift() {
        return _preferences.getInt(DEADLIFT, 250);
    }

    public void setStartingDeadlift(int weight) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putInt(DEADLIFT, weight);
        editor.commit();
    }
}
