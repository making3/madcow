package com.example.mattk.madcow.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
    final String SET_INTERVAL = "set_interval";
    final String SQUAT = "squat";
    final String BENCH = "bench";
    final String ROW = "row";
    final String PRESS = "press";
    final String DEADLIFT = "deadlift";
    final String WEEK = "week";
    final String DAY = "day";
    final String PLATE = "plate";
    final String PR_MATCH_WEEK = "pr_match_week";

    private SharedPreferences _preferences;

    public Settings(Context context) {
        _preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Settings(SharedPreferences preferences) {
        _preferences = preferences;
    }

    public float getSetInterval() {
        return _preferences.getFloat(SET_INTERVAL, 0.125f);
    }

    public int getStartingSquat() {
        return _preferences.getInt(SQUAT, 230);
    }

    public void setStartingSquat(int weight) {
        saveInt(SQUAT, weight);
    }

    public int getStartingBench() {
        return _preferences.getInt(BENCH, 140);
    }

    public void setStartingBench(int weight) {
        saveInt(BENCH, weight);
    }

    public int getStartingRow() {
        return _preferences.getInt(ROW, 145);
    }

    public void setStartingRow(int weight) {
        saveInt(ROW, weight);
    }

    public int getStartingPress() {
        return _preferences.getInt(PRESS, 95);
    }

    public void setStartingPress(int weight) {
        saveInt(PRESS, weight);
    }

    public int getStartingDeadlift() {
        return _preferences.getInt(DEADLIFT, 250);
    }

    public void setStartingDeadlift(int weight) {
        saveInt(DEADLIFT, weight);
    }

    public float getSmallestPlate() {
        return _preferences.getFloat(PLATE, 2.5f);
    }

    public void setSmallestPlate(float plate) {
        saveFloat(PLATE, plate);
    }

    public void setWeek(int week){
        saveInt(WEEK, week);
    }

    public int getWeek() {
        return _preferences.getInt(WEEK, 1);
    }

    public void setDay(int day){
        saveInt(DAY, day);
    }

    public int getDay() {
        return _preferences.getInt(DAY, 1);
    }

    public int getPrMatchWeek() {
        return _preferences.getInt(PR_MATCH_WEEK, 4);
    }

    public void setPrMatchWeek(int prMatchWeek) {
        saveInt(PR_MATCH_WEEK, prMatchWeek);
    }

    private void saveInt(String name, int value) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    private void saveFloat(String name, float value) {
        SharedPreferences.Editor editor = _preferences.edit();
        editor.putFloat(name, value);
        editor.commit();
    }
}
