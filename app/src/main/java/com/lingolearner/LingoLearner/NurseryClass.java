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

public class NurseryClass extends AppCompatActivity {

    ImageView Alphabet;
    ImageView Counting;
    ImageView Colors;
    ImageView Shape;
    ImageView Phonics;

    TextView heytxtCN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursery_class);
        TrackActivities.trackActivity("Nursery Class Activity");
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
        Colors= findViewById(R.id.colors);
        Shape = findViewById(R.id.shapes);
        Phonics = findViewById(R.id.phonics);

        heytxtCN = findViewById(R.id.titleCN);

        // Load the animations
        Animation fliprotateAnimation = AnimationUtils.loadAnimation(this, R.anim.fliprotateanim);

        // Apply an animation to the TextView
        heytxtCN.startAnimation(fliprotateAnimation);


        // Start the text animation
        startTextAnimation("Welcome to LingoLearner, Little Explorers!");



        Alphabet.setOnClickListener(v -> {
            Intent intent = new Intent(NurseryClass.this, NurseryAlphabets.class);
            startActivity(intent);
        });

        Counting.setOnClickListener(v -> {
            Intent intent = new Intent(NurseryClass.this, NurseryCounting.class);
            startActivity(intent);
        });

        Colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NurseryClass.this, NurseryColors.class);
                startActivity(intent);
            }
        });

        Shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NurseryClass.this, NurseryShapes.class);
                startActivity(intent);
            }
        });

        Phonics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NurseryClass.this, NurseryPhonics.class);
                startActivity(intent);
            }
        });

    }
    private void startTextAnimation(String text) {
        heytxtCN.setText("");  // Clear the TextView before starting the animation

        Handler handler = new Handler();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Append the next character
                    heytxtCN.append(String.valueOf(text.charAt(index)));
                }
            }, i * 300);  // 250ms delay for each character
        }
    }
}


