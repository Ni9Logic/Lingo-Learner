package com.example.LingoLearner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class TwoReading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_reading);
        TrackActivities.trackActivity("Reading Activity");

    }
}