package com.example.zatch.zatch_register;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.zatch.ImageSelectActivity;
import com.example.zatch.PNDialogMessage;
import com.example.zatch.PositiveNegativeDialog;
import com.example.zatch.R;
import com.example.zatch.ReturnPx;
import com.example.zatch.ServiceType;
import com.example.zatch.databinding.FragmentZatchRegisterProductInfoBinding;
import com.example.zatch.navigation.chat.ChattingRoomActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class ZatchProductInfoFragment extends Fragment implements DatePickerFragment.DatePickerDialogListener {

    //TODO: 코드 전체적으로 수정.. 맘에 안들어; 이미지 관련해서 recyclerview로 변경하고, 유효성 검사도 bool값 사용하는 걸로 바꿔보기

    String messageText[];
    private ReturnPx returnPx;

    private FragmentZatchRegisterProductInfoBinding binding;

    final int PERMISSION_REQUEST_CODE = 1;
    final int GALLERY_ACCESS = 1;
    final int REQUEST_IMAGE_CAPTURE = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentZatchRegisterProductInfoBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding.moreInfoInputLayout.setVisibility(View.INVISIBLE);

        returnPx = new ReturnPx(getActivity());

        ArrayAdapter<CharSequence> spinnerAdapter
                = ArrayAdapter.createFromResource(getContext(), R.array.gatch_category, R.layout.item_spinner_category);

        binding.productCategorySpinner.setAdapter(spinnerAdapter);
        binding.productCategorySpinner.setOnItemSelectedListener(SpinnerListener);

        ArrayAdapter<CharSequence> spinnerAdapter2
                = ArrayAdapter.createFromResource(getContext(), R.array.product_count_list, R.layout.support_simple_spinner_dropdown_item);

        binding.countSpinner.setAdapter(spinnerAdapter2);
        binding.countSpinner.setOnItemSelectedListener(SpinnerListener);


        //dialog message 저장 배열
        messageText = new String[]{"카테고리를 입력해주세요.", "상품 이름을 입력해주세요.", "이미지를 최소 1장 이상 첨부해주세요.",
                "구매일자를 입력해주세요.", "유통기한을 입력해주세요."};

        setOnClickListener();

    }

    private void showDatePickerDialog(String title) {

        DatePickerFragment dia = DatePickerFragment.newInstance(title);
        dia.setDatePickerDialogListener(this);
        DialogFragment dialog = dia;
        dialog.show(getFragmentManager(),"dialog");
    }

    @Override
    public void onDialogFinish(int year, int month, int date, String title) {

        if (title.equals("구매일자")) {
            binding.buyYearText.setText(String.valueOf(year));
            binding.buyMonthText.setText(String.valueOf(month));
            binding.buyDayText.setText(String.valueOf(date));
        }
        else{
            binding.endYearText.setText(String.valueOf(year));
            binding.endMonthText.setText(String.valueOf(month));
            binding.endDayText.setText(String.valueOf(date));

        }
    }

    AdapterView.OnItemSelectedListener SpinnerListener = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };


    private void setOnClickListener(){

        binding.imageAddButton.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_camera_gallery,null);
            builder.setView(view);
            AlertDialog dialog = builder.create();
            view.findViewById(R.id.galleryButtonText).setOnClickListener(a->{
                if(galleryPermissionCheck()) {
                    clickImageAddButton("gallery");
                    dialog.dismiss();

                }
            });
            view.findViewById(R.id.cameraButtonText).setOnClickListener(a->{
                clickImageAddButton("camera");
                dialog.dismiss();
            });
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        binding.nextButton.setOnClickListener(v->{
            moveViewPagerFragment();
        });

        binding.moreInfoDownButton.setOnClickListener(v->{
            showInfoLayout();
        });

        binding.datePickerContainer.setOnClickListener(v->{
            showDatePickerDialog("구매일자");
        });

        binding.datePickerContainer2.setOnClickListener(v->{
            showDatePickerDialog("유통기한");
        });
    }

    void showInfoLayout(){
        if (binding.moreInfoInputLayout.getVisibility() == View.VISIBLE) {
            binding.moreInfoInputLayout.setVisibility(View.INVISIBLE);
            binding.moreInfoInputLayout.animate().setDuration(200);
        } else {
            binding.moreInfoInputLayout.setVisibility(View.VISIBLE);
            binding.moreInfoInputLayout.animate().setDuration(200);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                clickImageAddButton("gallery");
            }  else {
                Toast.makeText(getContext(), "권한허용을 거부하셨습니다.이미지를 불러오지 못함.", Toast.LENGTH_SHORT).show();
            }

        }

    }

    boolean galleryPermissionCheck(){
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            return true;

        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GALLERY_ACCESS);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GALLERY_ACCESS);
        }
        return false;
    }

    //TODO: 굳이 하나의 함수에서 gallery, camera 둘다 처리할 필요가 없는 듯 하다.. 객체 동일 검사도 의미없고 -> 없애고 함수 따로 구현하는 걸로 수정, 추가로 gallery access 방식도 바꾸기

    void clickImageAddButton(String type){
        if(type.equals("gallery")) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, GALLERY_ACCESS);
        }else{
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//            }
        }
    }

    ActivityResultLauncher<Intent> mGetContent = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK)
                    imageAddToStack(result.getData().getParcelableExtra("imageResult"));
                else if(result.getResultCode() == RESULT_CANCELED){
                    clickImageAddButton("gallery");
                }
            });

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK && data != null ) {
            switch(requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", null);
                    imageAddToStack(Uri.parse(path));
                    break;

                case GALLERY_ACCESS:
                    Uri imageUri = data.getData();
                    Intent intent = new Intent(getContext(), ImageSelectActivity.class);
                    intent.putExtra("imageUri",imageUri);
                    mGetContent.launch(intent);
                    break;
            }

        }
    }

    List<Uri> imageAddList = new ArrayList(10);

    void imageAddToStack(Uri uri){

        ImageView img = new ImageView(getContext());
        img.setId(imageAddList.size());
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // dp = (int) (84*density + 0.5f);
        int margin_84 = (int)returnPx.returnPx(84);
        int margin_8 = (int) returnPx.returnPx(8);
        int margin_4 = (int) returnPx.returnPx(4);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(margin_84, margin_84);
        layoutParams.leftMargin = margin_8;
        img.setLayoutParams(layoutParams);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    imageDeleteDialogShow(uri, img);
                } catch (IndexOutOfBoundsException e) {
                    imageAddList.clear();
                }
                binding.imageCountText.setText(String.valueOf(imageAddList.size()));
                if (imageAddList.size() < 10 && ! binding.imageAddButton.isEnabled())
                    binding.imageAddButton.setEnabled(true);

            }
        });
        binding.zatchImageStack.addView(img);
        Glide.with(getContext()).load(uri).transform(new RoundedCorners(margin_4)).into(img);
        imageAddList.add(uri);
        binding.imageCountText.setText(String.valueOf(imageAddList.size()));

        if (imageAddList.size() == 10)
            binding.imageAddButton.setEnabled(false);
    }

    private void imageDeleteDialogShow(Uri uri, ImageView img){
        PositiveNegativeDialog builder = new PositiveNegativeDialog(getContext(), ServiceType.Zatch,PNDialogMessage.ImageDelete);
        AlertDialog dialog = builder.createDialog();
        builder.getNegative().setOnClickListener(v->{
            dialog.dismiss();
        });
        builder.getPositive().setOnClickListener(v->{
            binding.zatchImageStack.removeView(img);
            imageAddList.remove(imageAddList.indexOf(uri));
            dialog.dismiss();
        });
        dialog.show();
    }

    void moveViewPagerFragment(){
        //for DialogMessage 호출, 인덱스 번호 초기화
        int messageNumber = -1;

        if(binding.productCategorySpinner.getSelectedItemPosition() == 0)
            messageNumber = 0;
        else if(binding.inputProductNameText.getText().toString().equals(""))
            messageNumber = 1;
        else if(imageAddList.size() == 0)
            messageNumber = 2;
        else if(binding.productCategorySpinner.getSelectedItemPosition() == 1){
            if (binding.buyYearText.getText().equals(""))
                messageNumber = 3;
            else if (binding.endYearText.getText().equals(""))
                messageNumber = 4;
        }

        //alert dialog 조건별 코드
        if(messageNumber != -1){
//            builder = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_message_center,null);
            TextView informText = view.findViewById(R.id.dialogMessageText);
            informText.setText(messageText[messageNumber]);
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            Button ok = view.findViewById(R.id.messageOkButton);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        else
            ((ZatchRegisterActivity) getActivity()).moveNextFragment();
    }
}
