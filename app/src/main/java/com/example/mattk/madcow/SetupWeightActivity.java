package com.example.mattk.madcow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mattk.madcow.helpers.Settings;

public class SetupWeightActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        final EditText maxLiftWeight = (EditText)findViewById(R.id.max_lift_weight);
        final EditText maxLiftReps = (EditText)findViewById(R.id.max_lift_reps);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateResults(maxLiftWeight.getText().toString(), maxLiftReps.getText().toString());
            }
        };
        maxLiftWeight.addTextChangedListener(watcher);
        maxLiftReps.addTextChangedListener(watcher);
    }

    private void calculateResults(String weightString, String repString) {
        if (isValidInteger(weightString) == false || isValidInteger(repString) == false) {
            return;
        }

        int weight = Integer.parseInt(weightString);
        int reps = Integer.parseInt(repString);


        // 5RM =G9*(1.0278-(0.0278*5))
        // 1RM =(C9)/(1.0278-(0.0278*D9))
        //      Weight / (1.0278-(0.0278*reps))
        float oneRepMax = Math.round(weight / (float)(1.0278 - (0.0278 * reps)));
        TextView oneRepMaxText = (TextView)findViewById(R.id.estimated_one_rep);
        oneRepMaxText.setText(Float.toString(oneRepMax));

        float fiveRepMax = Math.round(oneRepMax * (float)(1.0278 - (0.0278 * 5)));
        TextView fiveRepMaxText = (TextView)findViewById(R.id.estimated_five_rep);
        fiveRepMaxText.setText(Float.toString(fiveRepMax));

        // Starting Weight =ROUND(H9*((1/1.025)^(PRWEEK-1))/(2*PLATE),0)*2*PLATE
        //                        ROUND(fiveRepMax * ((1 / 1.025) ^ (PRWEEK - 1)) / (2 * PLATE), 0) * 2 * PLATE
        //
        Settings settings = new Settings(this);

        int matchPrWeek = 3; // TODO: Create a setting for the PRWEEK value
        float weekPower = (float)Math.pow((1 / 1.025), matchPrWeek);
        float smallestPlate = settings.getSmallestPlate();
        float tempStarting = Math.round(fiveRepMax * weekPower / (2 * smallestPlate));
        float startingWeight = tempStarting * 2 * smallestPlate;
        TextView startingWeightText = (TextView)findViewById(R.id.estimated_starting_weight);
        startingWeightText.setText(Float.toString(startingWeight));
        // TODO: Test starting weight with various weights. Should move this to LiftCalculator or another helper for test purposes.
    }

    private boolean isValidInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
