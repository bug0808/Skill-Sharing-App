package com.example.mainactivity.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.RoomDatabase;

import java.util.List;

@Entity(tableName = "guides")
public class GuideEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;
    public String content;

    public GuideEntity(String title, String description, String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }
}

