package com.example.mattk.madcow;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WorkoutRow extends LinearLayout {
    private TextView _liftName;

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
        _liftName = (TextView)findViewById(R.id.lift_name);
    }

    public void SetLifts(Context context, String liftName, int[] liftNumbers) {
        LinearLayout layout = (LinearLayout)findViewById(R.id.lift_row);
        _liftName.setText(liftName);

        int count = layout.getChildCount();
        if (count > 1) {
            layout.removeViews(1, count - 1);
        }

        LinearLayout layoutLifts = new LinearLayout(context);
        layoutLifts.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        for (int lift : liftNumbers) {
            layoutLifts.addView(new IndividualLift(context, lift));
        }

        layout.addView(layoutLifts);
    }
}
