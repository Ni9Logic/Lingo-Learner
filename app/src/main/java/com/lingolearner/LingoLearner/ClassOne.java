package com.lingolearner.LingoLearner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ClassOne extends AppCompatActivity {

    ImageView Grammar;
    ImageView Vocabulary;
    ImageView Counting;
    ImageView BirdsName;
    ImageView BodyParts;
    ImageView WeekDays;
    ImageView Months;
    ImageView Writting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_one);
        TrackActivities.trackActivity("Class One Activity");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        Months = findViewById(R.id.months);
        Counting= findViewById(R.id.counting);
        WeekDays= findViewById(R.id.weekdays);
        BirdsName = findViewById(R.id.birdsname);
        BodyParts= findViewById(R.id.bodyparts);
        Grammar= findViewById(R.id.grammar);
        Vocabulary= findViewById(R.id.vocabulary);
        Writting=findViewById(R.id.writting);

        Months.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassOne.this, OneMonthsname.class);
                startActivity(intent);
            }
        });

        WeekDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassOne.this, OneWeekdays.class);
                startActivity(intent);
            }
        });


        Counting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassOne.this, OneCounting.class);
                startActivity(intent);
            }
        });

        Vocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassOne.this, OneVocabulary.class);
                startActivity(intent);
            }
        });

        Grammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassOne.this, OneGrammer.class);
                startActivity(intent);
            }
        });


        BirdsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassOne.this, OneBirdsName.class);
                startActivity(intent);
            }
        });

        BodyParts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassOne.this, OneBodyParts.class);
                startActivity(intent);
            }
        });

        Writting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassOne.this, OneWritting.class);
                startActivity(intent);
            }
        });
    }
}