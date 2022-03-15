package com.example.zatch.navigation.chat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.zatch.ServiceType;
import com.example.zatch.databinding.ActivityZatchChattingRoomBinding;
import com.example.zatch.databinding.DrawerLayoutChattingRoomZatchBinding;
import com.example.zatch.navigation.chat.data.ChatItemData;
import com.example.zatch.navigation.chat.data.ChatViewType;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ZatchChattingRoomActivity extends AppCompatActivity implements MakeMeetingBottomSheet.MakeMeetingBottomSheetListener,
        RoomEtcBottomSheet.RoomEtcBottomSheetListener {

    private ChattingMessageAdapter adapter;
    private ArrayList<ChatItemData> chattingData;
    private DrawerLayoutChattingRoomZatchBinding binding;
    private ActivityZatchChattingRoomBinding chattingBinding;

    private final int RequestCodeGallery = 100;
    private final int RequestCodeCamera = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = DrawerLayoutChattingRoomZatchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        chattingBinding = binding.activityChattingLayout;

        chattingBinding.writeChattingMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(chattingBinding.writeChattingMessage.getText().toString().equals(""))
                    chattingBinding.sendChatButtonZatch.setEnabled(false);
                else
                    chattingBinding.sendChatButtonZatch.setEnabled(true);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //chatting recycler
        chattingData = new ArrayList<>();
        adapter = new ChattingMessageAdapter(ServiceType.Zatch,chattingData,this);
        chattingBinding.chattingRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        chattingBinding.chattingRecycler.setAdapter(adapter);

        //clicklistener 구현 메서드
        onClickListenerInit();
    }

    private void onClickListenerInit(){
        chattingBinding.chattingMoreEtcButton.setOnClickListener(v->{
            if(chattingBinding.chattingMoreEtcButton.isChecked())
                chattingBinding.chattingMoreEtcLayout.setVisibility(View.VISIBLE);
            else
                chattingBinding.chattingMoreEtcLayout.setVisibility(View.GONE);
        });

        chattingBinding.sendChatButtonZatch.setOnClickListener(v->{
            sendMessage();
        });

        chattingBinding.zatchChatBackButton.setOnClickListener(v->{
            finish();
        });

        chattingBinding.moreEtcMakeMeeting.setOnClickListener(v->{
            makeReservationBottomSheet();
        });

        chattingBinding.roomEtcButton.setOnClickListener(v->{
            binding.drawerZatch.openDrawer(Gravity.RIGHT);
        });

        chattingBinding.moreEtcCamera.setOnClickListener(v->{
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, RequestCodeCamera);
        });

        chattingBinding.moreEtcGallery.setOnClickListener(v->{
            permissionCheck();
        });
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
                    String path = MediaStore.Images.Media.insertImage(ZatchChattingRoomActivity.this.getContentResolver(), bitmap, "Title", null);
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
        chattingBinding.chattingRecycler.scrollToPosition(chattingData.size()-1);
    }

    void makeRoomEtcBottomSheet(){
        RoomEtcBottomSheet bottomSheet = new RoomEtcBottomSheet(this);
        BottomSheetDialogFragment dialogFragment = bottomSheet;
        dialogFragment.show(getSupportFragmentManager(),null);
    }

    void sendMessage(){
        //chatting room에 message send & 입력창 초기화
        adapter.addItem(new ChatItemData("숑",chattingBinding.writeChattingMessage.getText().toString(),
                System.currentTimeMillis(),null, ChatViewType.RIGHT_MESSAGE));
        chattingBinding.writeChattingMessage.setText("");
        chattingBinding.chattingRecycler.scrollToPosition(chattingData.size()-1);
    }

    void makeReservationBottomSheet(){
        MakeMeetingBottomSheet bottomSheet = new MakeMeetingBottomSheet(this);
        BottomSheetDialogFragment dialogFragment = bottomSheet;
        dialogFragment.show(getSupportFragmentManager(),null);
    }

    @Override
    public void finishBottomSheet(EtcFunc etc) {
        switch (etc){
            case Block:
                //리스트에서 채팅방 삭제 코드 추가 필요
                finish();
                return;
            case Declaration:
                return;
            case Exit:
                //리스트에서 채팅방 삭제 코드 추가 필요
                finish();
                return;
        }
    }

    @Override
    public void finishBottomSheet(boolean isFinish) {
        if(isFinish)
            chattingBinding.isReservationMade.setText("예약완료");
    }
}
