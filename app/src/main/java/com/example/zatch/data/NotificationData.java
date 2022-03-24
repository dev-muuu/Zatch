package com.example.zatch.data;

public class NotificationData {

    private long date;
    private NotificationEnum type;

    public NotificationData(NotificationEnum type, long date){
        this.date = date;
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public String getTypeTitle() {
        return type.getServiceTitle();
    }

    public String getTypeMessage() {
        return type.getMessage();
    }

}
