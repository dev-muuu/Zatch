package com.example.zatch.navigation.my_zatch;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zatch.R;
import com.example.zatch.navigation.my_zatch.data.GatchRegisterData;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;

public class gatch_upload2 extends Activity {

    Dialog dialog;
    GatchRegisterData registerData;
    ArrayList<MultipartBody.Part> fileData;
    private ArrayList<gatchDataItem> imageData;
    EditText et_add;
    RadioButton radio1, radio2;

    private static final String BASE_URL = "http://ec2-3-34-33-239.ap-northeast-2.compute.amazonaws.com:3000";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gatch_upload2);

        Intent intent= getIntent();
        registerData = intent.getParcelableExtra("classData");
        imageData = intent.getParcelableArrayListExtra("imageData");
        Log.e("2","2");
        System.out.println(imageData);


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


        //image recyclerview
        RecyclerView recyclerView = findViewById(R.id.gatchImageRecyclerView);
        recyclerView.setAdapter(new GatchImageAdapter(imageData, gatch_upload2.this));
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext(),RecyclerView.HORIZONTAL,false));
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
                dialog.dismiss();
            }
        });
    }

    private void registerGatchToServer(){

        makeImageSendType();
        HashMap<String,RequestBody> mapData = new HashMap<>();
        mapData.put("categoryIdx",RequestBody.create(MediaType.parse("text/plain"), String.valueOf(registerData.getCategoryIdx())));
        mapData.put("purchaseCheck",RequestBody.create(MediaType.parse("text/plain"),String.valueOf(registerData.isPurchaseCheck())));
        mapData.put("productName",RequestBody.create(MediaType.parse("text/plain"), registerData.getProductName()));
        mapData.put("price",RequestBody.create(MediaType.parse("text/plain"), registerData.getPrice()));
        mapData.put("number",RequestBody.create(MediaType.parse("text/plain"), "3"));
        mapData.put("addInfo",RequestBody.create(MediaType.parse("text/plain"), et_add.getText().toString()));
        mapData.put("deadlineCheck",RequestBody.create(MediaType.parse("text/plain"),String.valueOf(registerData.isDeadlineCheck())));
        mapData.put("userIdx",RequestBody.create(MediaType.parse("text/plain"),"apple"));

        ArrayList<MultipartBody.Part> certified = new ArrayList<>();
        for(gatchDataItem item: imageData){
//            Boolean data = item.isSelected();
            certified.add(MultipartBody.Part.createFormData("certified",String.valueOf(item.isSelected())));
        }

        System.out.println(mapData);
        System.out.println("prepare end");

//
//        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
//                .connectTimeout(2, TimeUnit.MINUTES)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(BASE_URL)
//                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create());
        ServerApi api = builder.build().create(ServerApi.class);
        Call<GatchRegisterData> call = api.gatchPost(mapData, fileData, certified);
        System.out.println("send");
        call.enqueue(new Callback<GatchRegisterData>() {
            @Override
            public void onResponse(Call<GatchRegisterData> call, Response<GatchRegisterData> response) {

                Log.e("response","response");
                System.out.println(response.message());
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<GatchRegisterData> call, Throwable t) {
                Log.w("통신실패", "Raw: ${response.raw()}");
                System.out.println(t.getCause());
                System.out.println(t.toString());

            }
        });
    }

    private void makeImageSendType(){
        fileData = new ArrayList<>();
        for(gatchDataItem item : imageData) {
            File image = new File(getRealPathFromUri(item.getImage_uri()));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), image);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image", image.getName(), requestBody);
            fileData.add(body);
        }
    }

    private String getRealPathFromUri(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getBaseContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }



}
