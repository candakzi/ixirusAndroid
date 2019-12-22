package com.example.ixirus.ui.developmentplan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DevelopmentPlanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DevelopmentPlanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Development Plan");
    }

    public LiveData<String> getText() {
        return mText;
    }
}