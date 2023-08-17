package com.example.jeevandan_v2;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Collections;


public class activity_docter_register extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner specilization, hospitalName;
    EditText docName, docUsername, docPass, docConfPass;
    ImageButton signup, signin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docter_register);

        docName = findViewById(R.id.edit_docName);
        specilization = findViewById(R.id.spinner_docSpecilization);
        hospitalName = findViewById(R.id.spinner_hospName);
        docUsername = findViewById(R.id.edit_docUsername);
        docPass = findViewById(R.id.edit_docPass);
        docConfPass = findViewById(R.id.edit_docConfPass);
        signup = findViewById(R.id.btn_signup);
        signin = findViewById(R.id.btn_signin);

        String Spec = String.valueOf(getResources().getStringArray(R.array.doc_spec));
        String hosN = String.valueOf(getResources().getStringArray(R.array.doc_hospitalName));

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.doc_spec, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.doc_hospitalName, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        specilization.setAdapter(adapter1);
        hospitalName.setAdapter(adapter2);

        specilization.setOnItemSelectedListener(this);
        hospitalName.setOnItemSelectedListener(this);

        signup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Get the input values
                        String name = docName.getText().toString().trim();
                        String specialization = specilization.getSelectedItem().toString();
                        String hospital = hospitalName.getSelectedItem().toString();
                        String username = docUsername.getText().toString().trim();
                        String password = docPass.getText().toString().trim();
                        String confirmPassword = docConfPass.getText().toString().trim();

                        // Check if passwords match
                        if (!password.equals(confirmPassword)) {
                            Toast.makeText(activity_docter_register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Create an instance of the DatabaseHelper class
                        DatabaseHelper dbHelper = new DatabaseHelper(activity_docter_register.this);

                        // Insert the data into the Doctors table
                        long result = dbHelper.insertDoctor(name, specialization, hospital, username, password);

                        // Check if insertion was successful
                        if (result != -1) {
                            Toast.makeText(activity_docter_register.this, "Registration successful", Toast.LENGTH_SHORT).show();

                            // Redirect to the dashboard activity
                            Intent intent = new Intent(activity_docter_register.this, activity_docter_login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(activity_docter_register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent it = new Intent(activity_docter_register.this,activity_docter_login.class);
                startActivity(it);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}