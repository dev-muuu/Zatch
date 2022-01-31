package com.example.zatch.navigation.user_page;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserPageViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public UserPageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is userpage fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
