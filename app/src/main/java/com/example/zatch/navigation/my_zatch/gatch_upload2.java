package com.example.zatch.navigation.my_zatch;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.zatch.R;
import com.example.zatch.navigation.my_zatch.data.GatchRegisterData;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class gatch_upload2 extends Activity {
    Dialog dialog;
    ArrayList<gatchDataItem> arrayList;
    GatchRegisterData registerData;
    EditText et_add;
    RadioButton radio1, radio2;

    private static final String BASE_URL = "http://ec2-3-34-33-239.ap-northeast-2.compute.amazonaws.com:3000";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gatch_upload2);

        Intent intent= getIntent();
        registerData = intent.getParcelableExtra("classData");

        for(Uri i : registerData.getPhotos()){
            System.out.println(i);
        }

        //문제다... 정적으로 10개까지 선언해서 false도 계속 출력됨..
        for(boolean i : registerData.isCertified()){
            System.out.println("here");
            System.out.println(i);
        }

        TextView state_tag = (TextView) findViewById(R.id.purchase_state_tag);
        if (!registerData.isPurchaseCheck()) {
            state_tag.setText("구매 전");
            state_tag.setTextColor(getResources().getColor(R.color.black_45));
            state_tag.setBackgroundResource(R.drawable.tag_gray10);
        } else {
            state_tag.setText("구매 후");
            state_tag.setTextColor(getResources().getColor(R.color.zatch_yellow));
            state_tag.setBackgroundResource(R.drawable.tag_orange);
        }

        TextView tv_name, tv_category, tv_people, tv_oprice, tv_nprice;
        tv_name = (TextView)findViewById(R.id.gatch_item);
        tv_category = (TextView) findViewById(R.id.item_category);
        tv_people = (TextView) findViewById(R.id.item_person);
        tv_oprice = (TextView) findViewById(R.id.item_price1);
        tv_nprice = (TextView) findViewById(R.id.item_price2);

        tv_name.setText(registerData.getProductName());
        tv_category.setText(String.valueOf(registerData.getCategoryIdx())); // category indx를 어떻게 string으로 제공할지 고민필요..
        tv_people.setText(registerData.getNumber());
        tv_oprice.setText(registerData.getPrice());
        tv_nprice.setText(registerData.getPricePerPerson());

        et_add = (EditText) findViewById(R.id.add_et);

        dialog = new Dialog(gatch_upload2.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog2);

        radio1 = findViewById(R.id.gatch_radio1);
        radio2 = findViewById(R.id.gatch_radio2);
        radio1.setChecked(true);


        Button upload = (Button) findViewById(R.id.upload_gatch);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    public void showDialog() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        TextView noBtn = dialog.findViewById(R.id.dialog_cancel_btn);
        TextView okBtn = dialog.findViewById(R.id.dialog_ok_btn);
        TextView main = dialog.findViewById(R.id.dialog_text);
        main.setText("가치 등록을 완료하시겠습니까?");
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //등록 완료
                registerGatchToServer();
            }
        });
    }

    private void registerGatchToServer(){

        registerData.setDeadlineCheck(radio1.isChecked());
        registerData.setAddInfo(et_add.getText().toString());
//        registerData.setPhotos();
//        registerData.setCertified();

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        ServerApi api = builder.build().create(ServerApi.class);
        Call<GatchRegisterData> call = api.gatchPost(registerData);

        call.enqueue(new Callback<GatchRegisterData>() {
            @Override
            public void onResponse(Call<GatchRegisterData> call, Response<GatchRegisterData> response) {
                GatchRegisterData data = response.body();
                System.out.println(data.toString());
            }

            @Override
            public void onFailure(Call<GatchRegisterData> call, Throwable t) {
                Log.w("통신실패", "Raw: ${response.raw()}");
                System.out.println(t.getCause());
                System.out.println(t.toString());

            }
        });
    }
}
