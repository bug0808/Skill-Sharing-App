package com.example.mainactivity.ui.guides;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.example.mainactivity.Guide;
import com.example.mainactivity.data.GuideDao;
import com.example.mainactivity.data.GuideDatabase;
import com.example.mainactivity.data.GuideEntity;

import java.util.ArrayList;
import java.util.List;

public class GuidesViewModel extends AndroidViewModel {
    private final LiveData<List<Guide>> guideList;

    public GuidesViewModel(@NonNull Application application) {
        super(application);
        GuideDao dao = Room.databaseBuilder(application, GuideDatabase.class, "guides_db")
                .build()
                .guideDao();
        guideList = Transformations.map(dao.getAllGuides(), entities -> {
            List<Guide> guides = new ArrayList<>();
            for (GuideEntity entity : entities) {
                guides.add(new Guide(entity.title, entity.description, entity.content));
            }
            return guides;
        });
    }

    public LiveData<List<Guide>> getGuideList() {
        return guideList;
    }

    public void addGuide(Guide guide) {
        new Thread(() -> {
            GuideDao dao = Room.databaseBuilder(getApplication(), GuideDatabase.class, "guides_db")
                    .build()
                    .guideDao();
            dao.insert(new GuideEntity(guide.getTitle(), guide.getDescription(), guide.getContent()));
        }).start();
    }
}
