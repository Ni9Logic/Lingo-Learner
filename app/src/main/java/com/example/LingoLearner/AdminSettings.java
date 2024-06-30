package com.example.LingoLearner;

import android.content.Intent;
import android.os.Bundle;

import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class AdminSettings extends AppCompatActivity {
    CardView createAnnouncements;
    CardView generateReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        createAnnouncements = findViewById(R.id.Create_Announcement);
        generateReports = findViewById(R.id.activity_Admin_Generate_Reports);

        createAnnouncements.setOnClickListener(v -> {
            Intent intent = new Intent(AdminSettings.this, AdminSendAnnouncements.class);
            startActivity(intent);
        });

        generateReports.setOnClickListener(v -> {
            Intent intent = new Intent(AdminSettings.this, AdminGenerateReportUsers.class);
            startActivity(intent);
        });

        // Retrieve the user's email from the database
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


    }
}