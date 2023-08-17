package com.example.jeevandan_v2;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.List;

public class activity_allocated_list extends AppCompatActivity {

    private ListView listViewRecipients;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocated_list);

        listViewRecipients = findViewById(R.id.listView_recipients);
        databaseHelper = new DatabaseHelper(this);

        // Retrieve allocated recipients from the database
        List<String> allocatedRecipients = databaseHelper.getAllocatedRecipients();

        // Create an ArrayAdapter to display the recipients in the ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allocatedRecipients);

        // Set the adapter to the ListView
        listViewRecipients.setAdapter(adapter);
    }
}
