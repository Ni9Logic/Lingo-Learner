package com.lingolearner.LingoLearner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

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

    TextView heytxtCP;

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

        heytxtCP = findViewById(R.id.HeytxtCP);

        // Load the animations
        Animation fliprotateAnimation = AnimationUtils.loadAnimation(this, R.anim.fliprotateanim);

        // Apply an animation to the TextView
        heytxtCP.startAnimation(fliprotateAnimation);


        // Start the text animation
        startTextAnimation("Welcome to LingoLearner, Little Explorers in ClassPrep!");


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

    private void startTextAnimation(String text) {
        heytxtCP.setText("");  // Clear the TextView before starting the animation

        Handler handler = new Handler();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Append the next character
                    heytxtCP.append(String.valueOf(text.charAt(index)));
                }
            }, i * 300);  // 250ms delay for each character
        }
    }
}