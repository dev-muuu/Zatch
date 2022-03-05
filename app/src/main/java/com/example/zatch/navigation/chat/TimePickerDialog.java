package com.example.zatch.navigation.chat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.fragment.app.DialogFragment;

import com.example.zatch.R;

public class TimePickerDialog extends DialogFragment {

    private NumberPicker hourPicker, minutePicker;
    private TimePickerDialogListener listener;
    private String[] hourList, minuteList;

//    public static TimePickerDialog newInstance(int title)
    public static TimePickerDialog newInstance() {
        TimePickerDialog frag = new TimePickerDialog();
//        Bundle args = new Bundle();
//        args.putInt("title", title);
//        frag.setArguments(args);
        return frag;
    }

    interface TimePickerDialogListener{
        void finishDialog(String[] pickerResult);
    }

    public void setDialogListener(TimePickerDialogListener listener){
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

//        int title = getArguments().getInt("title");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_timepicker,null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        //view ui set
        hourPicker = view.findViewById(R.id.timePickerHour);
        minutePicker = view.findViewById(R.id.timePickerMinute);

        hourPicker.setDisplayedValues(makeHourStringData());
        minutePicker.setDisplayedValues(makeMinuteStringData());

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(hourList.length-1);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(minuteList.length-1);

        view.findViewById(R.id.timeFixButton).setOnClickListener(v -> {
            String[] result = new String[2];
            result[0] = hourList[hourPicker.getValue()];
            result[1] = minuteList[minutePicker.getValue()];
            listener.finishDialog(result);
            dialog.dismiss();
        });

        return dialog;
    }

    private String[] makeHourStringData(){
        hourList = new String[24];
        for(int i = 0; i < 24; i++)
            if(i < 10)
                hourList[i] = "0" + String.valueOf(i);
            else
                hourList[i] = String.valueOf(i);

        return hourList;
    }

    private String[] makeMinuteStringData(){
        minuteList = new String[60];
        for(int i = 0; i < 60; i++) {
            if(i < 10)
                minuteList[i] = "0" + String.valueOf(i);
            else
                minuteList[i] = String.valueOf(i);
        }
        return minuteList;
    }
}
