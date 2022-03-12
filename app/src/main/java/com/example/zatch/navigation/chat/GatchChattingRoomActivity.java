package com.example.zatch.navigation.chat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;
import android.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.zatch.PNDialogMessage;
import com.example.zatch.PositiveNegativeDialog;
import com.example.zatch.ServiceType;
import com.example.zatch.databinding.ActivityGatchChattingRoomBinding;
import com.example.zatch.navigation.chat.data.ChatItemData;

import java.util.ArrayList;

public class GatchChattingRoomActivity extends AppCompatActivity {

    private ChattingMessageAdapter adapter;
    private ActivityGatchChattingRoomBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGatchChattingRoomBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.writeChattingMessageGatch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.writeChattingMessageGatch.getText().toString().equals(""))
                    binding.sendChatButtonGatch.setEnabled(false);
                else
                    binding.sendChatButtonGatch.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.gatchRoomBackButton.setOnClickListener(v->{
            finish();
        });
        binding.gatchRefuseButton.setOnClickListener(v->{
            showNegativePositiveDialog(PNDialogMessage.GatchRefuse);
        });
        binding.gatchAcceptButton.setOnClickListener(v->{
            showNegativePositiveDialog(PNDialogMessage.GatchAccept);
        });
        binding.sendChatButtonGatch.setOnClickListener(v->{
            sendMessage();
        });

        ArrayList<ChatItemData> data = new ArrayList<>();
        adapter = new ChattingMessageAdapter(ServiceType.Gatch,data,this);
        binding.gatchChatRecycler.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getBaseContext());
        binding.gatchChatRecycler.setLayoutManager(manager);

    }

    void showNegativePositiveDialog(PNDialogMessage type){

        PositiveNegativeDialog dialogClass = new PositiveNegativeDialog(GatchChattingRoomActivity.this, ServiceType.Gatch, type);
        AlertDialog dialog = dialogClass.createDialog();
        dialogClass.getNegative().setOnClickListener(v->{
            dialog.dismiss();
        });
        dialogClass.getPositive().setOnClickListener(v->{
            dialog.dismiss();
        });
        dialog.show();
    }

    void sendMessage(){
        //chatting room에 message send & 입력창 초기화
        binding.writeChattingMessageGatch.setText("");
    }
}
