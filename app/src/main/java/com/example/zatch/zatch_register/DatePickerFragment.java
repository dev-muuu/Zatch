package com.example.zatch.zatch_register;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.example.zatch.databinding.DialogDatepickerBinding;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment{

    private DatePickerDialogListener dialogListener;
    private DialogDatepickerBinding binding;


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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //title 정보 받아오
        String title = getArguments().getString("title");

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        binding = DialogDatepickerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.datePickerTitleText.setText(title);
        binding.numberPickerYear.setTextColor(Color.BLACK);
        binding.numberPickerMonth.setTextColor(Color.BLACK);
        binding.numberPickerDate.setTextColor(Color.BLACK);

        Calendar c = Calendar.getInstance();
        int todayYear = c.get(Calendar.YEAR);
        int todayMonth = c.get(Calendar.MONTH);
        int todayDate = c.get(Calendar.DATE);

        String[] yearString = new String[todayYear + 30 - 1980 + 1];
        for (int i = 1980, j = 0; i <= todayYear+30; i++,j++)
            yearString[j] = String.valueOf(i)+"년";
        binding.numberPickerYear.setMinValue(0);
        binding.numberPickerYear.setMaxValue(yearString.length-1);
        binding.numberPickerYear.setValue(todayYear - 1980);
        binding.numberPickerYear.setDisplayedValues(yearString);

        binding.numberPickerMonth.setMinValue(0);
        binding.numberPickerMonth.setMaxValue(11);
        binding.numberPickerMonth.setValue(todayMonth);
        String[] monthString = new String[]{"1월", "2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"};
        binding.numberPickerMonth.setDisplayedValues(monthString);

        binding.numberPickerDate.setMinValue(0);
        binding.numberPickerDate.setMaxValue(30);
        binding.numberPickerDate.setValue(todayDate-1);
        String[] dateString = new String[31];
        for (int i = 1, j = 0; i <= 31; i++,j++)
            dateString[j] = String.valueOf(i)+"일";
        binding.numberPickerDate.setDisplayedValues(dateString);

        binding.datePickerSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = binding.numberPickerYear.getValue() + 1980;
                int month = binding.numberPickerMonth.getValue()+1;
                int date = binding.numberPickerDate.getValue()+1;

                dialogListener.onDialogFinish(year,month,date, title);
                dialog.dismiss();
            }
        });

        return dialog;
    }

}