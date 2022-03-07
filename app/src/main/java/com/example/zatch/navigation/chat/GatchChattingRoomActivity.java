package com.example.zatch.navigation.chat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.PNDialogMessage;
import com.example.zatch.R;
import com.example.zatch.ServiceType;
import com.example.zatch.navigation.chat.data.ChatItemData;

import java.util.ArrayList;

public class GatchChattingRoomActivity extends AppCompatActivity {

    private ChattingMessageAdapter adapter;
    private EditText chattingMessage;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button sendButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gatch_chatting_room);
        getSupportActionBar().hide();

        sendButton = findViewById(R.id.sendChatButtonGatch);
        chattingMessage = findViewById(R.id.writeChattingMessageGatch);
        chattingMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(chattingMessage.getText().toString().equals(""))
                    sendButton.setEnabled(false);
                else
                    sendButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        findViewById(R.id.gatchRoomBackButton).setOnClickListener(onClickListener);
        findViewById(R.id.gatchRefuseButton).setOnClickListener(onClickListener);
        findViewById(R.id.gatchAcceptButton).setOnClickListener(onClickListener);
        findViewById(R.id.gatchRequestCancelButton).setOnClickListener(onClickListener);
        findViewById(R.id.chattingMoreEtcButtonGatch).setOnClickListener(onClickListener);
        findViewById(R.id.sendChatButtonGatch).setOnClickListener(onClickListener);

        ArrayList<ChatItemData> data = new ArrayList<>();
        RecyclerView chattingRecycler = findViewById(R.id.gatchChatRecycler);
        adapter = new ChattingMessageAdapter(ServiceType.Gatch,data,getBaseContext());
        chattingRecycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext());
        chattingRecycler.setLayoutManager(manager);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.gatchRoomBackButton:
                    finish();
                    break;
                case R.id.gatchRefuseButton:
                    showNegativePositiveDialog(PNDialogMessage.GatchRefuse);
                    break;
                case R.id.gatchAcceptButton:
                    showNegativePositiveDialog(PNDialogMessage.GatchAccept);
                    //dialog
                    break;
                case R.id.gatchRequestCancelButton:
                    break;
                case R.id.chattingMoreEtcButtonGatch:
                    break;
                case R.id.sendChatButtonGatch:
                    sendMessage();
                    break;
            }
        }
    };

    void showNegativePositiveDialog(PNDialogMessage type){

        builder = new AlertDialog.Builder(GatchChattingRoomActivity.this);
        View view = LayoutInflater.from(GatchChattingRoomActivity.this).inflate(R.layout.dialog_negative_positive_yellow,null);
        builder.setView(view);
        TextView message = view.findViewById(R.id.dialogAskMessage);
        message.setText(type.getMessage());
        TextView positive = view.findViewById(R.id.dialogPositiveButton);
        TextView negative = view.findViewById(R.id.dialogNegativeButton);
        positive.setText(type.getPositive());
        dialog = builder.create();
        positive.setOnClickListener(v -> {
            dialog.dismiss();
        });
        negative.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    void sendMessage(){
        //chatting room에 message send & 입력창 초기화
//        adapter.addItem(chattingMessage.getText().toString());
        chattingMessage.setText("");
    }
}
