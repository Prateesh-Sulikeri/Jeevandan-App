package com.example.jeevandan_v2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "jeevandan_v2.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_DOCTORS = "Doctors";
    public static final String TABLE_RECIPIENTS = "Recipients";

    // Common column names
    public static final String KEY_ID = "id";
    public static final String KEY_FULL_NAME = "full_name";

    // Doctors Table - column names
    public static final String KEY_SPECIALIZATION = "specialization";
    public static final String KEY_HOSPITAL_NAME = "hospital_name";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    // Recipients Table - column names
    public static final String KEY_GENDER = "gender";
    public static final String KEY_ORGAN_REQUIRED = "organ_required";
    public static final String KEY_BLOOD_GROUP = "blood_group";
    public static final String KEY_AGE = "age";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_ALLOCATION_STATUS = "allocation_status";
    public static final String KEY_SUPER_URGENT_STATUS = "super_urgent_status";

    // Create table statements
    private static final String CREATE_TABLE_DOCTORS = "CREATE TABLE " + TABLE_DOCTORS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_FULL_NAME + " TEXT,"
            + KEY_SPECIALIZATION + " TEXT,"
            + KEY_HOSPITAL_NAME + " TEXT,"
            + KEY_USERNAME + " TEXT,"
            + KEY_PASSWORD + " TEXT"
            + ")";

    private static final String CREATE_TABLE_RECIPIENTS = "CREATE TABLE " + TABLE_RECIPIENTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_FULL_NAME + " TEXT,"
            + KEY_GENDER + " TEXT,"
            + KEY_ORGAN_REQUIRED + " TEXT,"
            + KEY_BLOOD_GROUP + " TEXT,"
            + KEY_AGE + " INTEGER,"
            + KEY_EMAIL + " TEXT,"
            + KEY_PHONE_NUMBER + " TEXT,"
            + KEY_ALLOCATION_STATUS + " TEXT,"
            + KEY_SUPER_URGENT_STATUS + " TEXT"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating required tables
        db.execSQL(CREATE_TABLE_DOCTORS);
        db.execSQL(CREATE_TABLE_RECIPIENTS);
    }

    public long insertDoctor(String name, String specialization, String hospital, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FULL_NAME, name);
        values.put(KEY_SPECIALIZATION, specialization);
        values.put(KEY_HOSPITAL_NAME, hospital);
        values.put(KEY_USERNAME, username);
        values.put(KEY_PASSWORD, password);

        // Insert the row
        long id = db.insert(TABLE_DOCTORS, null, values);

        // Close the database connection
        db.close();

        return id;
    }

    public long insertRecipient(String name, String gender, String organRequired, String bloodGroup, int age, String email, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FULL_NAME, name);
        values.put(KEY_GENDER, gender);
        values.put(KEY_ORGAN_REQUIRED, organRequired);
        values.put(KEY_BLOOD_GROUP, bloodGroup);
        values.put(KEY_AGE, age);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PHONE_NUMBER, phoneNumber);

        // Insert the row
        long id = db.insert(TABLE_RECIPIENTS, null, values);

        // Close the database connection
        db.close();

        return id;
    }

    public Cursor getUserCredentials(String username, boolean isDoctor) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tableName = isDoctor ? TABLE_DOCTORS : TABLE_RECIPIENTS;

        String[] columns = {KEY_PASSWORD};
        String selection = KEY_USERNAME + " = ?";
        String[] selectionArgs = {username};

        return db.query(tableName, columns, selection, selectionArgs, null, null, null);
    }


    public List<String> getAllRecipients() {
        List<String> recipientsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_FULL_NAME, KEY_ORGAN_REQUIRED};
        Cursor cursor = db.query(TABLE_RECIPIENTS, columns, null, null, null, null, null);

        int nameIndex = cursor.getColumnIndex(KEY_FULL_NAME);
        int organRequiredIndex = cursor.getColumnIndex(KEY_ORGAN_REQUIRED);

        if (nameIndex >= 0 && organRequiredIndex >= 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(nameIndex);
                String organRequired = cursor.getString(organRequiredIndex);

                String recipientInfo = "Name: " + name + ", Organs Required: " + organRequired;
                recipientsList.add(recipientInfo);
            }
        }

        cursor.close();
        db.close();

        return recipientsList;
    }

    public Cursor getRecipientDetails(String recipientName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {
                KEY_FULL_NAME,
                KEY_GENDER,
                KEY_ORGAN_REQUIRED,
                KEY_BLOOD_GROUP,
                KEY_AGE,
                KEY_EMAIL,
                KEY_PHONE_NUMBER
        };

        String selection = KEY_FULL_NAME + " = ?";
        String[] selectionArgs = {recipientName};

        return db.query(TABLE_RECIPIENTS, columns, selection, selectionArgs, null, null, null);
    }


    public void updateAllocationStatus(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ALLOCATION_STATUS, "1"); // Set allocation status to 1

        String whereClause = KEY_FULL_NAME + " = ?";
        String[] whereArgs = {name};

        // Update the row
        db.update(TABLE_RECIPIENTS, values, whereClause, whereArgs);

        // Close the database connection
        db.close();
    }

    public List<String> getAllocatedRecipients() {
        List<String> recipientsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {KEY_FULL_NAME, KEY_ORGAN_REQUIRED};
        String selection = KEY_ALLOCATION_STATUS + " = ?";
        String[] selectionArgs = {"1"}; // Select recipients with allocation_status = 1

        Cursor cursor = db.query(TABLE_RECIPIENTS, columns, selection, selectionArgs, null, null, null);

        int nameIndex = cursor.getColumnIndex(KEY_FULL_NAME);
        int organRequiredIndex = cursor.getColumnIndex(KEY_ORGAN_REQUIRED);

        if (nameIndex >= 0 && organRequiredIndex >= 0) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(nameIndex);
                String organRequired = cursor.getString(organRequiredIndex);

                String recipientInfo = "Name: " + name + ", Organs Required: " + organRequired;
                recipientsList.add(recipientInfo);
            }
        }

        cursor.close();
        db.close();

        return recipientsList;
    }


    public Cursor getRecipientDetails(String name, String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                KEY_ORGAN_REQUIRED,
                KEY_BLOOD_GROUP,
                KEY_GENDER,
                KEY_EMAIL
        };
        String selection = KEY_FULL_NAME + " = ? AND " + KEY_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {name, phoneNumber};
        return db.query(TABLE_RECIPIENTS, projection, selection, selectionArgs, null, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPIENTS);

        // Create tables again
        onCreate(db);
    }


}

