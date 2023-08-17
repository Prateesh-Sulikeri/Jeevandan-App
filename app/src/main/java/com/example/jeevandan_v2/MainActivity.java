package com.example.jeevandan_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {


    ImageButton mainPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPage = (ImageButton) findViewById(R.id.imageView5);

        mainPage.setOnClickListener(v -> {
            // get the value which input by user in EditText and convert it to string

            // Create the Intent object of this class Context() to Second_activity class
            Intent intent = new Intent(getApplicationContext(), activity_login.class);
            // now by putExtra method put the value in key, value pair key is
            // message_key by this key we will receive the value, and put the string

            // start the Intent
            startActivity(intent);
        });

    }


}