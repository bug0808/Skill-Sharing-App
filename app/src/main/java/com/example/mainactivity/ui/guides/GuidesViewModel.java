package com.example.mainactivity.ui.guides;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mainactivity.Guide;

import java.util.ArrayList;
import java.util.List;

public class GuidesViewModel extends ViewModel {

    private final MutableLiveData<List<Guide>> guideList;

    public GuidesViewModel() {
        guideList = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Guide>> getGuideList() {
        return guideList;
    }

    public void addGuide(Guide guide) {
        List<Guide> currentList = guideList.getValue();
        if (currentList != null) {
            currentList.add(guide);
            guideList.setValue(currentList);
        }
    }
}
