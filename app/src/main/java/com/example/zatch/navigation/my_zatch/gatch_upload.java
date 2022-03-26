package com.example.zatch.navigation.my_zatch;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.navigation.my_zatch.data.GatchRegisterData;

import java.util.ArrayList;

public class gatch_upload extends Activity {

    private static final String TAG = "gatch_upload";
    ImageView upload;
    RecyclerView recyclerView;
    MultiImageAdapter miadapter;
    TextView picNum;
    ArrayList<gatchDataItem> arrayList = new ArrayList<>();
//    ArrayList<Boolean> certifiedList = new ArrayList<>();
    boolean[] certifiedList = new boolean[10];
    ArrayList<Uri> uriList = new ArrayList<>(); //이미지 uri를 담을 arrayList 객체
    int count;
    Dialog dialog;
    Spinner spinner;
    EditText name, price, pnum;
    boolean pstate = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gatch_upload);
        //뒤로가기
        ImageButton back = (ImageButton) findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(Activity.RESULT_CANCELED, intent);
                finish();
            }
        });

        upload = (ImageView) findViewById(R.id.upload_img);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent imgup = new Intent(Intent.ACTION_PICK);
                imgup.setType(MediaStore.Images.Media.CONTENT_TYPE);
                //다중선택 가능 코드
//                imgup.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(imgup, 101);
            }
        });
        recyclerView = findViewById(R.id.image_area);
        picNum = (TextView) findViewById(R.id.pic_num);

        //카테고리 스피너
        spinner = (Spinner) findViewById(R.id.category_spinner);
        final String[] category = {spinner.getSelectedItem().toString()};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category[0] = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //가격, 인원 정보 입력
        EditText ppp;
        name = (EditText) findViewById(R.id.name_input);
        price = (EditText) findViewById(R.id.input_price);
        pnum = (EditText) findViewById(R.id.input_people);
        ppp = (EditText) findViewById(R.id.input_price_per_person);

        //인당 가격 자동 표시
        price.setText("1000");
        pnum.setText("2");
        ppp.setText(String.valueOf(Integer.parseInt(price.getText().toString()) / Integer.parseInt(pnum.getText().toString())));
        final int[] num1 = new int[1];
        final int[] num2 = new int[1];
        final int[] num3 = new int[1];
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    num1[0] = Integer.parseInt(price.getText().toString());
                    num2[0] = Integer.parseInt(pnum.getText().toString());
                    num3[0] = num1[0] / num2[0];
                    ppp.setText(String.valueOf(num3[0]));
                } catch (Exception e) {

                }
            }
        });
        pnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    num1[0] = Integer.parseInt(price.getText().toString());
                    num2[0] = Integer.parseInt(pnum.getText().toString());
                    num3[0] = num1[0] / num2[0];
                    ppp.setText(String.valueOf(num3[0]));
                } catch (Exception e) {
                }
            }
        });

        ImageView iv_mark = (ImageView) findViewById(R.id.ex_mark);
        TextView tv_notice1, tv_notice2;
        tv_notice1 = (TextView) findViewById(R.id.state_t1);
        tv_notice2 = (TextView) findViewById(R.id.state_t2);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.state_radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.purchase_state_false:
                        pstate = false;
                        iv_mark.setVisibility(View.GONE);
                        tv_notice1.setVisibility(View.GONE);
                        tv_notice2.setVisibility(View.GONE);
                        break;
                    case R.id.purchase_state_true:
                        pstate = true;
                        iv_mark.setVisibility(View.VISIBLE);
                        tv_notice1.setVisibility(View.VISIBLE);
                        tv_notice2.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        dialog = new Dialog(gatch_upload.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        TextView okBtn = dialog.findViewById(R.id.dialog_ok_btn);
        TextView tv = dialog.findViewById(R.id.dialog_text);

//        final int infoArr[] = new int[3];

        //다음 단계로 진행
        Button next = (Button) findViewById(R.id.progress_btn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().equals("")) {
                    showDialog();
                } else if (Integer.parseInt(price.getText().toString()) < 1) {
                    showDialog();
                    tv.setText("원가는 0원 이하로 입력하실 수 없습니다.");
                } else if (Integer.parseInt(pnum.getText().toString()) < 2) {
                    showDialog();
                    tv.setText("인원 수를 설정해 주세요.");
                } else {
//                    infoArr[0] = Integer.parseInt(price.getText().toString()); //원가
//                    infoArr[1] = Integer.parseInt(pnum.getText().toString()); //인원수
//                    infoArr[2] = Integer.parseInt(ppp.getText().toString()); //인당 금액
                    sendDataToNextPage();
                }
            }
        });

    }

    private void sendDataToNextPage() {

        GatchRegisterData registerData = new GatchRegisterData();
        registerData.setCategoryIdx(spinner.getSelectedItemPosition());
        registerData.setPurchaseCheck(pstate);
        registerData.setProductName(name.getText().toString());
        registerData.setPrice(price.getText().toString());
        registerData.setNumber(pnum.getText().toString());
        registerData.setPhotos(uriList);
        registerData.setCertified(certifiedList);

        Intent intent = new Intent(getApplicationContext(), gatch_upload2.class);
        intent.putExtra("classData",registerData);
        startActivity(intent);
    }

    // 앨범에서 액티비티로 돌아온 후 실행되는 메서드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {   // 어떤 이미지도 선택하지 않은 경우
            Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
        } else {   // 이미지를 하나라도 선택한 경우
//            if (data.getClipData() == null) {     // 이미지를 하나만 선택한 경우
//                Log.e("single choice: ", String.valueOf(data.getData()));
//                Uri imageUri = data.getData();
//                uriList.add(imageUri);
//                gatchDataItem item = new gatchDataItem();
//                item.setImage_uri(imageUri);
//                arrayList.add(item);
//
//                miadapter = new MultiImageAdapter(arrayList, getApplicationContext());
//                recyclerView.setAdapter(miadapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
//                count = miadapter.getItemCount();
//            } else {      // 이미지를 여러장 선택한 경우
                ClipData clipData = data.getClipData();
                Log.e("clipData", String.valueOf(clipData.getItemCount()));

//                if(clipData.getItemCount() > 10){   // 선택한 이미지가 11장 이상인 경우
//                    Toast.makeText(getApplicationContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
//                }
//                else{   // 선택한 이미지가 1장 이상 10장 이하인 경우
                Log.e(TAG, "multiple choice");
                Uri imageUri = data.getData();
                uriList.add(imageUri);
                try {
                    certifiedList[miadapter.getItemCount()] = false;
                }catch(NullPointerException e){
                    certifiedList[0] = false;
            }


//                gatchDataItem item = new gatchDataItem();
//                item.setImage_uri(data.getData());
//                arrayList.add(item);



//                for (int i = 0; i < clipData.getItemCount(); i++) {
//                    Uri imageUri = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져온다.
//                    try {
//                        uriList.add(imageUri);  //uri를 list에 담는다.
//                        gatchDataItem item = new gatchDataItem();
//                        item.setImage_uri(imageUri);
//                        arrayList.add(item);
//
//                    } catch (Exception e) {
//                        Log.e(TAG, "File select error", e);
//                    }
//                    }

//                    miadapter = new MultiImageAdapter(arrayList, getApplicationContext());
            miadapter = new MultiImageAdapter(uriList,certifiedList, getApplicationContext());
                    recyclerView.setAdapter(miadapter);   // 리사이클러뷰에 어댑터 세팅
                    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));     // 리사이클러뷰 수평 스크롤 적용
                    count = miadapter.getItemCount();
                }
//            }


        //이미지 개수 세기

        recyclerView = findViewById(R.id.image_area);
        picNum = (TextView) findViewById(R.id.pic_num);
        picNum.setText(Integer.toString(count));
        if (count == 0) {

        } else if (count > 9) {
            upload.setVisibility(View.GONE);
        }


    }

    public void showDialog() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView okBtn = dialog.findViewById(R.id.dialog_ok_btn);
        TextView tv = dialog.findViewById(R.id.dialog_text);
        tv.setText("상품 이름을 입력해 주세요.");
        okBtn.setText("확인");
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}

