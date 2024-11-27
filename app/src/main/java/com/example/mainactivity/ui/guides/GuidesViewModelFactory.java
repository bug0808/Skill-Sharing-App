package com.example.mainactivity.ui.guides;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mainactivity.DatabaseHelper;

public class GuidesViewModelFactory implements ViewModelProvider.Factory {

    private final DatabaseHelper databaseHelper;

    public GuidesViewModelFactory(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(GuidesViewModel.class)) {
            return (T) new GuidesViewModel(databaseHelper);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
