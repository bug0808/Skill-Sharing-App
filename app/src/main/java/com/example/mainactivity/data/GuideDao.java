package com.example.mainactivity.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GuideDao {
    @Insert
    void insert(GuideEntity guide);

    @Query("SELECT * FROM guides")
    LiveData<List<GuideEntity>> getAllGuides();
}
