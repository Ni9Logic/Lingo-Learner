package com.example.LingoLearner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class PrepClass extends AppCompatActivity {

    ImageView Alphabet;
    ImageView Counting;
    ImageView Vegetables;
    ImageView Fruits;
    ImageView Phonics;
    ImageView Rhythms;
    ImageView FunActivity;
    ImageView AnimalsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prep_class);
        TrackActivities.trackActivity("Prep Class Activity");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        Alphabet = findViewById(R.id.alphabets);
        Counting= findViewById(R.id.counting);
        Vegetables= findViewById(R.id.vegetables);
        Fruits = findViewById(R.id.fruits);
        Phonics = findViewById(R.id.phonics);
        Rhythms= findViewById(R.id.rhythm);
        FunActivity= findViewById(R.id.funactivity);
        AnimalsName= findViewById(R.id.animalsname);


        Alphabet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrepClass.this, PrepAlphabets.class);
                startActivity(intent);
            }
        });

        Counting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrepClass.this, PrepCounting.class);
                startActivity(intent);
            }
        });


        Vegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrepClass.this, PrepVegName.class);
                startActivity(intent);
            }
        });


        Phonics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrepClass.this, PrepPhonics.class);
                startActivity(intent);
            }
        });


        Rhythms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrepClass.this, PrepRhythm.class);
                startActivity(intent);
            }
        });


        Fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrepClass.this, PrepFruitsName.class);
                startActivity(intent);
            }
        });

        FunActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrepClass.this, NFunActivity.class);
                startActivity(intent);
            }
        });

        AnimalsName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrepClass.this, PrepAnimalsName.class);
                startActivity(intent);
            }
        });

    }
}