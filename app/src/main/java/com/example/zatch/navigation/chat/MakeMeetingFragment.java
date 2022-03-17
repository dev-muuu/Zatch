package com.example.zatch.navigation.chat;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;

import com.example.zatch.PNDialogMessage;
import com.example.zatch.PositiveNegativeDialog;
import com.example.zatch.R;
import com.example.zatch.ServiceType;
import com.example.zatch.databinding.FragmentMakeMeetingBinding;
import com.example.zatch.navigation.chat.data.MeetingData;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;

public class MakeMeetingFragment extends Fragment implements TimePickerDialog.TimePickerDialogListener {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Bundle bundle = new Bundle();

    private FragmentMakeMeetingBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentMakeMeetingBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(((MakeMeetingBottomSheet)getParentFragment().getParentFragment()).getIsMeetingMade()) {
            try {
                MeetingData meetingData = (MeetingData) getArguments().getSerializable("meetingData");
                initData(meetingData);
            }catch (NullPointerException e){
                Log.e("null","null");
            }
        }

        binding.button9.setOnClickListener(v-> makeReservationInfoDialog());

        binding.alarmSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->  {
            if(isChecked)
                sendDialogMessage();
        });

        //약속 시간 잡기
        binding.timeLayout1.setOnClickListener(v -> {
            openCalendarDialog();
        });
        binding.timeLayout2.setOnClickListener(v -> {
            openTimepickerDialog();
        });

        //약속 장소 잡기
        binding.meetingPlace.setOnClickListener(v->{
            onSaveInstanceState(bundle);
            Navigation.findNavController(getView()).navigate(R.id.action_makeMeetingFragment_to_addressSearchFragment);
        });

        Navigation.findNavController(getView()).getCurrentBackStackEntry().getSavedStateHandle()
                .getLiveData("result").observe(getViewLifecycleOwner(), o -> {
            onViewStateRestored(bundle);
            binding.meetingPlace.setText(o.toString());
        });
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("month",binding.makeMeetingMonth.getText().toString());
        outState.putString("date",binding.makeMeetingDate.getText().toString());
        outState.putString("hour",binding.makeMeetingHourText.getText().toString());
        outState.putString("minute",binding.makeMeetingMinuteText.getText().toString());
        outState.putBoolean("alarm",binding.alarmSwitch.isChecked());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            binding.makeMeetingMonth.setText(savedInstanceState.getString("month"));
            binding.makeMeetingDate.setText(savedInstanceState.getString("date"));
            binding.makeMeetingHourText.setText(savedInstanceState.getString("hour"));
            binding.makeMeetingMinuteText.setText(savedInstanceState.getString("minute"));
            binding.alarmSwitch.setChecked(savedInstanceState.getBoolean("alarm"));
        }
    }

    void initData(MeetingData data){

        binding.timeTitle.setTextColor(getResources().getColor(R.color.black_85));
        binding.placeTitle.setTextColor(getResources().getColor(R.color.black_85));
        binding.alarmTitle.setTextColor(getResources().getColor(R.color.black_85));

        binding.makeMeetingMonth.setText(data.getMonth());
        binding.makeMeetingDate.setText(data.getDate());
        binding.makeMeetingHourText.setText(data.getHour());
        binding.makeMeetingMinuteText.setText(data.getMinute());
        binding.meetingPlace.setText(data.getPlace());
        binding.alarmSwitch.setChecked(data.isGiveAlarm());

        binding.makeMeetingMonth.setTextColor(getResources().getColor(R.color.zatch_purple));
        binding.makeMeetingDate.setTextColor(getResources().getColor(R.color.zatch_purple));
        binding.makeMeetingHourText.setTextColor(getResources().getColor(R.color.zatch_purple));
        binding.makeMeetingMinuteText.setTextColor(getResources().getColor(R.color.zatch_purple));
        binding.meetingPlace.setTextColor(getResources().getColor(R.color.zatch_purple));

    }

    void openCalendarDialog() {

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("");
        MaterialDatePicker datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(selection -> {

            SimpleDateFormat month = new SimpleDateFormat("M");
            SimpleDateFormat date = new SimpleDateFormat("d");

            binding.makeMeetingMonth.setText(month.format(selection));
            binding.makeMeetingDate.setText(date.format(selection));

            datePicker.dismiss();
        });
        datePicker.addOnNegativeButtonClickListener(selection -> {
            datePicker.dismiss();
        });
        datePicker.show(getParentFragmentManager(), null);
    }

    void openTimepickerDialog() {
        TimePickerDialog dialog = TimePickerDialog.newInstance();
        dialog.setDialogListener(this);
        dialog.show(getParentFragmentManager(), null);
    }

    @Override
    public void finishDialog(String[] pickerResult) {
        binding.makeMeetingHourText.setText(pickerResult[0]);
        binding.makeMeetingMinuteText.setText(pickerResult[1]);
    }

    void sendDialogMessage() {

        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_message, null);
        builder.setView(view);
        TextView ok = view.findViewById(R.id.dialogOKButton);
        TextView text = view.findViewById(R.id.dialogInfoMessageText);
        text.setText("약속시간 30분 전에 자동으로 알림이 울립니다.");
        ok.setOnClickListener(v ->
                dialog.dismiss()
        );
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    void makeReservationInfoDialog() {
        PositiveNegativeDialog dialogClass = new PositiveNegativeDialog(getContext(), ServiceType.Zatch, PNDialogMessage.MakeMeeting);
        AlertDialog dialog = dialogClass.createDialog();
        dialogClass.getNegative().setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialogClass.getPositive().setOnClickListener(v -> {
            MakeMeetingBottomSheet bottomSheet = (MakeMeetingBottomSheet) getParentFragment().getParentFragment();
            bottomSheet.getBottomSheetListener().finishBottomSheet(makeDataClass());
            bottomSheet.dismiss();
            dialog.dismiss();
        });
        dialog.show();
    }

    private MeetingData makeDataClass(){
        MeetingData data = new MeetingData(
            binding.makeMeetingMonth.getText().toString(),
            binding.makeMeetingDate.getText().toString(),
            binding.makeMeetingHourText.getText().toString(),
            binding.makeMeetingMinuteText.getText().toString(),
            binding.meetingPlace.getText().toString(),
            binding.alarmSwitch.isChecked()
        );
        return data;
    }
}
