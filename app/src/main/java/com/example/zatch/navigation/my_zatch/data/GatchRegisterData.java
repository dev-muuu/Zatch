package com.example.zatch.navigation.my_zatch.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class GatchRegisterData implements Parcelable {

    //    @SerializedName("categoryIdx")
    private int categoryIdx;
    //    @SerializedName("purchaseCheck")
    private boolean purchaseCheck;
    //    @SerializedName("productName")
    private String productName;
    //    @SerializedName("price")
    private int price;
    //    @SerializedName("number")
    private int number;
    //    @SerializedName("addInfo")
    private String addInfo;
    //    @SerializedName("deadlineCheck")
    private boolean deadlineCheck;
    //    @SerializedName("userIdx")
    private int userIdx;
    //    @SerializedName("photos")
    private ArrayList<Uri> photos;
    //    @SerializedName("certified")
    private boolean[] certified;

    public GatchRegisterData(){

    }

//    public GatchRegisterData(int categoryIdx, boolean purchaseCheck, String productName, int price, int number
//            , String addInfo, boolean deadlineCheck, int userIdx, ArrayList<Uri> photos, ArrayList<Boolean> certified) {
//        this.categoryIdx = categoryIdx;
//        this.purchaseCheck = purchaseCheck;
//        this.productName = productName;
//        this.price = price;
//        this.number = number;
//        this.addInfo = addInfo;
//        this.deadlineCheck = deadlineCheck;
//        this.userIdx = userIdx;
//        this.photos = photos;
//        this.certified = certified;
//    }


    protected GatchRegisterData(Parcel in) {
        categoryIdx = in.readInt();
        purchaseCheck = in.readByte() != 0;
        productName = in.readString();
        price = in.readInt();
        number = in.readInt();
        addInfo = in.readString();
        deadlineCheck = in.readByte() != 0;
        userIdx = in.readInt();
        photos = in.createTypedArrayList(Uri.CREATOR);
        certified = in.createBooleanArray();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(categoryIdx);
        dest.writeByte((byte) (purchaseCheck ? 1 : 0));
        dest.writeString(productName);
        dest.writeInt(price);
        dest.writeInt(number);
        dest.writeString(addInfo);
        dest.writeByte((byte) (deadlineCheck ? 1 : 0));
        dest.writeInt(userIdx);
        dest.writeTypedList(photos);
        dest.writeBooleanArray(certified);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GatchRegisterData> CREATOR = new Creator<GatchRegisterData>() {
        @Override
        public GatchRegisterData createFromParcel(Parcel in) {
            return new GatchRegisterData(in);
        }

        @Override
        public GatchRegisterData[] newArray(int size) {
            return new GatchRegisterData[size];
        }
    };

    public int getCategoryIdx() {
        return categoryIdx;
    }

    public boolean isPurchaseCheck() {
        return purchaseCheck;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return String.valueOf(price);
    }

    public String getPricePerPerson(){
        return String.valueOf(this.price / this.number);
    }

    public String getNumber() {
        return number+"명";
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

    public ArrayList<Uri> getPhotos() {
        return photos;
    }

    public boolean[] isCertified() {
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

    public void setPrice(String price){
        this.price = Integer.parseInt(price);
    }

    public void setNumber(String number){
        this.number = Integer.parseInt(number);
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

    public void setPhotos(ArrayList<Uri> photos) {
        this.photos = photos;
    }

    public void setCertified(boolean[] certified) {
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
