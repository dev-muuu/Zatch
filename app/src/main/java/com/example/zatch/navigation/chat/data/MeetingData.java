package com.example.zatch.navigation.chat.data;

import java.io.Serializable;

public class MeetingData implements Serializable {  //일단 serializable로 하고, 이후 para/./로 바꾸든지 하기..

    private String month;
    private String date;
    private String hour;
    private String minute;
    private String place;
    private boolean giveAlarm;

    public MeetingData() {
    }

    public MeetingData(String month, String date, String hour, String minute, String place, boolean giveAlarm) {
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.place = place;
        this.giveAlarm = giveAlarm;
    }

    public String getMonth() {
        return month;
    }

    public String getDate() {
        return date;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getPlace() {
        return place;
    }

    public boolean isGiveAlarm() {
        return giveAlarm;
    }
}
