package com.example.jeevandan_v2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_recipient_login extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhoneNumber;
    private ImageButton btnSignIn;
    private ImageButton btnSignUp;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_login);

        editTextName = findViewById(R.id.edit_recpName);
        editTextPhoneNumber = findViewById(R.id.edit_recpPhNo);
        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);
        databaseHelper = new DatabaseHelper(this);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();

                if (name.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(activity_recipient_login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    loginRecipient(name, phoneNumber);
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_recipient_login.this, activity_recipient_register.class);
                startActivity(intent);
            }
        });
    }

    private void loginRecipient(String name, String phoneNumber) {
        // Get the cursor containing the recipient details
        Cursor cursor = databaseHelper.getRecipientDetails(name);

        // Check if the cursor has data and the recipient name and phone number column indices are valid
        if (cursor != null && cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(DatabaseHelper.KEY_FULL_NAME);
            int phoneNumberColumnIndex = cursor.getColumnIndex(DatabaseHelper.KEY_PHONE_NUMBER);

            if (nameColumnIndex >= 0 && phoneNumberColumnIndex >= 0) {
                // Retrieve the stored name and phone number from the cursor
                String storedName = cursor.getString(nameColumnIndex);
                String storedPhoneNumber = cursor.getString(phoneNumberColumnIndex);

                // Check if the entered name and phone number match the stored values
                if (name.equalsIgnoreCase(storedName) && phoneNumber.equals(storedPhoneNumber)) {
                    // Name and phone number matched, login successful
                    cursor.close();

                    // Start the recipient profile activity
                    Intent intent = new Intent(activity_recipient_login.this, activity_recipient_profile.class);
                    intent.putExtra("name", name);
                    intent.putExtra("phoneNumber", phoneNumber);
                    startActivity(intent);

                    // Finish the current activity
                    finish();
                } else {
                    // Name or phone number does not match, show an error message
                    Toast.makeText(activity_recipient_login.this, "Invalid name or phone number", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Invalid column index for name or phone number, show an error message
                Toast.makeText(activity_recipient_login.this, "Invalid name or phone number", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Cursor is empty, show an error message
            Toast.makeText(activity_recipient_login.this, "Invalid name or phone number", Toast.LENGTH_SHORT).show();
        }

        // Close the cursor and database connection
        if (cursor != null)
            cursor.close();
        databaseHelper.close();

    }
}
