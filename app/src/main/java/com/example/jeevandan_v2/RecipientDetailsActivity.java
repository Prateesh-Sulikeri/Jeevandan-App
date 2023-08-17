// RecipientDetailsActivity.java

package com.example.jeevandan_v2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RecipientDetailsActivity extends AppCompatActivity {

    private TextView textRecName, textRecOrganReq, textRecBloodGrp, textRecGender, textRecPhNo, textRecEmail;
    private Button btnAllocOrgan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_details);

        // Initialize views
        textRecName = findViewById(R.id.text_recName);
        textRecOrganReq = findViewById(R.id.text_recOrganReq);
        textRecBloodGrp = findViewById(R.id.text_recBloodGrp);
        textRecGender = findViewById(R.id.text_recGender);
        textRecPhNo = findViewById(R.id.text_recPhNo);
        textRecEmail = findViewById(R.id.text_recEmail);

        btnAllocOrgan = findViewById(R.id.btn_allocOrgan);

        // Get the recipient's information from intent extras
        Intent intent = getIntent();
        if (intent != null) {
            String recipientInfo = intent.getStringExtra("recipient_info");
            if (recipientInfo != null) {
                // Parse the recipient's information
                String[] recipientData = recipientInfo.split(", ");
                String name = recipientData[0].replace("Name: ", "");

                // Fetch recipient's details from the database
                DatabaseHelper dbHelper = new DatabaseHelper(this);
                Cursor cursor = dbHelper.getRecipientDetails(name);

                if (cursor != null && cursor.moveToFirst()) {
                    // Verify the column indices
                    int genderIndex = cursor.getColumnIndex(DatabaseHelper.KEY_GENDER);
                    int organRequiredIndex = cursor.getColumnIndex(DatabaseHelper.KEY_ORGAN_REQUIRED);
                    int bloodGroupIndex = cursor.getColumnIndex(DatabaseHelper.KEY_BLOOD_GROUP);
                    int ageIndex = cursor.getColumnIndex(DatabaseHelper.KEY_AGE);
                    int emailIndex = cursor.getColumnIndex(DatabaseHelper.KEY_EMAIL);
                    int phoneNumberIndex = cursor.getColumnIndex(DatabaseHelper.KEY_PHONE_NUMBER);

                    if (genderIndex >= 0 && organRequiredIndex >= 0 && bloodGroupIndex >= 0 &&
                            ageIndex >= 0 && emailIndex >= 0 && phoneNumberIndex >= 0) {
                        // Extract recipient's details from the cursor
                        String gender = cursor.getString(genderIndex);
                        String organRequired = cursor.getString(organRequiredIndex);
                        String bloodGroup = cursor.getString(bloodGroupIndex);
                        int age = cursor.getInt(ageIndex);
                        String email = cursor.getString(emailIndex);
                        String phoneNumber = cursor.getString(phoneNumberIndex);

                        // Set the data to the TextViews
                        textRecName.setText(name);
                        textRecOrganReq.setText(organRequired);
                        textRecBloodGrp.setText(bloodGroup);
                        textRecGender.setText(gender);
                        textRecPhNo.setText(phoneNumber);
                        textRecEmail.setText(email);

                        btnAllocOrgan.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Update the allocation status in the database
                                dbHelper.updateAllocationStatus(name);

                                // Show a toast message or perform any other action
                                Toast.makeText(RecipientDetailsActivity.this, "Organ Allocated", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                // Close the cursor
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }
}
