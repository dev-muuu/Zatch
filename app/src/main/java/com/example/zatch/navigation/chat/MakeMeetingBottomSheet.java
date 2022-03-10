package com.example.zatch.navigation.chat;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zatch.PNDialogMessage;
import com.example.zatch.PositiveNegativeDialog;
import com.example.zatch.R;
import com.example.zatch.ServiceType;
import com.example.zatch.navigation.chat.data.SearchPlaceData;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MakeMeetingBottomSheet extends BottomSheetDialogFragment implements TimePickerDialog.TimePickerDialogListener {

    private View view;
    private MakeMeetingBottomSheetListener listener;
    private Switch alarmSwitch;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private RelativeLayout timeLayout1, timeLayout2;
    private TextView meetingHour, meetingMinute, meetingMonth, meetingDate;

    static final String BASE_URL = "https://dapi.kakao.com/";
    static final String API_KEY = "KakaoAK de8b17f677631d70f9545c4b19608dcf";

    interface MakeMeetingBottomSheetListener {
        void finishBottomSheet(boolean isFinish);
    }

    public MakeMeetingBottomSheet(MakeMeetingBottomSheetListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.bottom_sheet_make_meeting, container);

        view.findViewById(R.id.button9).setOnClickListener(v -> {
            makeReservationInfoDialog();
        });

        alarmSwitch = view.findViewById(R.id.switch1);
        alarmSwitch.setOnClickListener(v -> {
            if (alarmSwitch.isChecked())
                sendDialogMessage();
        });

        //약속 잡기
        timeLayout1 = view.findViewById(R.id.timeLayout1);
        timeLayout2 = view.findViewById(R.id.timeLayout2);
        timeLayout1.setOnClickListener(v -> {
            openCalendarDialog();
        });
        timeLayout2.setOnClickListener(v -> {
            openTimepickerDialog();
        });

        meetingHour = view.findViewById(R.id.makeMeetingHourText);
        meetingMinute = view.findViewById(R.id.makeMeetingMinuteText);
        meetingMonth = view.findViewById(R.id.makeMeetingMonth);
        meetingDate = view.findViewById(R.id.makeMeetingDate);

        searchByKeyword("상현동");

        return view;
    }

    void openCalendarDialog() {

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("");
        MaterialDatePicker datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(selection -> {

            SimpleDateFormat month = new SimpleDateFormat("M");
            SimpleDateFormat date = new SimpleDateFormat("d");

            meetingMonth.setText(month.format(selection));
            meetingDate.setText(date.format(selection));

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
        meetingHour.setText(pickerResult[0]);
        meetingMinute.setText(pickerResult[1]);

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
            listener.finishBottomSheet(true);
            dialog.dismiss();
        });
        dialog.show();
    }

    //장소 검색

    public class ResultSearchKeyword {
        List<SearchPlaceData> documents;
    }

    private void searchByKeyword(String keyword) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
        KakaoApiService api = builder.build().create(KakaoApiService.class);
        Call<ResultSearchKeyword> call = api.getKakaoAddress(API_KEY, keyword);

        call.enqueue(new Callback<ResultSearchKeyword>() {
            @Override
            public void onResponse(Call<ResultSearchKeyword> call, Response<ResultSearchKeyword> response) {
                System.out.println(response.raw());
                System.out.println(response.body().documents.get(0).getPlaceName());
            }

            @Override
            public void onFailure(Call<ResultSearchKeyword> call, Throwable t) {
                Log.w("통신실패", "Raw: ${response.raw()}");
            }
        });


    }

}

