package com.example.jeevandan_v2;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_recipient_profile extends AppCompatActivity {

    private TextView textViewRecName;
    private TextView textViewRecOrganReq;
    private TextView textViewRecBloodGrp;
    private TextView textViewRecGender;
    private TextView textViewRecPhNo;
    private TextView textViewRecEmail;
    private TextView textViewAllocStatus;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_profile);

        textViewRecName = findViewById(R.id.text_recName);
        textViewRecOrganReq = findViewById(R.id.text_recOrganReq);
        textViewRecBloodGrp = findViewById(R.id.text_recBloodGrp);
        textViewRecGender = findViewById(R.id.text_recGender);
        textViewRecPhNo = findViewById(R.id.text_recPhNo);
        textViewRecEmail = findViewById(R.id.text_recEmail);

        databaseHelper = new DatabaseHelper(this);

        // Retrieve the recipient's name and phone number from the intent
        String name = getIntent().getStringExtra("name");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");

        // Retrieve recipient information from the database
        Cursor cursor = databaseHelper.getRecipientDetails(name, phoneNumber);
        if (cursor != null && cursor.moveToFirst()) {
            int organReqColumnIndex = cursor.getColumnIndex(DatabaseHelper.KEY_ORGAN_REQUIRED);
            int bloodGrpColumnIndex = cursor.getColumnIndex(DatabaseHelper.KEY_BLOOD_GROUP);
            int genderColumnIndex = cursor.getColumnIndex(DatabaseHelper.KEY_GENDER);
            int emailColumnIndex = cursor.getColumnIndex(DatabaseHelper.KEY_EMAIL);

            if (organReqColumnIndex >= 0 && bloodGrpColumnIndex >= 0 && genderColumnIndex >= 0 &&
                    emailColumnIndex >= 0) {
                // Retrieve recipient information from the cursor
                String organRequired = cursor.getString(organReqColumnIndex);
                String bloodGroup = cursor.getString(bloodGrpColumnIndex);
                String gender = cursor.getString(genderColumnIndex);
                String email = cursor.getString(emailColumnIndex);

                // Set the recipient information in the TextViews
                textViewRecName.setText(name);
                textViewRecOrganReq.setText(organRequired);
                textViewRecBloodGrp.setText(bloodGroup);
                textViewRecGender.setText(gender);
                textViewRecPhNo.setText(phoneNumber);
                textViewRecEmail.setText(email);

            } else {
                Toast.makeText(this, "Invalid column indices", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Recipient details not found", Toast.LENGTH_SHORT).show();
        }

        // Close the cursor and database connection
        if (cursor != null)
            cursor.close();
        databaseHelper.close();
    }
}