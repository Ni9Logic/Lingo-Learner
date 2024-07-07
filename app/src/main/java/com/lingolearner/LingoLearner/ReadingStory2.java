package com.lingolearner.LingoLearner;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReadingStory2 extends AppCompatActivity {

    private ImageButton btnPrevious, btnPlay, btnNext;
    private MediaPlayer mediaPlayer;
    private ImageView storyImage;
    private TextView tvStoryTitle, tvStory, tvMoral;
    private int[] sounds = {R.raw.thirstycrow}; // Adjust with actual audio files
    private int counter = 0;

    private String storyText = "Once there was a crow One day he was very thirsty. He flew here and there in search of water. He saw a pitcher under a tree. There was a little water in it. His beak could not reach a water. He got an idea He dropped some stones in the pitcher The water came up and he drank it. After drinking the water, he flew away happily.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_story2);
        TrackActivities.trackActivity("Reading Story 2 Activity");

        // Initialize ImageButtons
        btnPrevious = findViewById(R.id.btnPrevious);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);

        // Initialize ImageView, TextViews
        storyImage = findViewById(R.id.storyImage);
        tvStoryTitle = findViewById(R.id.tvStoryTitle);
        tvStory = findViewById(R.id.tvStory);
        tvMoral = findViewById(R.id.tvMoral);

        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();

        // Set text and image
        tvStoryTitle.setText(getString(R.string.title5));
        storyImage.setImageResource(R.drawable.image3);
        tvStory.setText(storyText); // Use the updated story text
        tvMoral.setText(getString(R.string.moral4));

        // Set click listeners
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter > 0) {
                    counter--;
                    playSound();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (counter < sounds.length - 1) {
                    counter++;
                    playSound();
                }
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                } else {
                    playSound();
                    btnPlay.setImageResource(R.drawable.pause);
                }
            }
        });

        // Prepare media player with first sound
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        prepareMediaPlayer(counter); // Prepare the media player with the first sound
    }

    private void prepareMediaPlayer(int position) {
        try {
            mediaPlayer.reset();
            // Construct a Uri for the sound file using the resource identifier
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + sounds[position]);
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playSound() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
        prepareMediaPlayer(counter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
