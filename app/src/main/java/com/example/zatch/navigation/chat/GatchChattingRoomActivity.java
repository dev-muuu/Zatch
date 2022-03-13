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
import com.example.zatch.databinding.LayoutGatchTutorialBinding;
import com.example.zatch.navigation.chat.data.ChatItemData;
import com.example.zatch.navigation.chat.data.GatchDepositData;

import java.util.ArrayList;

public class GatchChattingRoomActivity extends AppCompatActivity implements DepositBottomSheet.BottomSheetDepositListener{

    private ActivityGatchChattingRoomBinding binding;
    private LayoutGatchTutorialBinding tutorialBinding;
    private boolean tutorialRegister = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGatchChattingRoomBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        tutorialBinding = binding.gatchTutorial;

        //tutorial visibility init
        if(tutorialRegister)
            binding.gatchTutorial.getRoot().setVisibility(View.VISIBLE);
        else
            binding.gatchTutorial.getRoot().setVisibility(View.GONE);

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

        binding.gatchRoomBackButton.setOnClickListener(v-> finish());
        binding.gatchRefuseButton.setOnClickListener(v-> showNegativePositiveDialog(PNDialogMessage.GatchRefuse));
        binding.gatchAcceptButton.setOnClickListener(v-> showNegativePositiveDialog(PNDialogMessage.GatchAccept));
        binding.sendChatButtonGatch.setOnClickListener(v-> sendMessage());
        binding.chattingMoreEtcButtonGatch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                binding.chattingMoreEtcLayout.setVisibility(View.VISIBLE);
            else
                binding.chattingMoreEtcLayout.setVisibility(View.GONE);
        });
        //more etc layout 내 버튼 클릭
        binding.moreEtcDeposit.setOnClickListener(v -> {
            showDepositBottomSheet();
        });
        ArrayList<ChatItemData> data = new ArrayList<>();
        binding.gatchChatRecycler.setAdapter(new ChattingMessageAdapter(ServiceType.Gatch,data,this));
        binding.gatchChatRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        //tutorial ui setting
        tutorialBinding.checkBox4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                tutorialBinding.tutorialExplainText.setVisibility(View.VISIBLE);
                tutorialBinding.tutorialManagerText.setVisibility(View.VISIBLE);
            }
            else {
                tutorialBinding.tutorialExplainText.setVisibility(View.GONE);
                tutorialBinding.tutorialManagerText.setVisibility(View.GONE);
            }
        });

    }

    private void showDepositBottomSheet(){
        DepositBottomSheet bottomSheet = new DepositBottomSheet(this);
        bottomSheet.show(getSupportFragmentManager(),null);
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

    @Override
    public void finishBottomSheet(GatchDepositData data) {
        //data setting
        tutorialBinding.tutorialManagerText.setText(data.getAccountOwner());
        tutorialBinding.tutorialExplainText.setText(data.getMoreInfo());
        tutorialBinding.tutorialBankName.setText(data.getBankName());
        tutorialBinding.pricePerson.setText(data.getPricePerPeson());
        tutorialBinding.tutorialBankAccount.setText(data.getBankAccount());
        tutorialRegister = true;
        binding.gatchTutorial.getRoot().setVisibility(View.VISIBLE);
        binding.chattingMoreEtcButtonGatch.setChecked(false);
    }
}
