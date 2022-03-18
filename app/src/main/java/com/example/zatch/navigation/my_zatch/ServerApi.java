package com.example.zatch.navigation.my_zatch;

import com.example.zatch.navigation.my_zatch.data.GatchRegisterData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServerApi {

    @POST("value")
    Call<GatchRegisterData> gatchPost(
            @Body GatchRegisterData data
    );

}
