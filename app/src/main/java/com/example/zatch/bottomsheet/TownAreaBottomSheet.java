package com.example.zatch.bottomsheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zatch.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class TownAreaBottomSheet extends BottomSheetDialogFragment implements SeekBar.OnSeekBarChangeListener {

    View view;
    String[] townData;
    String currentArea;
    ImageView image;
    private TownAreaBottomSheetListener dialogListener;

    public void setDialogListener(TownAreaBottomSheetListener dialogListener){
        this.dialogListener = dialogListener;
    }

    public interface TownAreaBottomSheetListener{
        void TownAreaBottomSheetFinish();
    }

    public TownAreaBottomSheet(String[] townData, String currentArea){
        this.townData = townData;
        this.currentArea = currentArea;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.bottom_sheet_town_area,container);
        image = view.findViewById(R.id.townSelectImage);
        SeekBar seekBar = view.findViewById(R.id.townSeekBar);
        seekBar.setOnSeekBarChangeListener(this);

        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (progress){
            case 0:
                image.setImageDrawable(getResources().getDrawable(R.drawable.town_set_image_1));
                break;
            case 1:
                image.setImageDrawable(getResources().getDrawable(R.drawable.town_set_image_2));
                break;
            case 2:
                image.setImageDrawable(getResources().getDrawable(R.drawable.town_set_image_3));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
