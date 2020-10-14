package com.brukernavn.ung_cases.ui.recent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecentViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RecentViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is dashboard fragment");


    }

    public LiveData<String> getText() {
        return mText;
    }
}