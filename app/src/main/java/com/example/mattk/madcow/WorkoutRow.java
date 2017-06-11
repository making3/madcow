package com.example.mattk.madcow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WorkoutRow extends LinearLayout {
    public WorkoutRow(Context context) {
        super(context);
        init(context);
    }

    public WorkoutRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WorkoutRow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.activity_workout_row, this);
    }

    public void SetLifts(Context context, String liftName, int[] liftNumbers) {
        LinearLayout layout = (LinearLayout)findViewById(R.id.lift_row);
        layout.removeAllViews();

        TextView liftNameTextView = new TextView(context);
        liftNameTextView.setText(liftName);
        layout.addView(liftNameTextView);

        LinearLayout layoutLifts = new LinearLayout(context);
        layoutLifts.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        for (int lift : liftNumbers) {
            layoutLifts.addView(new IndividualLift(context, lift));
        }

        layout.addView(layoutLifts);
    }
}
