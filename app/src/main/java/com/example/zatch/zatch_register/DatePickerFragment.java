package com.example.zatch.zatch_register;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.zatch.R;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment{

    NumberPicker yearPicker,monthPicker,datePicker;
    Button saveButton;

    public static DatePickerFragment newInstance(String title) {
        DatePickerFragment frag = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    public interface DatePickerDialogListener {
        void onDialogFinish(int year, int month, int date, String title);
    }

    public void setDatePickerDialogListener(DatePickerDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }

    DatePickerDialogListener dialogListener;


    @RequiresApi(api = Build.VERSION_CODES.Q)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //title 정보 받아오
        String title = getArguments().getString("title");

//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_datepicker,null);

        builder.setView(view);
        Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        TextView pickerTitle = view.findViewById(R.id.datePickerTitleText);
        pickerTitle.setText(title);
        yearPicker = view.findViewById(R.id.numberPickerYear);
        yearPicker.setTextColor(Color.BLACK);
        monthPicker = view.findViewById(R.id.numberPickerMonth);
        monthPicker.setTextColor(Color.BLACK);
        datePicker = view.findViewById(R.id.numberPickerDate);
        datePicker.setTextColor(Color.BLACK);

        saveButton = view.findViewById(R.id.datePickerSaveButton);
        Calendar c = Calendar.getInstance();
        int todayYear = c.get(Calendar.YEAR);
        int todayMonth = c.get(Calendar.MONTH);
        int todayDate = c.get(Calendar.DATE);

//        yearPicker.setMinValue(1980);
//        yearPicker.setMaxValue(todayYear+30);
        String[] yearString = new String[todayYear + 30 - 1980 + 1];
        for (int i = 1980, j = 0; i <= todayYear+30; i++,j++)
            yearString[j] = String.valueOf(i)+"년";
        yearPicker.setMinValue(0);
        yearPicker.setMaxValue(yearString.length-1);
        yearPicker.setValue(todayYear - 1980);
        yearPicker.setDisplayedValues(yearString);

        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(11);
        monthPicker.setValue(todayMonth);
        String[] monthString = new String[]{"1월", "2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"};
        monthPicker.setDisplayedValues(monthString);

        datePicker.setMinValue(0);
        datePicker.setMaxValue(30);
        datePicker.setValue(todayDate-1);
        String[] dateString = new String[31];
        for (int i = 1, j = 0; i <= 31; i++,j++)
            dateString[j] = String.valueOf(i)+"일";
        datePicker.setDisplayedValues(dateString);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = yearPicker.getValue() + 1980;
                int month = monthPicker.getValue()+1;
                int date = datePicker.getValue()+1;

                dialogListener.onDialogFinish(year,month,date, title);
                dialog.dismiss();
            }
        });

        return dialog;
    }

}