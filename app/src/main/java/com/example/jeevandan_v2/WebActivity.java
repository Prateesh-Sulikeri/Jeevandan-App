package com.example.jeevandan_v2;

import static com.example.jeevandan_v2.R.id.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class WebActivity extends AppCompatActivity {

    WebView webView;
    TextView report;

    Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        report = findViewById(R.id.txt_report);
        close = findViewById(R.id.btn_close);
        webView = findViewById(webview);

        report.setText(getIntent().getStringExtra("message6"));

        String s1 = report.getText().toString();

        webView.loadUrl(s1);


        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent it =  new Intent(WebActivity.this,activity_recipient_profile.class);
                startActivity(it);
            }
        });

    }

}