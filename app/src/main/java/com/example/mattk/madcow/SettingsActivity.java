package com.example.mattk.madcow;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mattk.madcow.helpers.Settings;

public class SettingsActivity extends BaseActivity implements WeightDialogFragment.WeightDialogListener {
    private Settings _settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        _settings = new Settings(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView plateText = (TextView)findViewById(R.id.plate_value);
        TextView matchPrText = (TextView)findViewById(R.id.match_pr_week_value);
        TextView squatText = (TextView)findViewById(R.id.squat_value);
        TextView benchText = (TextView)findViewById(R.id.bench_value);
        TextView rowText = (TextView)findViewById(R.id.row_value);
        TextView pressText = (TextView)findViewById(R.id.press_value);
        TextView deadliftText = (TextView)findViewById(R.id.deadlift_value);

        plateText.setText(Float.toString(_settings.getSmallestPlate()));
        matchPrText.setText(Integer.toString(_settings.getPrMatchWeek()));
        squatText.setText(Integer.toString(_settings.getStartingSquat()));
        benchText.setText(Integer.toString(_settings.getStartingBench()));
        rowText.setText(Integer.toString(_settings.getStartingRow()));
        pressText.setText(Integer.toString(_settings.getStartingPress()));
        deadliftText.setText(Integer.toString(_settings.getStartingDeadlift()));
    }

    public void setStartingLiftClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        WeightDialogFragment fragment = new WeightDialogFragment();
        FrameLayout fl = (FrameLayout)v;
        RelativeLayout rl = (RelativeLayout)fl.getChildAt(0);

        String liftName = ((TextView)rl.getChildAt(0)).getText().toString();
        String currentWeight = ((TextView)rl.getChildAt(1)).getText().toString();
        int textViewId = rl.getChildAt(1).getId();
        fragment.setArguments(currentWeight, liftName, textViewId);

        fragment.show(fragmentManager, liftName.toLowerCase());
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String liftName, String result, int textViewId) {
        switch (liftName.toLowerCase()) {
            case "squat":
                _settings.setStartingSquat(Integer.parseInt(result));
                break;
            case "bench":
                _settings.setStartingBench(Integer.parseInt(result));
                break;
            case "row":
                _settings.setStartingRow(Integer.parseInt(result));
                break;
            case "press":
                _settings.setStartingPress(Integer.parseInt(result));
                break;
            case "deadlift":
                _settings.setStartingDeadlift(Integer.parseInt(result));
                break;
            case "plate":
                _settings.setSmallestPlate(Float.parseFloat(result));
                break;
            case "match_pr_week":
                _settings.setSmallestPlate(Integer.parseInt(result));
                break;
        }

        TextView settingText = (TextView) findViewById(textViewId);
        settingText.setText(result);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
    }
}
