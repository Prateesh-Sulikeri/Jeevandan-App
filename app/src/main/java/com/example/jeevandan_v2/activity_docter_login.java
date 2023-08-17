package com.example.jeevandan_v2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_docter_login extends AppCompatActivity {

    private EditText editDocUser, editDocPass;
    private ImageButton btnSignin, btnSignup;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_login);

        editDocUser = findViewById(R.id.edit_docUser);
        editDocPass = findViewById(R.id.edit_docPass);
        btnSignin = findViewById(R.id.btn_signin);
        btnSignup = findViewById(R.id.btn_signup);

        dbHelper = new DatabaseHelper(this);

//        btnSignin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String username = editDocUser.getText().toString().trim();
//                String password = editDocPass.getText().toString().trim();
//
//                Cursor cursor = dbHelper.getUserCredentials(username, true);
//
//                if (cursor != null && cursor.moveToFirst()) {
//                    try {
//                        int passwordColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.KEY_PASSWORD);
//
//                        String storedPassword = cursor.getString(passwordColumnIndex);
//
//                        if (password.equals(storedPassword)) {
//                            // Passwords match, login successful
//                            Toast.makeText(activity_docter_login.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(activity_docter_login.this, activity_dashboard.class);
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            // Passwords do not match, login failed
//                            Toast.makeText(activity_docter_login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
//                        }
//                    } catch (IllegalArgumentException e) {
//                        // KEY_PASSWORD column not found in the cursor
//                        Toast.makeText(activity_docter_login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    // No user found with the entered username
//                    Toast.makeText(activity_docter_login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
//                }
//
//                if (cursor != null) {
//                    cursor.close();
//                }
//            }
//        });



        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_docter_login.this, activity_dashboard.class);
                startActivity(intent);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_docter_login.this, activity_docter_register.class);
                startActivity(intent);
            }
        });
    }
}
