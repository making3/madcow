package com.making3.madcow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class WeightDialogFragment extends DialogFragment {
    private int _textViewId;
    private String _liftName;
    private String _startingWeight;
    WeightDialogListener listener;

    public interface WeightDialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String liftName, String text, int textViewId);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    public void setArguments(String startingWeight, String liftName, int textViewId) {
        _liftName = liftName;
        _startingWeight = startingWeight;
        _textViewId = textViewId;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof Activity) {
                listener = (WeightDialogListener) context;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    " must implement WeightDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_weight_dialog, null);
        final TextView setStartingWeight = (TextView)view.findViewById(R.id.setStartingWeight);
        final EditText weightText = (EditText)view.findViewById(R.id.weight);
        setStartingWeight.setText(_liftName + ": ");
        weightText.setText(_startingWeight);
        weightText.setSelection(_startingWeight.length());

        builder.setView(view)
            .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                                 public void onClick(DialogInterface dialog, int id) {
                 String weightTextValue = weightText.getText().toString();
                if (listener != null) {
                    listener.onDialogPositiveClick(WeightDialogFragment.this, _liftName, weightTextValue, _textViewId);
                }
            }
        })
            .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                                     public void onClick(DialogInterface dialog, int id) {
                if (listener != null) {
                    listener.onDialogNegativeClick(WeightDialogFragment.this);
                }
            }
        });
        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return dialog;
    }
}
