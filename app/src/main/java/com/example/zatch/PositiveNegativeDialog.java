package com.example.zatch;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class PositiveNegativeDialog {

    private View view;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private TextView negative, positive, message;
    private PNDialogMessage dialogData;

    public PositiveNegativeDialog(Context context, ServiceType type, PNDialogMessage dialogData) {
        this.dialogData = dialogData;
        this.builder = new AlertDialog.Builder(context);
        if(type == ServiceType.Zatch)
            this.view = LayoutInflater.from(context).inflate(R.layout.dialog_negative_positive,null);
        else
            this.view = LayoutInflater.from(context).inflate(R.layout.dialog_negative_positive_yellow,null);
    }

    private void setViewComponent(){
        this.negative = view.findViewById(R.id.dialogNegativeButton);
        this.positive = view.findViewById(R.id.dialogPositiveButton);
        positive.setText(dialogData.getPositive());
        this.message = view.findViewById(R.id.dialogAskMessage);
        message.setText(dialogData.getMessage());
    }

    public AlertDialog createDialog(){
        builder.setView(this.view);
        setViewComponent();
        this.dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    //message 수정시 사용할 코드
    public void setMessageText(String newMessage){
        this.message.setText(newMessage);
    }

    public TextView getNegative() {
        return negative;
    }

    public TextView getPositive() {
        return positive;
    }

    public TextView getMessage() {
        return message;
    }
}
