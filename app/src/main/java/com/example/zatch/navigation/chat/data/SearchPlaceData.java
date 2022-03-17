package com.example.zatch.navigation.chat.data;

public class SearchPlaceData {

    String place_name;           // 장소명, 업체명
    String address_name;         // 전체 지번 주소
//    String road_address_name;     // 전체 도로명 주소
//    String x;                    // X 좌표값 혹은 longitude
//    String y;                    // Y 좌표값 혹은 latitude

    public String getPlaceName() {
        return place_name;
    }

    public String getAddressName() {
        return address_name;
    }
//
//    public String getRoadAddressName() {
//        return road_address_name;
//    }
//
//    public String getX() {
//        return x;
//    }
//
//    public String getY() {
//        return y;
//    }
}
