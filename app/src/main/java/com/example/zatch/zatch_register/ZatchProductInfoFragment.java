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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.zatch.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ZatchProductInfoFragment extends Fragment implements DatePickerFragment.DatePickerDialogListener {

    View view;
    LinearLayout imageLayout;
    ScrollView infoLayout;
    Spinner categorySpinner, countSpinner;
    CheckBox check1, check2;
    TextView buyYear, buyMonth, buyDate, endYear, endMonth, endDate, imageCountText;
    EditText productName;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    String messageText[];

    float density;

    final int PERMISSION_REQUEST_CODE = 1;
    final int GALLERY_ACCESS = 1;
    final int REQUEST_IMAGE_CAPTURE = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_zatch_register_product_info, container, false);

        view.findViewById(R.id.imageAddButton).setOnClickListener(onClickListener);
        imageLayout = view.findViewById(R.id.zatchImageStack);
        imageCountText = view.findViewById(R.id.imageCountText);

        view.findViewById(R.id.nextButton).setOnClickListener(onClickListener);
        view.findViewById(R.id.moreInfoDownButton).setOnClickListener(onClickListener);

        infoLayout = view.findViewById(R.id.moreInfoInputLayout);
        infoLayout.setVisibility(View.INVISIBLE);


        //<category spinner> part
        categorySpinner = view.findViewById(R.id.productCategorySpinner);

        ArrayAdapter<CharSequence> spinnerAdapter
                = ArrayAdapter.createFromResource(getContext(), R.array.category_list, R.layout.item_spinner_category);

        categorySpinner.setAdapter(spinnerAdapter);
        categorySpinner.setOnItemSelectedListener(SpinnerListener);

        //<count spinner> part
        countSpinner = view.findViewById(R.id.countSpinner);

        ArrayAdapter<CharSequence> spinnerAdapter2
                = ArrayAdapter.createFromResource(getContext(), R.array.product_count_list, R.layout.support_simple_spinner_dropdown_item);

        countSpinner.setAdapter(spinnerAdapter2);
        countSpinner.setOnItemSelectedListener(SpinnerListener);

        //for date picker
        view.findViewById(R.id.datePickerContainer).setOnClickListener(onClickListener);
        view.findViewById(R.id.datePickerContainer2).setOnClickListener(onClickListener);

        //datePicker text view
        buyYear = view.findViewById(R.id.buyYearText);
        buyMonth = view.findViewById(R.id.buyMonthText);
        buyDate = view.findViewById(R.id.buyDayText);

        endYear = view.findViewById(R.id.endYearText);
        endMonth = view.findViewById(R.id.endMonthText);
        endDate = view.findViewById(R.id.endDayText);

        //dialog message 저장 배열
        messageText = new String[]{"카테고리를 입력해주세요.", "상품 이름을 입력해주세요.", "이미지를 최소 1장 이상 첨부해주세요.",
                "구매일자를 입력해주세요.", "유통기한을 입력해주세요."};


        productName = view.findViewById(R.id.inputProductNameText);

        //density for dp
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        density = displayMetrics.density;


//        //<check box> part
//        check1 = view.findViewById(R.id.productCheckBox1);
////        check1.setOnClickListener(onClickListener);
//        check2 = view.findViewById(R.id.productCheckBox2);
////        check2.setOnClickListener(onClickListener);

        return view;
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
            buyYear.setText(String.valueOf(year));
            buyMonth.setText(String.valueOf(month));
            buyDate.setText(String.valueOf(date));
        }
        else{
            endYear.setText(String.valueOf(year));
            endMonth.setText(String.valueOf(month));
            endDate.setText(String.valueOf(date));

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

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageAddButton:

                    builder = new AlertDialog.Builder(getContext());
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_camera_gallery,null);
                    view.findViewById(R.id.galleryButtonText).setOnClickListener(onClickListener);
                    view.findViewById(R.id.cameraButtonText).setOnClickListener(onClickListener);
                    builder.setView(view);
                    dialog = builder.create();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                    break;

                case R.id.nextButton:
                    moveViewPagerFragment();
                    break;
                case R.id.moreInfoDownButton:
                    showInfoLayout();
                    break;
                case R.id.datePickerContainer:
                    showDatePickerDialog("구매일자");
                    break;
                case R.id.datePickerContainer2:
                    showDatePickerDialog("유통기한");
                    break;
                case R.id.cameraButtonText:
                    clickImageAddButton("camera");
                    break;
                case R.id.galleryButtonText:
                    if(galleryPermissionCheck())
                        clickImageAddButton("gallery");
                    break;

            }
        }
    };

    void showInfoLayout(){
        if (infoLayout.getVisibility() == View.VISIBLE) {
            infoLayout.setVisibility(View.INVISIBLE);
            infoLayout.animate().setDuration(200);
        } else {
            infoLayout.setVisibility(View.VISIBLE);
            infoLayout.animate().setDuration(200);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted. Continue the action or workflow
                // in your app.
                clickImageAddButton("gallery");
            }  else {
                // Explain to the user that the feature is unavailable because
                // the features requires a permission that the user has denied.
                // At the same time, respect the user's decision. Don't link to
                // system settings in an effort to convince the user to change
                // their decision.
                Toast.makeText(getContext(), "권한허용을 거부하셨습니다.이미지를 불러오지 못함.", Toast.LENGTH_SHORT).show();
            }

        // Other 'case' lines to check for other
        // permissions this app might request.
    }




    }

    boolean galleryPermissionCheck(){
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
//            performAction(...);
            return true;
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
//            showInContextUI(...);
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GALLERY_ACCESS);

        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GALLERY_ACCESS);
//            requestPermissionLauncher.launch(
//                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        return false;
    }

    void clickImageAddButton(String type){
        if(type.equals("gallery")) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, GALLERY_ACCESS);
        }else{
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
//            }
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK && data != null ) {
            System.out.println("oook");
            switch(requestCode) {
                case REQUEST_IMAGE_CAPTURE:

                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", null);
                    imageAddToStack(Uri.parse(path));
                    break;

                case GALLERY_ACCESS:
                    imageAddToStack(data.getData());
                    break;
            }

        }
    }

    List<Uri> imageAddList = new ArrayList(10);

    void imageAddToStack(Uri uri){
        dialog.dismiss();

        ImageView img = new ImageView(getContext());
        img.setId(imageAddList.size());
        img.setScaleType(ImageView.ScaleType.FIT_XY);

        // dp = (int) (84*density + 0.5f);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (84 * density + 0.5f), (int) (84 * density + 0.5f));
        layoutParams.leftMargin = (int) (8 * density + 0.5f);
        img.setLayoutParams(layoutParams);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageLayout.removeView(img);
                try {
                    imageAddList.remove(imageAddList.indexOf(uri));
                } catch (IndexOutOfBoundsException e) {
                    imageAddList.clear();
                }
                imageCountText.setText(String.valueOf(imageAddList.size()));
                if (imageAddList.size() < 10 && !view.findViewById(R.id.imageAddButton).isEnabled())
                    view.findViewById(R.id.imageAddButton).setEnabled(true);

            }
        });
        imageLayout.addView(img);
        Glide.with(getContext()).load(uri).transform(new RoundedCorners((int) (4 * density + 0.5f))).into(img);
        imageAddList.add(uri);
        imageCountText.setText(String.valueOf(imageAddList.size()));

        if (imageAddList.size() == 10)
            view.findViewById(R.id.imageAddButton).setEnabled(false);
    }

    void moveViewPagerFragment(){
        //for DialogMessage 호출, 인덱스 번호 초기화
        int messageNumber = -1;

        if(categorySpinner.getSelectedItemPosition() == 0)
            messageNumber = 0;
        else if(productName.getText().toString().equals(""))
            messageNumber = 1;
        else if(imageAddList.size() == 0)
            messageNumber = 2;
        else if(categorySpinner.getSelectedItemPosition() == 1){
            if (buyYear.getText().equals(""))
                messageNumber = 3;
            else if (endYear.getText().equals(""))
                messageNumber = 4;
        }

        //alert dialog 조건별 코드
        if(messageNumber != -1){
//            builder = new AlertDialog.Builder(getContext(),R.style.CustomAlertDialog);
            builder = new AlertDialog.Builder(getContext());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_message,null);
            TextView informText = view.findViewById(R.id.dialogMessageText);
            informText.setText(messageText[messageNumber]);
            builder.setView(view);
            dialog = builder.create();
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
