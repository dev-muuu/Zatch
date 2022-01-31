package com.example.zatch.zatch_register;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zatch.R;

public class ZatchRegisterPreShowFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_zatch_register_pre_show, container, false);

        view.findViewById(R.id.zatchRegisterButton).setOnClickListener(onClickListener);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.zatchRegisterButton:
                    registerPopUp();
                    break;
            }
        }
    };

    void registerPopUp(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_negative_positive,null);
        builder.setView(view);
        Button positive = view.findViewById(R.id.dialogPositiveButton);
        Button negative = view.findViewById(R.id.dialogNegativeButton);
        AlertDialog messageDialog = builder.create();
        messageDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageDialog.dismiss();
                Toast.makeText(getContext(),"등록 완료되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageDialog.dismiss();
            }
        });
        messageDialog.show();

    }
}
