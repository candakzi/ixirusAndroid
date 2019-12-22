package com.example.ixirus.ui.coachingmentoring;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoachingMentoringViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CoachingMentoringViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Coaching Mentoring");
    }

    public LiveData<String> getText() {
        return mText;
    }
}