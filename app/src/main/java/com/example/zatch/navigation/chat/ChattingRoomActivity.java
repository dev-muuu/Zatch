package com.example.zatch.navigation.chat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.zatch.ImageSelectActivity;
import com.example.zatch.PNDialogMessage;
import com.example.zatch.PositiveNegativeDialog;
import com.example.zatch.R;
import com.example.zatch.ServiceType;
import com.example.zatch.databinding.ActivityChattingRoomBinding;
import com.example.zatch.databinding.DialogGalleryAccessRefuseBinding;
import com.example.zatch.databinding.DrawerLayoutChattingRoomBinding;
import com.example.zatch.databinding.LayoutChattingRoomMoreLayoutBottomBinding;
import com.example.zatch.databinding.LayoutChattingRoomTopBarBinding;
import com.example.zatch.databinding.LayoutGatchTutorialBinding;
import com.example.zatch.navigation.chat.data.ChatItemData;
import com.example.zatch.navigation.chat.data.ChatViewType;
import com.example.zatch.navigation.chat.data.GatchDepositData;
import com.example.zatch.navigation.chat.data.MeetingData;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ChattingRoomActivity extends AppCompatActivity implements MakeMeetingBottomSheet.MakeMeetingBottomSheetListener,
        RoomEtcBottomSheet.RoomEtcBottomSheetListener, DepositBottomSheet.BottomSheetDepositListener{

    private ActivityChattingRoomBinding binding;
    private DrawerLayoutChattingRoomBinding drawerBinding;
    private LayoutChattingRoomMoreLayoutBottomBinding bottomLayoutBinding;
    private LayoutGatchTutorialBinding tutorialBinding;
    private LayoutChattingRoomTopBarBinding topBarBinding;

    private ClipboardManager clipboardManager;
    private ArrayList<ChatItemData> chattingData;
    private ChattingMessageAdapter adapter;

    private boolean tutorialRegister = false; //잠시 true로 변경시켜놓음
    private boolean isMeetingMade = false;
    private Context context = ChattingRoomActivity.this;
    private MeetingData meetingData;
    private ServiceType serviceType;

    private final int RequestCodeGallery = 100;
    private final int RequestCodeCamera = 200;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawerBinding = DrawerLayoutChattingRoomBinding.inflate(getLayoutInflater());
        setContentView(drawerBinding.getRoot());

        binding = drawerBinding.activityChattingLayout;
        bottomLayoutBinding = binding.chattingMoreDataLayout;
        topBarBinding = binding.topBarLayout;
        tutorialBinding = binding.gatchTutorialLayout;

        serviceType = (ServiceType) getIntent().getSerializableExtra("service");

        if(serviceType == ServiceType.Gatch)
            initServiceType();

        //clipboard
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        binding.writeChattingMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.writeChattingMessage.getText().toString().equals(""))
                    binding.sendChatButton.setEnabled(false);
                else
                    binding.sendChatButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //chatting recycler
        chattingData = new ArrayList<>();
        adapter = new ChattingMessageAdapter(serviceType,chattingData,this);
        binding.chattingRecycler.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.chattingRecycler.setAdapter(adapter);

        //tutorial visibility init
        if(tutorialRegister) {
            binding.gatchTutorialLayout.getRoot().setVisibility(View.VISIBLE);
            uploadTutorial(new GatchDepositData("신한","0101","ssooya","2000","ㅋ"));
        }else
            tutorialBinding.getRoot().setVisibility(View.GONE);

        //chatting member recycer
        List<Boolean> memberList = new ArrayList<Boolean>(){};
        memberList.add(true);
        memberList.add(false);
        drawerBinding.memberRecyclerview.setLayoutManager(new LinearLayoutManager(context));
        drawerBinding.memberRecyclerview.setAdapter(new ChattingMemberListAdapter(serviceType,memberList,drawerBinding.drawerService,getSupportFragmentManager(),this));

        //clicklistener 구현 메서드
        onClickListenerInit();
    }

    private void onClickListenerInit(){

        //about topbar layout
        topBarBinding.ChatBackButton.setOnClickListener(v->{
            finish();
        });

        topBarBinding.roomEtcButton.setOnClickListener(v->{
            drawerBinding.drawerService.openDrawer(Gravity.RIGHT);
        });

        topBarBinding.gatchRefuseButton.setOnClickListener(v->
                showNegativePositiveDialog(PNDialogMessage.GatchRefuse)
        );

        topBarBinding.gatchAcceptButton.setOnClickListener(v->
                showNegativePositiveDialog(PNDialogMessage.GatchAccept)
        );

        //about message layout
        binding.chattingMoreEtcButton.setOnClickListener(v-> {
            if(binding.chattingMoreEtcButton.isChecked())
                bottomLayoutBinding.chattingMoreLayout.setVisibility(View.VISIBLE);
            else
                bottomLayoutBinding.chattingMoreLayout.setVisibility(View.GONE);
        });

        binding.sendChatButton.setOnClickListener(v->{
            sendMessage();
        });

        //about bottom layout
        bottomLayoutBinding.moreEtcCamera.setOnClickListener(v->{
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, RequestCodeCamera);
        });

        bottomLayoutBinding.moreEtcGallery.setOnClickListener(v->{
            permissionCheck();
        });

        bottomLayoutBinding.moreEtcMakeMeeting.setOnClickListener(v->{
            makeReservationBottomSheet();
        });

        bottomLayoutBinding.moreEtcDeposit.setOnClickListener(v -> {
            showDepositBottomSheet();
        });

        //about drawer layout
        drawerBinding.exitChattingRoom.setOnClickListener(v->{
            drawerBinding.drawerService.closeDrawer(Gravity.RIGHT);
            showNegativePositiveDialog(PNDialogMessage.Exit);
        });

        //tutorial layout
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

    private void initServiceType(){
            // top bar
            topBarBinding.townText.setTextColor(getColor(R.color.zatch_deepyellow));
            topBarBinding.isReservationMade.setBackground(getDrawable(R.drawable.text_background_deep_yellow_radius_10));
            //zatchInfoLayout
            binding.zatchInfoLayout.getRoot().setVisibility(View.GONE);
            //input chatting layout
            binding.chattingMoreEtcButton.setBackground(getDrawable(R.drawable.selector_chat_show_etc_layout_yellow));
            binding.sendChatButton.setBackground(getDrawable(R.drawable.selector_chat_send_yellow));
            //bottom layout
            bottomLayoutBinding.depositLayout.setVisibility(View.VISIBLE);
            //drawer layout
    }

    /*
     * 갤러리, 카메라 관련 코드
     */
    private void permissionCheck() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //이미 권한 부여됐을 때 사용
            openGallery();
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            //이미 권한 거부된 상태일 때 사용
            sendDialogForGalleryAccess();
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
                else
                    sendDialogForGalleryAccess();   //새로 권한 요청했는데 거부 -> sentDialog 실행됨
            });

    public void openGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, RequestCodeGallery);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case RequestCodeCamera:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(ChattingRoomActivity.this.getContentResolver(), bitmap, "Title", null);
                    sendImageMessage(Uri.parse(path));
                    break;
                case RequestCodeGallery:
                    //서버 연결시, uploadImage()통해 image파일 서버에 전달 필요.
                    Uri imageUri = data.getData();
                    Intent intent = new Intent(ChattingRoomActivity.this, ImageSelectActivity.class);
                    intent.putExtra("imageUri",imageUri);
                    mGetContent.launch(intent);
                    break;
            }
        }
    }

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK)
                    sendImageMessage(result.getData().getParcelableExtra("imageResult"));
                else if(result.getResultCode() == RESULT_CANCELED){
                    openGallery();
                }
    });

    private void sendDialogForGalleryAccess(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_gallery_access_refuse,null);
        builder.setView(view);
        DialogGalleryAccessRefuseBinding binding = DialogGalleryAccessRefuseBinding.bind(view);
        AlertDialog dialog = builder.create();
        binding.cancel.setOnClickListener(v->{
            dialog.dismiss();
        });
        binding.goSetting.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(Settings.ACTION_APPLICATION_SETTINGS);
            startActivity(intent);
        });
        dialog.show();
    }

    /*
     * chatting message 전송 관련
     */
    private void sendImageMessage(Uri imageUri){
        adapter.addItem(new ChatItemData("숑",String.valueOf(imageUri),System.currentTimeMillis(),null, ChatViewType.RIGHT_IMAGE));
        adapter.notifyDataSetChanged();
        binding.chattingRecycler.scrollToPosition(chattingData.size()-1);
    }

    void sendMessage(){
        //chatting room에 message send & 입력창 초기화
        adapter.addItem(new ChatItemData("숑",binding.writeChattingMessage.getText().toString(), System.currentTimeMillis(),null, ChatViewType.RIGHT_MESSAGE));
        binding.writeChattingMessage.setText("");
        binding.chattingRecycler.scrollToPosition(chattingData.size()-1);
    }

    /*
    다이얼로그 관련
     */
    private void showNegativePositiveDialog(PNDialogMessage type){
        PositiveNegativeDialog dialogClass = new PositiveNegativeDialog(ChattingRoomActivity.this, serviceType, type);
        AlertDialog dialog = dialogClass.createDialog();
        dialogClass.getNegative().setOnClickListener(v->{
            dialog.dismiss();
        });
        dialogClass.getPositive().setOnClickListener(v->{
            positiveActionByType(type, dialog);
        });

        dialog.show();
    }

    private void positiveActionByType(@NotNull PNDialogMessage type, AlertDialog dialog){
        switch (type){
            case Exit:
                dialog.dismiss();
                finish();
                break;
        }
    }

    /*
    bottom sheet 관련
     */
    private void makeReservationBottomSheet(){
        MakeMeetingBottomSheet bottomSheet = new MakeMeetingBottomSheet(this, isMeetingMade, serviceType);
        BottomSheetDialogFragment dialogFragment = bottomSheet;
        dialogFragment.show(getSupportFragmentManager(),null);
    }

    private void showDepositBottomSheet(){
        DepositBottomSheet bottomSheet = new DepositBottomSheet(this, tutorialRegister);
        bottomSheet.show(getSupportFragmentManager(),null);
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
            case Out:
                //리스트에서 채팅방 삭제 코드 추가 필요
                finish();
                return;
        }
    }

    @Override
    public void finishBottomSheet(MeetingData meetingData) {
        topBarBinding.isReservationMade.setVisibility(View.VISIBLE);
        this.isMeetingMade = true;
        this.meetingData = meetingData;
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
        tutorialBinding.getRoot().setVisibility(View.VISIBLE);
        binding.chattingMoreEtcButton.setChecked(false);
    }

    private void registerBankDataClipBoard(){
        String bankData = String.format("%s %s",tutorialBinding.tutorialBankName.getText().toString(),tutorialBinding.tutorialBankAccount.getText().toString());
        ClipData clipData = ClipData.newPlainText("accountNumber",bankData);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "계좌번호가 클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show();
    }
}
