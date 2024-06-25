package com.example.LingoLearner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_three);

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
}
