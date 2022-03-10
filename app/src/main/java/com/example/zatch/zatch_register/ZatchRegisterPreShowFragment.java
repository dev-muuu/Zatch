package com.example.zatch.zatch_register;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.zatch.PNDialogMessage;
import com.example.zatch.PositiveNegativeDialog;
import com.example.zatch.R;
import com.example.zatch.ServiceType;

public class ZatchRegisterPreShowFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_zatch_register_pre_show, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.zatchRegisterButton).setOnClickListener(onClickListener);
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
        PositiveNegativeDialog dialogClass = new PositiveNegativeDialog(getContext(), ServiceType.Zatch, PNDialogMessage.Register);
        AlertDialog dialog = dialogClass.createDialog();
        dialogClass.setMessageText("재치 " + dialogClass.getMessageText());
        dialogClass.getNegative().setOnClickListener(v->{
            dialog.dismiss();
        });
        dialogClass.getPositive().setOnClickListener(v->{
            dialog.dismiss();
            Toast.makeText(getContext(),"등록 완료되었습니다.",Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }
}
