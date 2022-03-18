package com.example.zatch.navigation.my_zatch.data;

import com.google.gson.annotations.SerializedName;

import java.net.URI;

public class GatchRegisterData {

    public GatchRegisterData(int categoryIdx, boolean purchaseCheck, String productName, int price, int number
            , String addInfo, boolean deadlineCheck, int userIdx, String photos, boolean certified) {
        this.categoryIdx = categoryIdx;
        this.purchaseCheck = purchaseCheck;
        this.productName = productName;
        this.price = price;
        this.number = number;
        this.addInfo = addInfo;
        this.deadlineCheck = deadlineCheck;
        this.userIdx = userIdx;
        this.photos = photos;
        this.certified = certified;
    }
    @SerializedName("categoryIdx")
    private int categoryIdx;
    @SerializedName("purchaseCheck")
    private boolean purchaseCheck;
    @SerializedName("productName")
    private String productName;
    @SerializedName("price")
    private int price;
    @SerializedName("number")
    private int number;
    @SerializedName("addInfo")
    private String addInfo;
    @SerializedName("deadlineCheck")
    private boolean deadlineCheck;
    @SerializedName("userIdx")
    private int userIdx;
    @SerializedName("photos")
    private String photos;
    @SerializedName("certified")
    private boolean certified;

    public int getCategoryIdx() {
        return categoryIdx;
    }

    public boolean isPurchaseCheck() {
        return purchaseCheck;
    }

    public String getProductName() {
        return productName;
    }

    public int getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public String getAddInfo() {
        return addInfo;
    }

    public boolean isDeadlineCheck() {
        return deadlineCheck;
    }

    public int getUserIdx() {
        return userIdx;
    }

    public String getPhotos() {
        return photos;
    }

    public boolean isCertified() {
        return certified;
    }

    public void setCategoryIdx(int categoryIdx) {
        this.categoryIdx = categoryIdx;
    }

    public void setPurchaseCheck(boolean purchaseCheck) {
        this.purchaseCheck = purchaseCheck;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setAddInfo(String addInfo) {
        this.addInfo = addInfo;
    }

    public void setDeadlineCheck(boolean deadlineCheck) {
        this.deadlineCheck = deadlineCheck;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public void setCertified(boolean certified) {
        this.certified = certified;
    }

    @Override
    public String toString() {
        return "GatchRegisterData{" +
                "categoryIdx=" + categoryIdx +
                ", purchaseCheck=" + purchaseCheck +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", number=" + number +
                ", addInfo='" + addInfo + '\'' +
                ", deadlineCheck=" + deadlineCheck +
                ", userIdx=" + userIdx +
                ", photos=" + photos +
                ", certified=" + certified +
                '}';
    }
}
