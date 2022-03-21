package com.example.zatch.navigation.chat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import android.app.AlertDialog;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.zatch.PNDialogMessage;
import com.example.zatch.PositiveNegativeDialog;
import com.example.zatch.ServiceType;
import com.example.zatch.databinding.ActivityGatchChattingRoomBinding;
import com.example.zatch.databinding.DrawerLayoutChattingRommtGatchBinding;
import com.example.zatch.databinding.LayoutGatchTutorialBinding;
import com.example.zatch.navigation.chat.data.ChatItemData;
import com.example.zatch.navigation.chat.data.ChatViewType;
import com.example.zatch.navigation.chat.data.GatchDepositData;
import com.example.zatch.navigation.chat.data.MeetingData;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class GatchChattingRoomActivity extends AppCompatActivity
        implements DepositBottomSheet.BottomSheetDepositListener, MakeMeetingBottomSheet.MakeMeetingBottomSheetListener {

    private ActivityGatchChattingRoomBinding binding;
    private LayoutGatchTutorialBinding tutorialBinding;
    private DrawerLayoutChattingRommtGatchBinding drawerBinding;
    private boolean tutorialRegister = false; //잠시 true로 변경시켜놓음
    private ArrayList<ChatItemData> data;
    private ChattingMessageAdapter adapter;
    private ClipboardManager clipboardManager;

    private final int RequestCodeGallery = 100;
    private final int RequestCodeCamera = 200;

    private boolean isMeetingMade = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerBinding = DrawerLayoutChattingRommtGatchBinding.inflate(getLayoutInflater());
        setContentView(drawerBinding.getRoot());

        binding = drawerBinding.activityChattingLayout;
        tutorialBinding = binding.gatchTutorial;

        //tutorial visibility init
        if(tutorialRegister) {
            binding.gatchTutorial.getRoot().setVisibility(View.VISIBLE);
            uploadTutorial(new GatchDepositData("신한","0101","ssooya","2000","ㅋ"));
        }else
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

        //recyclerview 설정
        data = new ArrayList<>();
        adapter = new ChattingMessageAdapter(ServiceType.Gatch,data,this);
        binding.gatchChatRecycler.setAdapter(adapter);
        binding.gatchChatRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        //clipboard
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        onClickListenerInit();

        //drawer layout
        List<Boolean> memberList = new ArrayList<Boolean>(){};
        memberList.add(true);
        memberList.add(false);
        memberList.add(false);
        memberList.add(false);
        drawerBinding.memberRecyclerview.setLayoutManager(new LinearLayoutManager(GatchChattingRoomActivity.this));
        drawerBinding.memberRecyclerview.setAdapter(new ChattingMemberListAdapter(memberList,getSupportFragmentManager()));

    }

    private void onClickListenerInit(){
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
        binding.moreEtcGallery.setOnClickListener(v-> permissionCheck());
        binding.moreEtcCamera.setOnClickListener(v-> {
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, RequestCodeCamera);
        });
        binding.moreEtcMakeMeeting.setOnClickListener(v->{
            MakeMeetingBottomSheet bottomSheet = new MakeMeetingBottomSheet(this,isMeetingMade,ServiceType.Gatch);
            BottomSheetDialogFragment dialogFragment = bottomSheet;
            dialogFragment.show(getSupportFragmentManager(),null);
        });
        binding.etcButton.setOnClickListener(v->{
            drawerBinding.drawerGatch.openDrawer(Gravity.RIGHT);
        });

        drawerBinding.exitRoom.setOnClickListener(v->
            showNegativePositiveDialog(PNDialogMessage.Exit));
    }

    private void showDepositBottomSheet(){
        DepositBottomSheet bottomSheet = new DepositBottomSheet(this, tutorialRegister);
        bottomSheet.show(getSupportFragmentManager(),null);
    }

    void showNegativePositiveDialog(PNDialogMessage type){
        PositiveNegativeDialog dialogClass = new PositiveNegativeDialog(GatchChattingRoomActivity.this, ServiceType.Gatch, type);
        AlertDialog dialog = dialogClass.createDialog();
        dialogClass.getNegative().setOnClickListener(v->{
            dialog.dismiss();
        });
        dialogClass.getPositive().setOnClickListener(v->{
            positiveActionByType(type, dialog);
        });

        dialog.show();
    }

    private void positiveActionByType(PNDialogMessage type, AlertDialog dialog){
        switch (type){
            case Exit:
                dialog.dismiss();
                finish();
                break;
        }
    }

    private void permissionCheck(){

        if (ContextCompat.checkSelfPermission( getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //이미 권한 부여됐을 때 사용
            openGallery();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            //이미 권한 거부된 상태일 때 사용
//            sentDialog("위치 권한을 허용하셔야 동네 인증이 가능합니다.");
        } else {
            //새로 권한 요청시 실행. 이후 callback 결과 실행됨
            requestPermissionLauncher.launch(
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted)
                    openGallery();
//                else
//                    sentDialog("위치 권한을 허용하셔야 동네 인증이 가능합니다.");   //새로 권한 요청했는데 거부 -> sentDialog 실행됨
            });

    private void openGallery(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, RequestCodeGallery);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null ) {
            switch(requestCode) {
                case RequestCodeCamera:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(GatchChattingRoomActivity.this.getContentResolver(), bitmap, "Title", null);
                    sendImageMessage(Uri.parse(path));
                    break;
                case RequestCodeGallery:
                    //서버 연결시, uploadImage()통해 image파일 서버에 전달 필요.
                    Uri imageUri = data.getData();
                    sendImageMessage(imageUri);
                    break;
            }
        }
    }

    void sendImageMessage(Uri imageUri){
        adapter.addItem(new ChatItemData("숑",String.valueOf(imageUri),System.currentTimeMillis(),null, ChatViewType.RIGHT_IMAGE));
        adapter.notifyDataSetChanged();
        binding.gatchChatRecycler.scrollToPosition(data.size()-1);
    }

    void sendMessage(){
        //chatting room에 message send & 입력창 초기화
        adapter.addItem(new ChatItemData("ssooya",binding.writeChattingMessageGatch.getText().toString()
                ,System.currentTimeMillis(),null, ChatViewType.RIGHT_MESSAGE));
        binding.writeChattingMessageGatch.setText("");
        binding.gatchChatRecycler.scrollToPosition(data.size()-1);
    }

    @Override
    public void uploadTutorial(GatchDepositData data) {
        //data setting
        tutorialBinding.tutorialManagerText.setText(data.getAccountOwner());
        tutorialBinding.tutorialExplainText.setText(data.getMoreInfo());
        tutorialBinding.tutorialBankName.setText(data.getBankName());
        tutorialBinding.pricePerson.setText(data.getPricePerPeson());
        tutorialBinding.tutorialBankAccount.setText(data.getBankAccount());
        
        tutorialBinding.tutorialBankAccount.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        tutorialBinding.tutorialBankName.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        
        tutorialBinding.tutorialBankAccount.setOnClickListener(v -> {
            registerBankDataClipBoard();
        });
        tutorialBinding.tutorialBankName.setOnClickListener(v -> {
            registerBankDataClipBoard();
        });
        
        tutorialRegister = true;
        binding.gatchTutorial.getRoot().setVisibility(View.VISIBLE);
        binding.chattingMoreEtcButtonGatch.setChecked(false);
    }
    
    private void registerBankDataClipBoard(){
        String bankData = String.format("%s %s",tutorialBinding.tutorialBankName.getText().toString(),tutorialBinding.tutorialBankAccount.getText().toString());
        ClipData clipData = ClipData.newPlainText("accountNumber",bankData);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "계좌번호가 클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finishBottomSheet(MeetingData meetingData) {

    }
}
