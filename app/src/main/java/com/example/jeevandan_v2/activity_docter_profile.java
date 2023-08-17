package com.example.jeevandan_v2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class activity_docter_profile extends AppCompatActivity {

    private TextView docName, Spec, HospName, docUsername;
    ImageButton docLogout;
    DatabaseHelper dbHelper;
    String loggedInUsername; // Store the username of the currently logged-in doctor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_profile);

        docName = findViewById(R.id.text_docName);
        Spec = findViewById(R.id.text_Spec);
        HospName = findViewById(R.id.text_HospName);
        docUsername = findViewById(R.id.text_docUsername);

        docLogout = findViewById(R.id.btn_docLogout);

        dbHelper = new DatabaseHelper(this);

        docName.setTextColor(Color.BLACK);
        Spec.setTextColor(Color.BLACK);
        HospName.setTextColor(Color.BLACK);
        docUsername.setTextColor(Color.BLACK);


        // Get the username of the currently logged-in doctor from the previous activity
        Intent intent = getIntent();
        loggedInUsername = intent.getStringExtra("USERNAME");

        // Retrieve the doctor's details from the database and display them
        displayDoctorDetails();

        docLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Perform logout action and navigate to the login activity
                Intent intent = new Intent(activity_docter_profile.this, activity_docter_login.class);
                startActivity(intent);
                finish(); // Optional: Finish the current activity to prevent going back to the profile page after logging out
            }
        });
    }

    private void displayDoctorDetails() {
        // Retrieve the doctor's details from the database based on the logged-in username
        Cursor cursor = dbHelper.getUserCredentials(loggedInUsername, true);

        if (cursor != null && cursor.moveToFirst()) {
            String fullName = "";
            String specialization = "";
            String hospitalName = "";
            String username = "";

            int fullNameIndex = cursor.getColumnIndex(DatabaseHelper.KEY_FULL_NAME);
            int specializationIndex = cursor.getColumnIndex(DatabaseHelper.KEY_SPECIALIZATION);
            int hospitalNameIndex = cursor.getColumnIndex(DatabaseHelper.KEY_HOSPITAL_NAME);
            int usernameIndex = cursor.getColumnIndex(DatabaseHelper.KEY_USERNAME);

            if (fullNameIndex != -1)
                fullName = cursor.getString(fullNameIndex);
            if (specializationIndex != -1)
                specialization = cursor.getString(specializationIndex);
            if (hospitalNameIndex != -1)
                hospitalName = cursor.getString(hospitalNameIndex);
            if (usernameIndex != -1)
                username = cursor.getString(usernameIndex);

            docName.setText(fullName);
            Spec.setText(specialization);
            HospName.setText(hospitalName);
            docUsername.setText(username);
        } else {
            // No doctor found with the logged-in username or cursor is null
            Toast.makeText(this, "Doctor details not found", Toast.LENGTH_SHORT).show();
        }

        // Close the cursor
        if (cursor != null) {
            cursor.close();
        }
    }


}
