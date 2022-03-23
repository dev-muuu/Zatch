package com.example.zatch.navigation.chat;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.zatch.PNDialogMessage;
import com.example.zatch.PositiveNegativeDialog;
import com.example.zatch.R;
import com.example.zatch.ServiceType;
import com.example.zatch.databinding.BottomsheetChattingEtcBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

enum EtcFunc{
    Block,
    Declaration,
    Out
}

public class RoomEtcBottomSheet extends BottomSheetDialogFragment {

    private RoomEtcBottomSheetListener listener;
    private ServiceType serviceType;
    private BottomsheetChattingEtcBinding binding;

    public RoomEtcBottomSheet(RoomEtcBottomSheetListener listener, ServiceType serviceType) {
        this.listener = listener;
        this.serviceType = serviceType;
    }

    interface RoomEtcBottomSheetListener{
        void finishBottomSheet(EtcFunc etc);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = BottomsheetChattingEtcBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(serviceType == ServiceType.Gatch)
            binding.exitButton.setVisibility(View.VISIBLE);

        onClickListener();
    }

    private void onClickListener(){
        binding.blockButton.setOnClickListener(v -> printDialogMessage(PNDialogMessage.Block));
        binding.declarationButton.setOnClickListener(v-> {
            showDeclarationDialog();
            listener.finishBottomSheet(EtcFunc.Declaration);
        });
        binding.exitButton.setOnClickListener(v-> printDialogMessage(PNDialogMessage.Exit));
    }

    void showDeclarationDialog(){
        dismiss();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_chatting_declaration,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void printDialogMessage(PNDialogMessage dialogData){
        PositiveNegativeDialog dialogClass = new PositiveNegativeDialog(getContext(), serviceType, dialogData);
        AlertDialog dialog = dialogClass.createDialog();
        dialogClass.getNegative().setOnClickListener(v->{
            dialog.dismiss();
        });
        dialogClass.getPositive().setOnClickListener(v->{
            dialog.dismiss();
            dismiss();
            if(dialogData == PNDialogMessage.Exit)
                listener.finishBottomSheet(EtcFunc.Out);
            else
                listener.finishBottomSheet(EtcFunc.Block);
        });
        dialog.show();
    }
}
