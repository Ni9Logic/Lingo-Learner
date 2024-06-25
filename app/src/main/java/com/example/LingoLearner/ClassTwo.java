package com.example.LingoLearner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ClassTwo extends AppCompatActivity {
    ImageView Counting;
    ImageView Vocabulary;
    ImageView Grammar;
    ImageView Reading;
    ImageView Writting;
    ImageView Listening;
    ImageView Story;
    ImageView Weather;
    ImageView Seasons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_two);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        Counting= findViewById(R.id.counting);
        Vocabulary=findViewById(R.id.vocabulary1);
        Reading=findViewById(R.id.reading);
        Writting=findViewById(R.id.writting);
        Listening=findViewById(R.id.listening);
        Grammar=findViewById(R.id.grammar);
        Story=findViewById(R.id.stories);
        Weather=findViewById(R.id.weather);
        Seasons=findViewById(R.id.season);




        Counting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassTwo.this, TwoCounting.class);
                startActivity(intent);
            }
        });

        Vocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassTwo.this, TwoVocabulary.class);
                startActivity(intent);
            }
        });

        Reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassTwo.this, TwoReading.class);
                startActivity(intent);
            }
        });

        Writting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassTwo.this, TwoWritting.class);
                startActivity(intent);
            }
        });

        Listening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassTwo.this, TwoListening.class);
                startActivity(intent);
            }
        });

        Story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassTwo.this, TwoStory.class);
                startActivity(intent);
            }
        });

        Grammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassTwo.this, TwoGrammer.class);
                startActivity(intent);
            }
        });

        Weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassTwo.this, Weather.class);
                startActivity(intent);
            }
        });

        Seasons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassTwo.this, Seasons.class);
                startActivity(intent);
            }
        });

    }
}