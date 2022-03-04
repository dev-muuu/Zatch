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

import com.example.zatch.R;
import com.example.zatch.navigation.chat.data.ChatItemData;

import java.util.ArrayList;

public class GatchChattingRoomActivity extends AppCompatActivity {

    private ChattingMessageAdapter adapter;
    private EditText chattingMessage;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Button sendButton;

    enum GatchChatMessage{

        Accept("가치 채팅방으로 이동합니다.","네"),
        Refuse("가치 요청을 거절하시겠습니까?","네"),
        Exit("채팅방을 나가시겠습니까?\n" +
                "채팅방을 나가면 채팅 내역은 복구되지 않습니다.","네, 확인했습니다.");

        private final String message;
        private final String positive;

        GatchChatMessage(String message, String positive) {
            this.message = message;
            this.positive = positive;
        }

        public String getMessage(){
            return this.message;
        }

        public String getPositive(){
            return this.positive;
        }
    }

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
        adapter = new ChattingMessageAdapter(Chat.Gatch,data,getBaseContext());
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
                    showNegativePositiveDialog(GatchChatMessage.Refuse);
                    break;
                case R.id.gatchAcceptButton:
                    showNegativePositiveDialog(GatchChatMessage.Accept);
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

    void showNegativePositiveDialog(GatchChatMessage type){
        builder = new AlertDialog.Builder(GatchChattingRoomActivity.this);
        View view = LayoutInflater.from(GatchChattingRoomActivity.this).inflate(R.layout.dialog_negative_positive,null);
        builder.setView(view);
        TextView message = view.findViewById(R.id.dialogAskMessage);
        message.setText(type.getMessage());
        TextView positive = view.findViewById(R.id.dialogPositiveButton);
        TextView negative = view.findViewById(R.id.dialogNegativeButton);
        positive.setText(type.getPositive());
        positive.setBackground(getResources().getDrawable(R.drawable.text_background_deep_yellow));
        dialog = builder.create();
        positive.setOnClickListener(v -> {
            dialog.dismiss();
        });
        negative.setTextColor(getResources().getColor(R.color.zatch_deepyellow));
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
