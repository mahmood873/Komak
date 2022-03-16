package com.example.komak.ui.profile;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private Button changepass;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Profile fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }
}
