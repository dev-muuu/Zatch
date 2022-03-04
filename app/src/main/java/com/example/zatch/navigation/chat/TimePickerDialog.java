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

    private NumberPicker ampmPicker, hourPicker, minutePicker;
    private TimePickerDialogListener listener;
    private String[] ampmList, hourList, minuteList;

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
        ampmPicker = view.findViewById(R.id.timePickerAMPM);
        hourPicker = view.findViewById(R.id.timePickerHour);
        minutePicker = view.findViewById(R.id.timePickerMinute);

        ampmList = new String[]{"오전","오후"};

        ampmPicker.setDisplayedValues(ampmList);
        hourPicker.setDisplayedValues(makeHourStringData());
        minutePicker.setDisplayedValues(makeMinuteStringData());

        ampmPicker.setMinValue(0);
        ampmPicker.setMaxValue(ampmList.length - 1);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(hourList.length-1);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(minuteList.length-1);

        view.findViewById(R.id.timeFixButton).setOnClickListener(v -> {
            String[] result = new String[3];
            result[0] = ampmList[ampmPicker.getValue()];
            result[1] = hourList[hourPicker.getValue()];
            result[2] = minuteList[minutePicker.getValue()];
            listener.finishDialog(result);
            dialog.dismiss();
        });

        return dialog;
    }

    private String[] makeHourStringData(){
        hourList = new String[12];
        for(int i = 1; i <= 12; i++)
            hourList[i-1] = String.valueOf(i);

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
