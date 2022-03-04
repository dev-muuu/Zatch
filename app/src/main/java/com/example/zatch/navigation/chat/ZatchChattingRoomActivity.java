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
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.navigation.chat.data.ChatItemData;
import com.example.zatch.navigation.chat.data.ChatType;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ZatchChattingRoomActivity extends AppCompatActivity implements MakeMeetingBottomSheet.MakeMeetingBottomSheetListener,
        RoomEtcBottomSheet.RoomEtcBottomSheetListener {

    private TextView isMakeReservation;
    private EditText chattingMessage;
    private RecyclerView chattingRecycler;
    private ChattingMessageAdapter adapter;
    private CheckBox moreEtcButton;
    private ConstraintLayout moreEtcLayout;
    private Button sendButton;
    private ArrayList<ChatItemData> chattingData;

    private final int RequestCodeGallery = 100;
    private final int RequestCodeCamera = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zatch_chatting_room);
        getSupportActionBar().hide();

        findViewById(R.id.sendChatButtonZatch).setOnClickListener(onClickListener);
        findViewById(R.id.moreEtcMakeMeeting).setOnClickListener(onClickListener);
        findViewById(R.id.roomEtcButton).setOnClickListener(onClickListener);
        findViewById(R.id.chattingMoreEtcButton).setOnClickListener(onClickListener);
        findViewById(R.id.moreEtcCamera).setOnClickListener(onClickListener);
        findViewById(R.id.moreEtcGallery).setOnClickListener(onClickListener);

        chattingMessage = findViewById(R.id.writeChattingMessage);
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

        isMakeReservation = findViewById(R.id.textView83);
        chattingRecycler = findViewById(R.id.chattingRecycler);
        moreEtcButton = findViewById(R.id.chattingMoreEtcButton);
        moreEtcLayout = findViewById(R.id.chattingMoreEtcLayout);
        sendButton = findViewById(R.id.sendChatButtonZatch);

        //chatting recycler
        chattingData = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getBaseContext());
        adapter = new ChattingMessageAdapter(Chat.Zatch,chattingData,getBaseContext());
        chattingRecycler.setLayoutManager(layoutManager);
        chattingRecycler.setAdapter(adapter);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.chattingMoreEtcButton:
                    if(moreEtcButton.isChecked())
                        moreEtcLayout.setVisibility(View.VISIBLE);
                    else
                        moreEtcLayout.setVisibility(View.GONE);
                    break;
                case R.id.sendChatButtonZatch:
                    sendMessage();
                    break;
                case R.id.moreEtcMakeMeeting:
                    makeReservationBottomSheet();
                    break;
                case R.id.roomEtcButton:
                    makeRoomEtcBottomSheet();
                    break;
                case R.id.moreEtcCamera:
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, RequestCodeCamera);
                    break;
                case R.id.moreEtcGallery:
                    permissionCheck();
                    break;
            }
        }
    };

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
        adapter.addItem(new ChatItemData("숑",String.valueOf(imageUri),System.currentTimeMillis(),null, ChatType.RIGHT_IMAGE));
        adapter.notifyDataSetChanged();
        chattingRecycler.scrollToPosition(chattingData.size()-1);
    }

    void makeRoomEtcBottomSheet(){
        RoomEtcBottomSheet bottomSheet = new RoomEtcBottomSheet(this);
        BottomSheetDialogFragment dialogFragment = bottomSheet;
        dialogFragment.show(getSupportFragmentManager(),null);
    }

    void sendMessage(){
        //chatting room에 message send & 입력창 초기화
        adapter.addItem(new ChatItemData("숑",chattingMessage.getText().toString(),System.currentTimeMillis(),null, ChatType.RIGHT_MESSAGE));
        chattingMessage.setText("");
        chattingRecycler.scrollToPosition(chattingData.size()-1);
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
                break;
            case Declaration:
                break;
            case Exit:
                //리스트에서 채팅방 삭제 코드 추가 필요
                finish();
                break;
        }
    }

    @Override
    public void finishBottomSheet(boolean isFinish) {
        if(isFinish)
            isMakeReservation.setText("예약완료");
    }
}
