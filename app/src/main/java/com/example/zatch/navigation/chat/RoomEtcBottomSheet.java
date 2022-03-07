package com.example.zatch.navigation.chat;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zatch.PNDialogMessage;
import com.example.zatch.PositiveNegativeDialog;
import com.example.zatch.R;
import com.example.zatch.ServiceType;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

enum EtcFunc{
    Block,
    Declaration,
    Exit
}

public class RoomEtcBottomSheet extends BottomSheetDialogFragment {

    private View view;
    private RoomEtcBottomSheetListener listener;

    public RoomEtcBottomSheet(RoomEtcBottomSheetListener listener) {
        this.listener = listener;
    }

    interface RoomEtcBottomSheetListener{
        void finishBottomSheet(EtcFunc etc);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.bottomsheet_chatting_etc,container);
        view.findViewById(R.id.blockButton).setOnClickListener(onClickListener);
        view.findViewById(R.id.declarationButton).setOnClickListener(onClickListener);
        view.findViewById(R.id.exitButton).setOnClickListener(onClickListener);

        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.blockButton:
                    printDialogMessage(PNDialogMessage.Block);
                    break;
                case R.id.declarationButton:
                    listener.finishBottomSheet(EtcFunc.Declaration);
                    break;
                case R.id.exitButton:
                    printDialogMessage(PNDialogMessage.Exit);
                    break;
            }
        }
    };

    private void printDialogMessage(PNDialogMessage dialogData){
        PositiveNegativeDialog dialogClass = new PositiveNegativeDialog(getContext(), ServiceType.Zatch, dialogData);
        AlertDialog dialog = dialogClass.createDialog();
        dialogClass.getNegative().setOnClickListener(v->{
            dialog.dismiss();
        });
        dialogClass.getPositive().setOnClickListener(v->{
            dialog.dismiss();
            dismiss();
            if(dialogData == PNDialogMessage.Exit)
                listener.finishBottomSheet(EtcFunc.Exit);
            else
                listener.finishBottomSheet(EtcFunc.Block);
        });
        dialog.show();
    }
}
