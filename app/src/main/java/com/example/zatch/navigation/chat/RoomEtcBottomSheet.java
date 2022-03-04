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

import com.example.zatch.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

enum EtcFunc{
    Block,
    Declaration,
    Exit
}

public class RoomEtcBottomSheet extends BottomSheetDialogFragment {

    private View view;
    private RoomEtcBottomSheetListener listener;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;

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
                    blockFunc();
                    break;
                case R.id.declarationButton:
                    listener.finishBottomSheet(EtcFunc.Declaration);
                    break;
                case R.id.exitButton:
                    exitFunc();
                    break;
                case R.id.dialogNegativeButton:
                    dialog.dismiss();
                    break;
            }
        }
    };

    void exitFunc(){
        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_negative_positive,null);
        TextView negative = view.findViewById(R.id.dialogNegativeButton);
        negative.setOnClickListener(onClickListener);
        TextView positive = view.findViewById(R.id.dialogPositiveButton);
        positive.setOnClickListener(v -> {
            dialog.dismiss();
            dismiss();
            listener.finishBottomSheet(EtcFunc.Exit);
        });
        positive.setText("네, 확인했습니다.");
        TextView message = view.findViewById(R.id.dialogAskMessage);
        message.setText("채팅방을 나가시겠습니까?\n" +
                "채팅방을 나가면 채팅 내역은 복구되지 않습니다.");
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    void blockFunc(){
        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_negative_positive,null);
        TextView negative = view.findViewById(R.id.dialogNegativeButton);
        negative.setOnClickListener(onClickListener);
        TextView positive = view.findViewById(R.id.dialogPositiveButton);
        positive.setOnClickListener(v -> {
            dialog.dismiss();
            dismiss();
            listener.finishBottomSheet(EtcFunc.Block);
        });
        positive.setText("네, 차단합니다.");
        TextView message = view.findViewById(R.id.dialogAskMessage);
        message.setText("한민지님을 차단하시겠습니까?\n" +
                "더 이상의 대화가 불가합니다.");
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
