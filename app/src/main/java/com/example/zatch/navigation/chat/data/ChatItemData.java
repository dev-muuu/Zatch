package com.example.zatch.navigation.chat.data;

import android.net.Uri;

import java.text.SimpleDateFormat;

public class ChatItemData {
    private String userName;
    private String content;
    private long time;
//    private String timeText;
    private Uri userImage;
    private int viewType;
    private final SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    public ChatItemData(String userName, String content, long time, Uri userImage, int viewType) {
        this.userName = userName;
        this.content = content;
        this.time = time;
        this.userImage = userImage;
        this.viewType = viewType;
    }

    public String getUserName() {
        return userName;
    }


    public String getContent() {
        return content;
    }


    public String getTimeText() {
        System.out.println(format);
        return format.format(this.time);
    }


    public Uri getUserImage() {
        return userImage;
    }

    public int getViewType() {
        return viewType;
    }

}
