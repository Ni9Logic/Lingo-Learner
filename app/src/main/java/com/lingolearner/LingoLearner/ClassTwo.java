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

public class ClassTwo extends AppCompatActivity {
    ImageView Counting;
    ImageView Vocabulary;
    ImageView Grammar;
    ImageView Writting;
    ImageView Listening;
    ImageView Story;
    ImageView Weather;
    ImageView Seasons;

    TextView heytxtC2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_two);
        TrackActivities.trackActivity("Class Two Activity");

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
        Writting=findViewById(R.id.writting);
        Listening=findViewById(R.id.listening);
        Grammar=findViewById(R.id.grammar);
        Story=findViewById(R.id.stories);
        Weather=findViewById(R.id.weather);
        Seasons=findViewById(R.id.season);

        heytxtC2 = findViewById(R.id.HeytxtC2);


        // Load the animations
        Animation fliprotateAnimation = AnimationUtils.loadAnimation(this, R.anim.fliprotateanim);

        // Apply an animation to the TextView
        heytxtC2.startAnimation(fliprotateAnimation);


        // Start the text animation
        startTextAnimation("Welcome to LingoLearner, Little Explorers in Class2!");

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
    private void startTextAnimation(String text) {
        heytxtC2.setText("");  // Clear the TextView before starting the animation

        Handler handler = new Handler();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Append the next character
                    heytxtC2.append(String.valueOf(text.charAt(index)));
                }
            }, i * 300);  // 250ms delay for each character
        }
    }
}