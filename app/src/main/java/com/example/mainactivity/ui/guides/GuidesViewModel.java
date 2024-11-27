package com.example.mainactivity.ui.guides;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mainactivity.DatabaseHelper;
import com.example.mainactivity.classes.Guide;

import java.util.ArrayList;
import java.util.List;

public class GuidesViewModel extends ViewModel {

    private final MutableLiveData<List<Guide>> guideList;
    private final DatabaseHelper databaseHelper;

    public GuidesViewModel(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        guideList = new MutableLiveData<>(new ArrayList<>());
        loadGuides();
    }

    public LiveData<List<Guide>> getGuideList() {
        return guideList;
    }

    public void addGuide(Guide guide) {
        long guideId = databaseHelper.addGuide(guide);
        loadGuides();
    }

    public void loadGuides() {
        List<Guide> guides = databaseHelper.getAllGuides();
        guideList.setValue(guides);
    }
}
