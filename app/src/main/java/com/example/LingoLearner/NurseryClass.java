package com.example.LingoLearner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class NurseryClass extends AppCompatActivity {

    ImageView Alphabet;
    ImageView Counting;
    ImageView Colors;
    ImageView Shape;
    ImageView Phonics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursery_class);

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
}


