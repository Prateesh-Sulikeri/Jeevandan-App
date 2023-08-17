package com.example.jeevandan_v2;

//import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;


public class activity_login extends AppCompatActivity {

    ImageButton btnDocLogin ;
    ImageButton btnRecLogin ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);

        btnDocLogin = (ImageButton) findViewById(R.id.btn_doc);
        btnRecLogin = (ImageButton) findViewById(R.id.btn_rec);

//        now we are seeting what to happen on the button click
// First button

        btnDocLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent it = new Intent(activity_login.this,activity_docter_login.class);
                startActivity(it);
            }
        });

        btnRecLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent it = new Intent(activity_login.this,activity_recipient_login.class);
                startActivity(it);
            }
        });
    }


}


