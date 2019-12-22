package com.example.ixirus.ui.mytasks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyTasksViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyTasksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("My Tasks");
    }

    public LiveData<String> getText() {
        return mText;
    }
}