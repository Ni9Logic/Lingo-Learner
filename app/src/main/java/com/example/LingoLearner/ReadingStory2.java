package com.example.LingoLearner;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LingoLearner.R;

public class ReadingStory2 extends AppCompatActivity {

    private Button btnPrevious, btnPlay, btnNext;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_story2);

        // Initialize buttons
        btnPrevious = findViewById(R.id.btnPrevious);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);

        // Initialize MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.thirstycrow); // Replace poem1_audio with the name of your audio file

        // Set click listeners
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlayPauseClick();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPreviousClick();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNextClick();
            }
        });
    }

    // Click listener for the play/pause button
    public void onPlayPauseClick() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnPlay.setText("Play");
        }
    }

    // Click listener for the previous button
    public void onPreviousClick() {
        // Implement functionality to go to the previous poem
    }

    // Click listener for the next button
    public void onNextClick() {
        // Implement functionality to go to the next poem
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources when activity is destroyed
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
