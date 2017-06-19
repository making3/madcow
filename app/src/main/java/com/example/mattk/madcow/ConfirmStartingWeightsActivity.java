package com.example.mattk.madcow;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mattk.madcow.data.Lift;
import com.example.mattk.madcow.helpers.Settings;

import java.util.HashMap;
import java.util.Map;

public class ConfirmStartingWeightsActivity extends AppCompatActivity {
    private HashMap<Lift, Integer> _startingWeights;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_starting_weights);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("Confirm Starting Weights");
        bar.setDisplayHomeAsUpEnabled(true);

        _startingWeights = (HashMap<Lift, Integer>)getIntent().getExtras().get("STARTING_WEIGHTS");

        for (Map.Entry<Lift, Integer> startingWeight : _startingWeights.entrySet()) {
            Lift lift = startingWeight.getKey();
            String weight = Integer.toString(startingWeight.getValue());

            switch (lift) {
                case SQUAT:
                    ((TextView)findViewById(R.id.lift_squat)).setText(weight);
                    break;
                case BENCH:
                    ((TextView)findViewById(R.id.lift_bench)).setText(weight);
                    break;
                case ROW:
                    ((TextView)findViewById(R.id.lift_row)).setText(weight);
                    break;
                case PRESS:
                    ((TextView)findViewById(R.id.lift_press)).setText(weight);
                    break;
                case DEADLIFT:
                    ((TextView)findViewById(R.id.lift_deadlift)).setText(weight);
                    break;
            }
        }
    }

    public void confirmStartingWeights(View v) {
        for (Map.Entry<Lift, Integer> startingWeight : _startingWeights.entrySet()) {
            Lift lift = startingWeight.getKey();
            int weight = startingWeight.getValue();

            Settings settings = new Settings(this);

            switch (lift) {
                case SQUAT:
                    settings.setStartingSquat(weight);
                    break;
                case BENCH:
                    settings.setStartingBench(weight);
                    break;
                case ROW:
                    settings.setStartingRow(weight);
                    break;
                case PRESS:
                    settings.setStartingPress(weight);
                    break;
                case DEADLIFT:
                    settings.setStartingDeadlift(weight);
                    break;
            }
        }

        redirectToMain(v);
    }

    public void redirectToMain(View v) {
        Intent intent = new Intent(ConfirmStartingWeightsActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
