package com.example.mainactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database and table information
    private static final String DATABASE_NAME = "userDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";

    // User Table Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_DOB = "date_of_birth";
    private static final String COLUMN_PERSONAL_ID = "personal_id";

    // review table columns
    private static final String COLUMN_USERID = "id";
    private static final String COLUMN_REVIEWERID = "reviewId";
    private static final String COLUMN_REVIEWTEXT = "reviewText";
    private static final int COLUMN_RATING = 0;
    
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PERSONAL_ID + " INTEGER UNIQUE,"
                + COLUMN_PHONE + " TEXT,"
                + COLUMN_EMAIL + " TEXT,"
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_FIRST_NAME + " TEXT,"
                + COLUMN_LAST_NAME + " TEXT,"
                + COLUMN_DOB + " TEXT"
                + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // user table functions
    // Insert a new user into the database
    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PERSONAL_ID, user.getPersonalId());
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_DOB, user.getDateOfBirth());

        // Insert the row
        long result = db.insert(TABLE_USERS, null, values);
        db.close();
        return result; // returns the row ID or -1 if there's an error
    }

    // Retrieve a user by their personal ID
    public User getUserByPersonalId(int personalId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_ID, COLUMN_PERSONAL_ID, COLUMN_PHONE, COLUMN_EMAIL, COLUMN_PASSWORD,
                        COLUMN_FIRST_NAME, COLUMN_LAST_NAME, COLUMN_DOB},
                COLUMN_PERSONAL_ID + "=?",
                new String[]{String.valueOf(personalId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
            );
            user.setPersonalId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PERSONAL_ID)));
            cursor.close();
            return user;
        } else {
            return null; // User not found
        }
    }

    public boolean checkIfEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase(); // Get a readable database
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_EMAIL + "=?", new String[]{email},
                null, null, null); // Query the database for the given email

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true; // Email exists
        } else {
            cursor.close();
            return false; // Email does not exist
        }
    }

     public boolean checkIfPhoneExists(String phone) {
        SQLiteDatabase db = this.getReadableDatabase(); // Get a readable database
        Cursor cursor = db.query(TABLE_USERS, null, COLUMN_PHONE + "=?", new String[]{phone},
                null, null, null); // Query the database for the given email

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true; // Email exists
        } else {
            cursor.close();
            return false; // Email does not exist
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    // Safely retrieve the column indexes
                    int idIndex = cursor.getColumnIndex(COLUMN_ID);
                    int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
                    int passwordIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
                    int firstNameIndex = cursor.getColumnIndex(COLUMN_FIRST_NAME);
                    int lastNameIndex = cursor.getColumnIndex(COLUMN_LAST_NAME);
                    int phoneIndex = cursor.getColumnIndex(COLUMN_PHONE);
                    int dobIndex = cursor.getColumnIndex(COLUMN_DOB);

                    // Check for valid column indices (they should be >= 0)
                    if (idIndex != -1 && emailIndex != -1 && passwordIndex != -1 &&
                            firstNameIndex != -1 && lastNameIndex != -1 && phoneIndex != -1 && dobIndex != -1) {

                        // Retrieve values from the cursor safely
                        String email = cursor.getString(emailIndex);
                        String password = cursor.getString(passwordIndex);
                        String firstName = cursor.getString(firstNameIndex);
                        String lastName = cursor.getString(lastNameIndex);
                        String phoneNumber = cursor.getString(phoneIndex);
                        String dob = cursor.getString(dobIndex);

                        // Create a User object and add it to the list
                        User user = new User(firstName, lastName, email, password, phoneNumber, dob);
                        userList.add(user);
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();  // Always close the cursor in a finally block
            db.close();      // Close the database connection
        }
        return userList;
    }

    // Other operations (update, delete) can also be added similarly

    //review table functions
}
