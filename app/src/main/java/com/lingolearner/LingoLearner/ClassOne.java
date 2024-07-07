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

public class ClassOne extends AppCompatActivity {

    ImageView Grammar;
    ImageView Vocabulary;
    ImageView Counting;
    ImageView BirdsName;
    ImageView BodyParts;
    ImageView WeekDays;
    ImageView Months;
    ImageView Writting;

    TextView heytxtC1;

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

        heytxtC1 = findViewById(R.id.HeytxtC1);

        // Load the animations
        Animation fliprotateAnimation = AnimationUtils.loadAnimation(this, R.anim.fliprotateanim);

        // Apply an animation to the TextView
        heytxtC1.startAnimation(fliprotateAnimation);


        // Start the text animation
        startTextAnimation("Welcome to LingoLearner, Little Explorers in ClassOne!");

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

    private void startTextAnimation(String text) {
        heytxtC1.setText("");  // Clear the TextView before starting the animation

        Handler handler = new Handler();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Append the next character
                    heytxtC1.append(String.valueOf(text.charAt(index)));
                }
            }, i * 300);  // 250ms delay for each character
        }
    }
}