// activity_dashboard.java

package com.example.jeevandan_v2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class activity_dashboard extends AppCompatActivity {
    private ListView listViewRecipients;

    Button btnAlloc;
    private List<String> recipientsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        listViewRecipients = findViewById(R.id.listView_recipients);

        btnAlloc = findViewById(R.id.btn_alloList);

        btnAlloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent it = new Intent(activity_dashboard.this, activity_allocated_list.class);
                startActivity(it);
            }
        });

        // Retrieve recipient data from the database
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        recipientsList = dbHelper.getAllRecipients();

        // Create an ArrayAdapter to display the recipient data in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, recipientsList);
        listViewRecipients.setAdapter(adapter);

        // Set item click listener
        listViewRecipients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the recipient's information
                String recipientInfo = recipientsList.get(position);

                // Open RecipientDetailsActivity and pass the recipient's information
                Intent intent = new Intent(activity_dashboard.this, RecipientDetailsActivity.class);
                intent.putExtra("recipient_info", recipientInfo);
                startActivity(intent);
            }
        });
    }
}
