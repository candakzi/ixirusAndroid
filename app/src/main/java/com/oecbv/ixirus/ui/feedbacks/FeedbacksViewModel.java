package com.oecbv.ixirus.ui.feedbacks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FeedbacksViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FeedbacksViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}