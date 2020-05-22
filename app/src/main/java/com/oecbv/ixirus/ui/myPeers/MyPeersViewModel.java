package com.oecbv.ixirus.ui.myPeers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyPeersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyPeersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}