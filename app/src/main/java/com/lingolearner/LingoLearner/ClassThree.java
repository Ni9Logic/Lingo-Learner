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

public class ClassThree extends AppCompatActivity {
    ImageView Drawing;
    ImageView Vocabulary;
    ImageView Grammar;
    ImageView Reading;
    ImageView Writting;
    ImageView Story;
    ImageView Game;

    TextView heytxtC3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_three);
        TrackActivities.trackActivity("Class Three Acitvity");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        Drawing = findViewById(R.id.drawing);
        Vocabulary = findViewById(R.id.vocab);
        Reading=findViewById(R.id.reading);
        Writting=findViewById(R.id.writting);
        Grammar=findViewById(R.id.grammar);
        Story=findViewById(R.id.story);
        Game=findViewById(R.id.game);
        heytxtC3 = findViewById(R.id.HeytxtC3);

        // Load the animations
        Animation fliprotateAnimation = AnimationUtils.loadAnimation(this, R.anim.fliprotateanim);

        // Apply an animation to the TextView
        heytxtC3.startAnimation(fliprotateAnimation);


        // Start the text animation
        startTextAnimation("Welcome to LingoLearner, Little Explorers in Class3!");

        Drawing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassThree.this, ThreeDrawing.class);
                startActivity(intent);
            }
        });


        Vocabulary.setOnClickListener(v -> {
            Intent intent = new Intent(ClassThree.this, ThreeVocabulary.class);
            startActivity(intent);
        });

        Reading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassThree.this, ThreeReading.class);
                startActivity(intent);
            }
        });

        Writting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassThree.this, ThreeWritting.class);
                startActivity(intent);
            }
        });


        Story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassThree.this, ThreeStory.class);
                startActivity(intent);
            }
        });

        Grammar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassThree.this, ThreeGrammar.class);
                startActivity(intent);
            }
        });


        Game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassThree.this, ThreeGame.class);
                startActivity(intent);
            }
        });

    }
    private void startTextAnimation(String text) {
        heytxtC3.setText("");  // Clear the TextView before starting the animation

        Handler handler = new Handler();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Append the next character
                    heytxtC3.append(String.valueOf(text.charAt(index)));
                }
            }, i * 300);  // 250ms delay for each character
        }
    }
}
