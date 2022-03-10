package com.example.zatch.navigation.chat;

import com.example.zatch.navigation.chat.data.SearchPlaceData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;


interface KakaoApiService{
    @GET("/v2/local/search/keyword.json")
    Call<MakeMeetingBottomSheet.ResultSearchKeyword> getKakaoAddress(
            @Header("Authorization") String key,
            @Query("query") String address
    );

}
