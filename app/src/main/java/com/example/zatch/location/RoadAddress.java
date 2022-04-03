package com.example.zatch.location;

public class RoadAddress {
    private String building_name;   //건물 이름
    private String road_name;       //도로명
    private String main_building_no; //main 건물 번호
    private String sub_building_no; //서브 건물 번호

    public String getBuilding_name() {
        return building_name;
    }

    public String getRoad_name() {
        return road_name;
    }

    public String getMain_building_no() {
        return main_building_no;
    }

    public String getSub_building_no() {
        return sub_building_no;
    }
}