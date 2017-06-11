package com.example.mattk.madcow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IndividualLift extends LinearLayout {
    public IndividualLift(Context context, String set, boolean isMaxLift) {
        super(context);
        init(context, set, isMaxLift);
    }

    private void init(Context context, String set, boolean isMaxLift) {
        this.setGravity(Gravity.CENTER);
        setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 0.20f));

        LinearLayoutCompat.LayoutParams tvParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(context);
        tv.setLayoutParams(tvParams);
        tv.setText(set);
        if (isMaxLift) {
            tv.setBackground(getResources().getDrawable(R.drawable.max_weight_circle));
            tv.setGravity(Gravity.CENTER);
            // tv.setBackgroundColor(getResources().getColor(R.color.primary_light));
        }
        addView(tv);
    }
}
