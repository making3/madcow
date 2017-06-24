package com.making3.madcow;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.making3.madcow.data.Lift;
import com.making3.madcow.helpers.LiftCalculator;
import com.making3.madcow.helpers.Settings;

import java.util.HashMap;
import java.util.Queue;

public class SetupWeightActivity extends AppCompatActivity {
    private Queue<Lift> _lifts;
    private Lift _currentLift;
    private HashMap<Lift, Integer> _startingWeights;

    private EditText _maxWeightEditText;
    private EditText _maxRepsEditText;
    private TextView _maxLiftTextView;
    private TextView _startingWeightText;

    public SetupWeightActivity() {
        _startingWeights = new HashMap<>();
        _lifts = Lift.getLiftsQueue();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        _maxWeightEditText = (EditText)findViewById(R.id.max_lift_weight);
        _maxRepsEditText = (EditText)findViewById(R.id.max_lift_reps);
        _maxLiftTextView = (TextView)findViewById(R.id.max_lift_label);
        _startingWeightText = (TextView)findViewById(R.id.estimated_starting_weight);

        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                calculateResults(_maxWeightEditText.getText().toString(), _maxRepsEditText.getText().toString());
            }
        };
        _maxWeightEditText.addTextChangedListener(watcher);
        _maxRepsEditText.addTextChangedListener(watcher);

        setupNextLift();
    }

    private void setupNextLift() {
        if (_lifts.isEmpty()) {
            confirmStartingLifts();
            return;
        }

        _currentLift = _lifts.remove();
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Setup Starting " + _currentLift.toString());
        bar.setDisplayHomeAsUpEnabled(true);

        _maxLiftTextView.setText("Max " + _currentLift.toString());
        _maxWeightEditText.setText("");
        _maxRepsEditText.setText("5");
        _maxWeightEditText.requestFocus();

        // Allows the first edit text to show keyboard
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        // Allows other edit texts to show keyboards (at least on Nextbit Robin phones so far)
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(_maxWeightEditText, InputMethodManager.SHOW_IMPLICIT);
    }

    private void confirmStartingLifts() {
        Intent intent = new Intent(getBaseContext(), ConfirmStartingWeightsActivity.class);
        intent.putExtra("STARTING_WEIGHTS", _startingWeights);
        startActivity(intent);
    }

    private void calculateResults(String weightString, String repString) {
        TextView oneRepMaxText = (TextView)findViewById(R.id.estimated_one_rep);
        TextView fiveRepMaxText = (TextView)findViewById(R.id.estimated_five_rep);
        TextView startingWeightText = (TextView)findViewById(R.id.estimated_starting_weight);
        Button nextStartingWeight = (Button)findViewById(R.id.next_starting_weight);

        if (isValidInteger(weightString) == false || isValidInteger(repString) == false) {
            final String notAvailable = "N/A";
            oneRepMaxText.setText(notAvailable);
            fiveRepMaxText.setText(notAvailable);
            startingWeightText.setText(notAvailable);
            nextStartingWeight.setEnabled(false);
            return;
        }

        int weight = Integer.parseInt(weightString);
        int reps = Integer.parseInt(repString);

        Settings settings = new Settings(this);
        LiftCalculator calc = new LiftCalculator(settings);

        int oneRepMax = calc.getOneRepMax(weight, reps);
        oneRepMaxText.setText(Integer.toString(oneRepMax));

        int fiveRepMax = calc.getFiveRepMax(oneRepMax);
        fiveRepMaxText.setText(Integer.toString(fiveRepMax));

        int startingWeight = calc.getStartingWeight(fiveRepMax);
        startingWeightText.setText(Integer.toString(startingWeight));
        nextStartingWeight.setEnabled(true);
    }

    private boolean isValidInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void nextStartingWeightClick(View v) {
        int startingWeight = Integer.parseInt(_startingWeightText.getText().toString());
        _startingWeights.put(_currentLift, startingWeight);
        setupNextLift();
    }
}
