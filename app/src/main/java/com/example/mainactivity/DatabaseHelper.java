package com.example.mainactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mainactivity.classes.Review;
import com.example.mainactivity.classes.User;

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

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        // Create the users table
        String CREATE_USERS_TABLE = "CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "personal_id INTEGER," +
                "phone TEXT," +
                "email TEXT," +
                "password TEXT," +
                "first_name TEXT," +
                "last_name TEXT," +
                "date_of_birth TEXT" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);

        // Create the reviews table
        String CREATE_REVIEWS_TABLE = "CREATE TABLE reviews (" +
                "review_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER," +          // ID of the user being reviewed
                "reviewer_id INTEGER," +      // ID of the user writing the review
                "review_text TEXT," +
                "rating INTEGER," +
                "date TEXT," +
                "FOREIGN KEY (user_id) REFERENCES users(id)," +
                "FOREIGN KEY (reviewer_id) REFERENCES users(id)" +
                ")";
        db.execSQL(CREATE_REVIEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

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

    // In DatabaseHelper.java
    public int validateLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query the database to validate the user credentials
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_PERSONAL_ID, COLUMN_PASSWORD},
                COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PERSONAL_ID));

            if (storedPassword.equals(password)) {
                cursor.close();
                return userId;  // Return the userId if credentials match
            } else {
                cursor.close();
                return -1;  // Invalid password
            }
        } else {
            return -1;  // User not found
        }
    }

    public void logAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PERSONAL_ID));
                String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME));
                String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                String dob = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB));

                // Log each user to Logcat
                Log.d("User Info", "ID: " + userId + ", Name: " + firstName + " " +
                        lastName + ", Email: " + email + ", Password: " + password + ", Phone: "
                        + phone + ", DOB: " + dob);
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Log.d("User Info", "No users found.");
        }
    }


    public String getUserNameByPersonalId(int personalId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_FIRST_NAME, COLUMN_LAST_NAME},
                COLUMN_PERSONAL_ID + "=?",
                new String[]{String.valueOf(personalId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String firstName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME));
            cursor.close();
            return firstName + " " + lastName;  // Combine first and last name
        } else {
            return null; // User not found
        }
    }

    public boolean deleteUser(int personalId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Use the personal_id to identify the row to delete
        int rowsAffected = db.delete(TABLE_USERS, COLUMN_PERSONAL_ID + "=?", new String[]{String.valueOf(personalId)});

        db.close();

        // Return true if a row was deleted, false otherwise
        return rowsAffected > 0;
    }

    // Other operations (update, delete) can also be added similarly






    //review table functions
    public long addReview(Review review) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("user_id", review.getUserId());
        values.put("reviewer_id", review.getReviewerId());
        values.put("review_text", review.getReviewText());
        values.put("rating", review.getRating());
        values.put("date", review.getDate());

        long result = db.insert("reviews", null, values);
        db.close();
        return result; // Returns row ID or -1 if there's an error
    }

    public List<Review> getReviewsForUser(int userId) {
        List<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM reviews WHERE user_id = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    int reviewIdIndex = cursor.getColumnIndex("review_id");
                    int reviewerIdIndex = cursor.getColumnIndex("reviewer_id");
                    int reviewTextIndex = cursor.getColumnIndex("review_text");
                    int ratingIndex = cursor.getColumnIndex("rating");
                    int dateIndex = cursor.getColumnIndex("date");

                    if (reviewIdIndex != -1 && reviewerIdIndex != -1 &&
                            reviewTextIndex != -1 && ratingIndex != -1 && dateIndex != -1) {

                        int reviewId = cursor.getInt(reviewIdIndex);
                        int reviewerId = cursor.getInt(reviewerIdIndex);
                        String reviewText = cursor.getString(reviewTextIndex);
                        int rating = cursor.getInt(ratingIndex);
                        String date = cursor.getString(dateIndex);

                        Review review = new Review(userId, reviewerId, reviewText, rating, date);
                        review.setReviewId(reviewId);
                        reviews.add(review);
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return reviews;
    }

}