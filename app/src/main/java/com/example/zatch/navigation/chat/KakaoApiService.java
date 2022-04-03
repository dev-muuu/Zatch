package com.example.zatch.navigation.chat;

import com.example.zatch.location.MapViewActivity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface KakaoApiService{
    @GET("/v2/local/search/keyword.json")
    Call<AddressResultFragment.ResultSearchKeyword> getKakaoAddress(
            @Header("Authorization") String key,
            @Query("query") String address
    );

    @GET("/v2/local/geo/coord2address.json")
    Call<MapViewActivity.ResultSearchBuilding> getBuildingName(
            @Header("Authorization") String key,
            @Query("x") String x,
            @Query("y") String y
            );

}

