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

    private int lastAssignedId = 0;
    // Database and table information
    private static final String DATABASE_NAME = "userDatabase";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_USERS = "users";
    private static final String TABLE_REVIEWS = "reviews";
    private static final String TABLE_USER_SKILLS = "userSkills";

    // User Table Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_DOB = "date_of_birth";
    private static final String COLUMN_PERSONAL_ID = "personal_id";

    // Review table columns
    private static final String COLUMN_REVIEW_ID = "reviewId";
    private static final String COLUMN_REVIEWER_ID = "reviewerId";
    private static final String COLUMN_USER_ID = "userId";
    private static final String COLUMN_REVIEW_TEXT = "review_text";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_REVIEW_DATE = "date";

    private static final String COLUMN_SKILL = "skill";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
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

        // Create reviews table
        String CREATE_REVIEWS_TABLE = "CREATE TABLE reviews (" +
                "reviewId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "reviewerId INTEGER," +
                "review_text TEXT," +
                "rating INTEGER," +
                "date TEXT," +
                "FOREIGN KEY (userId) REFERENCES users(id)," +
                "FOREIGN KEY (reviewerId) REFERENCES users(id)" +
                ")";
        db.execSQL(CREATE_REVIEWS_TABLE);

        // Create userSkills table
        String CREATE_SKILLS_TABLE = "CREATE TABLE userSkills (" +
                "user_id INTEGER," +
                "skill TEXT," +
                "PRIMARY KEY(user_id, skill)," +
                "FOREIGN KEY(user_id) REFERENCES users(id)" +
                ")";
        db.execSQL(CREATE_SKILLS_TABLE);
        syncLastAssignedId(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS reviews");
        db.execSQL("DROP TABLE IF EXISTS userSkills");
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        // Enable foreign key constraints
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
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
            cursor.close();
            return user;
        } else {
            return null; // User not found
        }
    }

    public void syncLastAssignedId(SQLiteDatabase db) {
        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_PERSONAL_ID + ") FROM " + TABLE_USERS, null);
        if (cursor != null && cursor.moveToFirst()) {
            lastAssignedId = cursor.getInt(0); // Update lastAssignedId
            cursor.close();
        }
    }

    public boolean checkIfEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID}, COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null);

        boolean exists = (cursor != null && cursor.moveToFirst());
        if (cursor != null) cursor.close();
        return exists;
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
            return firstName + " " + lastName;
        } else {
            return null;
        }
    }

    public boolean deleteUser(int personalId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_USERS, COLUMN_PERSONAL_ID + "=?", new String[]{String.valueOf(personalId)});
        db.close();
        return rowsAffected > 0;
    }

    // Other operations (update, delete) can also be added similarly






    //review table functions
    public long addReview(Review review) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, review.getUserId());
        values.put(COLUMN_REVIEWER_ID, review.getReviewerId());
        values.put(COLUMN_REVIEW_TEXT, review.getReviewText());
        values.put(COLUMN_RATING, review.getRating());
        values.put(COLUMN_REVIEW_DATE, review.getDate());

        long result = db.insert(TABLE_REVIEWS, null, values);
        db.close();
        return result; // Returns row ID or -1 if there's an error
    }

    public List<Review> getReviewsForUser(int userId) {
        List<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM reviews WHERE userId = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    int reviewIdIndex = cursor.getColumnIndex(COLUMN_REVIEW_ID);
                    int reviewerIdIndex = cursor.getColumnIndex(COLUMN_REVIEWER_ID);
                    int reviewTextIndex = cursor.getColumnIndex(COLUMN_REVIEW_TEXT);
                    int ratingIndex = cursor.getColumnIndex(COLUMN_RATING);
                    int dateIndex = cursor.getColumnIndex(COLUMN_REVIEW_DATE);

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


    //skills table functions
    public void insertUserSkills(SQLiteDatabase db, String userId, List<String> skills) {
        for (String skill : skills) {
            ContentValues values = new ContentValues();
            values.put("user_id", userId);
            values.put("skill", skill);
            db.insertWithOnConflict("user_skills", null, values, SQLiteDatabase.CONFLICT_IGNORE);
        }
    }

    public List<String> getUserSkills(SQLiteDatabase db, String userId) {
        List<String> skills = new ArrayList<>();
        Cursor cursor = db.query("user_skills",
                new String[]{"skill"},
                "user_id = ?",
                new String[]{userId},
                null, null, null);

        if (cursor.moveToFirst()) {
            do {
                skills.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return skills;
    }

    public void updateSkills(int userId, List<String> selectedSkills) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Begin a transaction to ensure atomicity
        db.beginTransaction();

        try {
            // First, remove existing skills for the user
            db.delete(TABLE_USER_SKILLS, "user_id = ?", new String[]{String.valueOf(userId)});

            // Now, insert the new list of selected skills
            for (String skill : selectedSkills) {
                ContentValues values = new ContentValues();
                values.put("user_id", userId);
                values.put("skill", skill);

                // Insert or ignore in case the skill already exists
                db.insertWithOnConflict(TABLE_USER_SKILLS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            }

            // Set the transaction as successful
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("Database Error", "Error updating skills for user ID " + userId, e);
        } finally {
            // End the transaction
            db.endTransaction();
            db.close();
        }
    }


    public void logUserSkills(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get skills for a specific user
        String query = "SELECT " + COLUMN_SKILL + " FROM " + TABLE_USER_SKILLS + " WHERE user_id = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

            if (cursor != null && cursor.moveToFirst()) {
                List<String> skills = new ArrayList<>();

                // Check if there are any skills for the user
                do {
                    // Get each skill from the cursor
                    String skill = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKILL));
                    skills.add(skill);
                } while (cursor.moveToNext());

                // Log the user ID and the list of skills
                Log.d("User Skills", "User ID: " + userId + ", Skills: " + skills);
            } else {
                // If cursor is null or no skills found
                Log.d("User Skills", "No skills found for user ID: " + userId);
            }
        } catch (Exception e) {
            Log.e("Database Error", "Error while querying user skills: ", e);
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
    }



}