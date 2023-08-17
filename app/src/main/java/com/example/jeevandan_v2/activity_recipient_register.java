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

public class activity_recipient_register extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner recGender, recOrgReq, recBloodGrp;
    EditText recName, recAge, recEmail, recPhNo, reportCard;
    ImageButton signup, signin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipient_register);

        recName = findViewById(R.id.edit_recName);
        recGender = findViewById(R.id.spinner_recGender);
        recOrgReq = findViewById(R.id.spinner_recOrgReq);
        recBloodGrp = findViewById(R.id.spinner_recBloodGrp);
        recAge = findViewById(R.id.edit_age);
        recEmail = findViewById(R.id.edit_Email);
        recPhNo = findViewById(R.id.edit_recPhNo);

        signup = findViewById(R.id.btn_signup);
        signin = findViewById(R.id.btn_signin);


        String gender = String.valueOf(getResources().getStringArray(R.array.rec_gender));
        String orgReq = String.valueOf(getResources().getStringArray(R.array.rec_orgon));

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.rec_gender,android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.rec_orgon, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.rec_blood, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        recGender.setAdapter(adapter1);
        recOrgReq.setAdapter(adapter2);
        recBloodGrp.setAdapter(adapter3);

        recGender.setOnItemSelectedListener(this);
        recOrgReq.setOnItemSelectedListener(this);
        recBloodGrp.setOnItemSelectedListener(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the input values
                String name = recName.getText().toString().trim();
                String gender = recGender.getSelectedItem().toString();
                String organRequired = recOrgReq.getSelectedItem().toString();
                String bloodGroup = recBloodGrp.getSelectedItem().toString();
                int age = Integer.parseInt(recAge.getText().toString().trim());
                String email = recEmail.getText().toString().trim();
                String phoneNumber = recPhNo.getText().toString().trim();

                // Create an instance of the DatabaseHelper class
                DatabaseHelper dbHelper = new DatabaseHelper(activity_recipient_register.this);

                // Insert the data into the Recipients table
                long result = dbHelper.insertRecipient(name, gender, organRequired, bloodGroup, age, email, phoneNumber);

                // Check if insertion was successful
                if (result != -1) {
                    Toast.makeText(activity_recipient_register.this, "Registration successful", Toast.LENGTH_SHORT).show();

                    // Redirect to the recipient profile activity
                    Intent intent = new Intent(activity_recipient_register.this, activity_recipient_profile.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(activity_recipient_register.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        signin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent it = new Intent(activity_recipient_register.this,activity_recipient_login.class);
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