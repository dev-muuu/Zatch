package com.example.zatch.navigation.my_zatch;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class gatchDataItem implements Parcelable {

    private Uri image_uri;
    private boolean checkbox = false;

    public gatchDataItem(Uri image){
        this.image_uri = image;
    }


    protected gatchDataItem(Parcel in) {
        image_uri = in.readParcelable(Uri.class.getClassLoader());
        checkbox = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(image_uri, flags);
        dest.writeByte((byte) (checkbox ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<gatchDataItem> CREATOR = new Creator<gatchDataItem>() {
        @Override
        public gatchDataItem createFromParcel(Parcel in) {
            return new gatchDataItem(in);
        }

        @Override
        public gatchDataItem[] newArray(int size) {
            return new gatchDataItem[size];
        }
    };

    public Uri getImage_uri(){return image_uri;}
    public void setImage_uri(Uri imageUri) {image_uri = imageUri;}

    public boolean isSelected(){
        return checkbox;
    }
    public void setCheckbox(boolean checkbox){ this.checkbox = checkbox; }

}
