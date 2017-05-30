package com.example.mattk.madcow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IndividualLift extends LinearLayout {
    public IndividualLift(Context context, int weight) {
        super(context);
        init(context, weight);
    }

    private void init(Context context, int weight) {
        this.setGravity(Gravity.CENTER);
        setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 0.20f));

        LinearLayoutCompat.LayoutParams tvParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(context);
        tv.setLayoutParams(tvParams);
        tv.setText(Integer.toString(weight));
        addView(tv);
    }
}
