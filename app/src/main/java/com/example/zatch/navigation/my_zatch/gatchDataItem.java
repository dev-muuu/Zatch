package com.example.zatch.navigation.my_zatch;

import android.net.Uri;

import java.io.Serializable;

public class gatchDataItem implements Serializable {
    private Uri image_uri;
    private boolean checkbox;

    public Uri getImage_uri(){return image_uri;}
    public void setImage_uri(Uri imageUri) {image_uri = imageUri;}

    public boolean isSelected(){
        return checkbox;
    }
    public void setCheckbox(boolean checkbox){ this.checkbox = checkbox; }

}
