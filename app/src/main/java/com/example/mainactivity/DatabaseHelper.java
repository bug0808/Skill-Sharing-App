package com.example.mainactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mainactivity.classes.Events;
import com.example.mainactivity.classes.Guide;
import com.example.mainactivity.classes.Review;
import com.example.mainactivity.classes.User;
import com.example.mainactivity.classes.UserSkills;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class DatabaseHelper extends SQLiteOpenHelper {

    // database and table information
    private static final String DATABASE_NAME = "userDatabase";
    private static final int DATABASE_VERSION = 32;
    private static final String TABLE_USERS = "users";
    private static final String TABLE_REVIEWS = "reviews";
    private static final String TABLE_USER_SKILLS = "user_skills";
    private static final String TABLE_GUIDES = "guides";
    private static final String TABLE_LOGIN = "login";

    // user Table Columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_DOB = "date_of_birth";
    private static final String COLUMN_PERSONAL_ID = "personal_id";

    // review table columns
    private static final String COLUMN_REVIEW_ID = "reviewId";
    private static final String COLUMN_REVIEWER_ID = "reviewerId";
    private static final String COLUMN_USER_ID = "userId";
    private static final String COLUMN_REVIEW_TEXT = "review_text";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_REVIEW_DATE = "date";

    //skills
    private static final String COLUMN_SKILL = "skill";

    // guides
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";

    //events
    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_EVENT_ID = "_id";
    public static final String COLUMN_EVENT_TITLE = "title";
    public static final String COLUMN_EVENT_DESCRIPTION = "description";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_LOCATION = "location";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //user table
    String CREATE_USERS_TABLE = "CREATE TABLE users (" +
                "id INTEGER," +
                "personal_id INTEGER PRIMARY KEY," +
                "phone TEXT," +
                "email TEXT," +
                "password TEXT," +
                "first_name TEXT," +
                "last_name TEXT," +
                "date_of_birth TEXT" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);

        //logged in table
        String CREATE_LOGIN_TABLE = "CREATE TABLE login (" +
                "userId INTEGER PRIMARY KEY," +
                "logged_in INTEGER DEFAULT 0," +
                "FOREIGN KEY (userId) REFERENCES users(personal_id)" +
                ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        // reviews table
        String CREATE_REVIEWS_TABLE = "CREATE TABLE reviews (" +
                "reviewId INTEGER," +
                "userId INTEGER PRIMARY KEY," +
                "reviewerId INTEGER," +
                "review_text TEXT," +
                "rating INTEGER," +
                "date TEXT," +
                "FOREIGN KEY (userId) REFERENCES users(personal_id)," +
                "FOREIGN KEY (reviewerId) REFERENCES users(personal_id)" +
                ")";
        db.execSQL(CREATE_REVIEWS_TABLE);

        // skills table
        String CREATE_SKILLS_TABLE = "CREATE TABLE user_skills (" +
                "user_id INTEGER," +
                "skill TEXT," +
                "PRIMARY KEY(user_id, skill)," +
                "FOREIGN KEY(user_id) REFERENCES users(personal_id)" +
                ")";
        db.execSQL(CREATE_SKILLS_TABLE);

        //guides table
        String CREATE_GUIDES_TABLE = "CREATE TABLE " + TABLE_GUIDES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TITLE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT"
                + ")";
        db.execSQL(CREATE_GUIDES_TABLE);

        //events table
        String createTableQuery = "CREATE TABLE " + TABLE_EVENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LOCATION + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("PRAGMA foreign_keys = OFF;");

        db.execSQL("DELETE FROM user_skills;");
        db.execSQL("DELETE FROM reviews;");
        db.execSQL("DELETE FROM guides;");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS login;");
        db.execSQL("DROP TABLE IF EXISTS users;");
        db.execSQL("DROP TABLE IF EXISTS reviews;");
        db.execSQL("DROP TABLE IF EXISTS user_skills;");
        db.execSQL("DROP TABLE IF EXISTS guides;");

        onCreate(db);

        db.execSQL("PRAGMA foreign_keys = ON;");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    //new user in db
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

        long result = db.insert(TABLE_USERS, null, values);
        return result;
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PHONE, user.getPhone());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_DOB, user.getDateOfBirth());

        return db.update(TABLE_USERS, values, COLUMN_PERSONAL_ID + " = ?",
                new String[]{String.valueOf(user.getPersonalId())});
    }

    public boolean updateUserDetails(int userId, String firstName, String lastName, String email, String phone, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("first_name", firstName);
        values.put("last_name", lastName);
        values.put("email", email);
        values.put("phone", phone);
        values.put("date_of_birth", dob);

        int rowsAffected = db.update("users", values, "id = ?", new String[]{String.valueOf(userId)});
        return rowsAffected > 0;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PERSONAL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))
                );
                users.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return users;
    }

    // get user by their id
    public User getUserByPersonalId(int personalId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_PERSONAL_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(personalId)});

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PERSONAL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB))
            );
            cursor.close();
            return user;
        } else {
            return null;
        }
    }

    // get user by their email
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_EMAIL + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(email)});

        if (cursor != null && cursor.moveToFirst()) {
            User user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PERSONAL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DOB))
            );
            cursor.close();
            return user;
        } else {
            return null;
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

    // checking login logic
    public int validateLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_PERSONAL_ID, COLUMN_PASSWORD},
                COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String storedPassword = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PERSONAL_ID));

            if (storedPassword.equals(password)) {
                cursor.close();
                return userId;
            } else {
                cursor.close();
                return -1;
            }
        } else {
            return -1;
        }
    }

    // make a unique 6-digit personal ID
    public int generateUniquePersonalId() {
        SQLiteDatabase database = this.getReadableDatabase();
        Random random = new Random();
        int id;
        boolean isUnique;

        do {
            id = 100000 + random.nextInt(900000);

            isUnique = isIdUnique(database, id);
        } while (!isUnique);
        return id;
    }

    // checking if a personal ID is unique
    private boolean isIdUnique(SQLiteDatabase database, int id) {
        String query = "SELECT COUNT(*) FROM " + TABLE_USERS + " WHERE " + COLUMN_PERSONAL_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(id)});

        boolean isUnique = false;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            isUnique = count == 0;
        }

        cursor.close();
        return isUnique;
    }

    //logging method for testing/debug
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

                Log.d("User Info", "ID: " + userId + ", Name: " + firstName + " " +
                        lastName + ", Email: " + email + ", Password: " + password + ", Phone: "
                        + phone + ", DOB: " + dob);
            } while (cursor.moveToNext());

            cursor.close();
        } else {
            Log.d("User Info", "No users found.");
        }
    }

    //getting name by persons id
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

    public List<Integer> getUserIdsByFirstName(String firstName) {
        List<Integer> userIds = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT id, first_name, last_name FROM users WHERE first_name LIKE ? ORDER BY last_name ASC";
        Cursor cursor = db.rawQuery(query, new String[]{firstName});

        if (cursor.moveToFirst()) {
            do {
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("personal_id"));
                userIds.add(userId);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userIds;
    }

    //deleting a user from db (for testing)
    public boolean deleteUser(int personalId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_USERS, COLUMN_PERSONAL_ID + "=?", new String[]{String.valueOf(personalId)});
        return rowsAffected > 0;
    }

    //login table functions
    //setting a user as logged in
    public void setUserLoggedIn(String userId, boolean isLoggedIn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("logged_in", isLoggedIn ? 1 : 0);

        db.insertWithOnConflict(TABLE_LOGIN, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    //checking if a user is logged in
    public boolean isUserLoggedIn(String userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_LOGIN, new String[]{"logged_in"},
                "userId" + "=?", new String[]{userId}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            boolean loggedIn = cursor.getInt(0) == 1;
            cursor.close();
            return loggedIn;
        }
        return false;
    }

    //check who is logged in right now
    public int getLoggedInUserId() {
        SQLiteDatabase db = this.getReadableDatabase();
        int userId = -1;

        Cursor cursor = db.query("login", new String[]{"userId"}, "logged_in = ?", new String[]{"1"}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("userId"));
            cursor.close();
        }
        return userId;
    }

    //review table functions
    //adding a review
    public long addReview(Review review) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, review.getUserId());
        values.put(COLUMN_REVIEWER_ID, review.getReviewerId());
        values.put(COLUMN_REVIEW_TEXT, review.getReviewText());
        values.put(COLUMN_RATING, review.getRating());
        values.put(COLUMN_REVIEW_DATE, review.getDate());

        long result = db.insert(TABLE_REVIEWS, null, values);
        return result;
    }

    public boolean deleteReview(int reviewId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_REVIEWS, COLUMN_REVIEW_ID + " = ?",
                new String[]{String.valueOf(reviewId)});
        return rowsAffected > 0;
    }

    // get reviews given userid
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

    // get skills given id
    public List<String> getUserSkills(SQLiteDatabase db, int userId) {
        List<String> skills = new ArrayList<>();
        String userIdString = String.valueOf(userId);

        Cursor cursor = db.query("user_skills",
                new String[]{"skill"},
                "user_id = ?",
                new String[]{userIdString},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                skills.add(cursor.getString(cursor.getColumnIndexOrThrow("skill")));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return skills;
    }

    //get all users skills
    public List<UserSkills> getAllUsersWithSkills() {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT user_id, GROUP_CONCAT(skill) as skills FROM user_skills GROUP BY user_id";

        Cursor cursor = db.rawQuery(query, null);
        List<UserSkills> userList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int personalId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                String skillsString = cursor.getString(cursor.getColumnIndexOrThrow("skills"));

                // Log the userId and skillsString
                Log.d("DatabaseHelper", "Found user with ID: " + personalId + " and skills: " + skillsString);

                // Check if skillsString is valid
                List<String> skills = new ArrayList<>();
                if (skillsString != null && !skillsString.isEmpty()) {
                    skills = Arrays.asList(skillsString.split(","));
                    Log.d("DatabaseHelper", "Skills list: " + skills);
                } else {
                    Log.w("DatabaseHelper", "No skills found for user with ID: " + personalId);
                }

                // Create the UserSkills object and add to the list
                UserSkills user = new UserSkills(personalId, skills);
                userList.add(user);

            } while (cursor.moveToNext());
        } else {
            Log.w("DatabaseHelper", "No users found in user_skills table.");
        }

        cursor.close();
        return userList;
    }

    //update skills method
    public void updateSkills(int userId, List<String> selectedSkills) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys = ON;");

        db.beginTransaction();

        try {
            Cursor cursor = db.query(TABLE_USERS, new String[]{"personal_id"}, COLUMN_PERSONAL_ID + " = ?",
                    new String[]{String.valueOf(userId)}, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                db.delete(TABLE_USER_SKILLS, "user_id = ?", new String[]{String.valueOf(userId)});

                ContentValues values = new ContentValues();
                for (String skill : selectedSkills) {
                    values.clear();
                    values.put("user_id", userId);
                    values.put("skill", skill);
                    db.insert(TABLE_USER_SKILLS, null, values);
                    Log.d("DatabaseHelper", "Inserted skills for user " + userId + ": " + skill);
                }
                db.setTransactionSuccessful();
                cursor.close();
            } else {
                Log.e("DatabaseHelper", "User not found: " + userId);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error updating skills: ", e);
        } finally {
            db.endTransaction();
        }
    }

    //skill logging for testing
    public void logUserSkills(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT " + COLUMN_SKILL + " FROM " + TABLE_USER_SKILLS + " WHERE user_id = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
            if (cursor != null && cursor.moveToFirst()) {
                List<String> skills = new ArrayList<>();
                do {
                    String skill = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKILL));
                    skills.add(skill);
                } while (cursor.moveToNext());

                Log.d("User Skills", "User ID: " + userId + ", Skills: " + skills);
            } else {
                Log.d("User Skills", "No skills found for user ID: " + userId);
            }
        } catch (Exception e) {
            Log.e("Database Error", "Error while querying user skills: ", e);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    //guide functions
    // insert a new guide
    public long addGuide(Guide guide) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, guide.getTitle());
        values.put(COLUMN_DESCRIPTION, guide.getDescription());

        long id = db.insert(TABLE_GUIDES, null, values);
        return id;
    }

    //get a guide by ID
    public Guide getGuide(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GUIDES,
                new String[] { COLUMN_ID, COLUMN_TITLE, COLUMN_DESCRIPTION },
                COLUMN_ID + "=?", new String[] { String.valueOf(id) },
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Guide guide = new Guide(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            );
            cursor.close();
            return guide;
        } else {
            return null;
        }
    }

    //update a guide
    public int updateGuide(Guide guide, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, guide.getTitle());
        values.put(COLUMN_DESCRIPTION, guide.getDescription());

        return db.update(TABLE_GUIDES, values, COLUMN_ID + "=?", new String[] { String.valueOf(id) });
    }

    //delete a guide by ID
    public void deleteGuide(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GUIDES, COLUMN_ID + "=?", new String[] { String.valueOf(id) });
    }

    //get all guides
    public List<Guide> getAllGuides() {
        List<Guide> guideList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GUIDES, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));

                Guide guide = new Guide(title, description);

                guideList.add(guide);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return guideList;
    }

    //event functions
    //insert event
    public void insertEvent(String title, String description, String date, String location) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_EVENT_TITLE, title);
            values.put(COLUMN_EVENT_DESCRIPTION, description);
            values.put(COLUMN_DATE, date);
            values.put(COLUMN_LOCATION, location);
            db.insert(TABLE_EVENTS, null, values);
        }
    }

    //get all events
    public List<Events> getAllEvents() {
        List<Events> eventsList = new ArrayList<>();

        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.query(TABLE_EVENTS, null, null, null, null, null, null)) {

            if (cursor != null) {
                int idIndex = cursor.getColumnIndex(COLUMN_EVENT_ID);
                int titleIndex = cursor.getColumnIndex(COLUMN_EVENT_TITLE);
                int descriptionIndex = cursor.getColumnIndex(COLUMN_EVENT_DESCRIPTION);
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

    //delete events
    public boolean deleteEvent(int eventId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete the event from the database where eventId matches
        int result = db.delete(TABLE_EVENTS, COLUMN_EVENT_ID + " = ?", new String[]{String.valueOf(eventId)});
        db.close();
        return result > 0;  // Returns true if the event was successfully deleted
    }
}