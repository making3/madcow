package com.example.mattk.madcow;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WorkoutRow extends LinearLayout {
    TextView _liftName;

    public WorkoutRow(Context context) {
        super(context);
        init(context, null);
    }

    public WorkoutRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WorkoutRow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context,  AttributeSet attrs) {
        View.inflate(context, R.layout.activity_workout_row, this);
        _liftName = (TextView)findViewById(R.id.lift_name);

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.WorkoutRow,
                0, 0
            );

            String liftName = "";

            try {
                liftName = a.getString(R.styleable.WorkoutRow_liftName);
            } catch(Exception e) {
                Log.e("WorkoutRow", "There was an error loading attributes.");
            } finally {
                a.recycle();
            }

            SetLiftName(liftName);
        }

        LinearLayout layoutLifts = new LinearLayout(context);

        layoutLifts.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        layoutLifts.addView(new IndividualLift(context, 75));
        layoutLifts.addView(new IndividualLift(context, 90));
        layoutLifts.addView(new IndividualLift(context, 110));
        layoutLifts.addView(new IndividualLift(context, 125));
        layoutLifts.addView(new IndividualLift(context, 145));

        LinearLayout layout = (LinearLayout)findViewById(R.id.lift_row);
        layout.addView(layoutLifts);
    }

    public void SetLiftName(String liftName) {
        _liftName.setText(liftName);
    }
}
