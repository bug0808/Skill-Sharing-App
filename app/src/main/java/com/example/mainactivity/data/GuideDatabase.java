package com.example.mainactivity.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {GuideEntity.class}, version = 1)
public abstract class GuideDatabase extends RoomDatabase {
    public abstract GuideDao guideDao();
}
