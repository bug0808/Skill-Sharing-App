package com.example.mainactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mainactivity.classes.Events;

import java.util.ArrayList;
import java.util.List;

public class EventsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "events_db";
    private static final int DATABASE_VERSION = 1;

    // Table name and columns
    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_LOCATION = "location";

    public EventsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_EVENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LOCATION + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }

    // Insert a new event
    public void insertEvent(String title, String description, String date, String location) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, title);
            values.put(COLUMN_DESCRIPTION, description);
            values.put(COLUMN_DATE, date);
            values.put(COLUMN_LOCATION, location);
            db.insert(TABLE_EVENTS, null, values); // Insert without specifying the ID
        }
    }

    // Get all events
    public List<Events> getAllEvents() {
        List<Events> eventsList = new ArrayList<>();

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.query(TABLE_EVENTS, null, null, null, null, null, null)) {

            if (cursor != null) {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int titleIndex = cursor.getColumnIndex(COLUMN_TITLE);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_DESCRIPTION);
                int dateIndex = cursor.getColumnIndex(COLUMN_DATE);
                int locationIndex = cursor.getColumnIndex(COLUMN_LOCATION);

                while (cursor.moveToNext()) {
                    if (idIndex != -1 && titleIndex != -1 && descriptionIndex != -1 &&
                            dateIndex != -1 && locationIndex != -1) {
                        Events event = new Events(
                                String.valueOf(cursor.getInt(idIndex)),
                                cursor.getString(titleIndex),
                                cursor.getString(descriptionIndex),
                                cursor.getString(dateIndex),
                                cursor.getString(locationIndex)
                        );
                        eventsList.add(event);
                    }
                }
            }
        }
        return eventsList;
    }
    public boolean deleteEvent(int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the event from the database where eventId matches
        int result = db.delete(TABLE_EVENTS, COLUMN_ID + " = ?", new String[]{String.valueOf(eventId)});
        db.close();
        return result > 0;  // Returns true if the event was successfully deleted
    }
}
