package com.example.zatch.navigation.my_zatch;

import com.example.zatch.navigation.my_zatch.data.GatchRegisterData;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface ServerApi {
    @Multipart
    @POST("value")
    Call<GatchRegisterData> gatchPost(
            @PartMap Map<String, RequestBody> data,
            @Part ArrayList<MultipartBody.Part> photos,
            @Part ArrayList<MultipartBody.Part> certified
    );

}
