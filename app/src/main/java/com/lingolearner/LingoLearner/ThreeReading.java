package com.lingolearner.LingoLearner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ThreeReading extends AppCompatActivity {
    ImageView ReadingPoem1;
    ImageView ReadingPoem2;
    ImageView ReadingPoem3;
    ImageView ReadingStory1;
    ImageView ReadingStory2;
    ImageView ReadingStory3;
    ImageView ReadingStory4;
    ImageView ReadingStory5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_reading);
        TrackActivities.trackActivity("Reading Activity");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        ReadingPoem1= findViewById(R.id.image1);
        ReadingPoem2 = findViewById(R.id.image2);
        ReadingPoem3=findViewById(R.id.image3);
        ReadingStory1=findViewById(R.id.image4);
        ReadingStory2=findViewById(R.id.img5);
        ReadingStory3=findViewById(R.id.img6);
        ReadingStory4=findViewById(R.id.img7);
        ReadingStory5=findViewById(R.id.img8);



        ReadingPoem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreeReading.this, ReadingPoem1.class);
                startActivity(intent);
            }
        });


        ReadingPoem2.setOnClickListener(v -> {
            Intent intent = new Intent(ThreeReading.this, ReadingPoem2.class);
            startActivity(intent);
        });

        ReadingPoem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreeReading.this, ReadingPoem3.class);
                startActivity(intent);
            }
        });

        ReadingStory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreeReading.this, ReadingStory1.class);
                startActivity(intent);
            }
        });


        ReadingStory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreeReading.this, ReadingStory2.class);
                startActivity(intent);
            }
        });

        ReadingStory3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreeReading.this, ReadingStory3.class);
                startActivity(intent);
            }
        });


        ReadingStory4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreeReading.this, ReadingStory4.class);
                startActivity(intent);
            }
        });

        ReadingStory5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThreeReading.this, ReadingStory5.class);
                startActivity(intent);
            }
        });

    }
}
