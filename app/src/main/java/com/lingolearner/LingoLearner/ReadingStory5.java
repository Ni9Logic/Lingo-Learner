package com.lingolearner.LingoLearner;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ReadingStory5 extends AppCompatActivity {

    private ImageButton btnPrevious, btnPlay, btnNext;
    private MediaPlayer mediaPlayer;
    private ImageView bgImage, storyImage;
    private TextView tvStoryTitle, tvStory, tvMoral;
    private String storyText = "Once upon a time, there lived a proud and naughty elephant in a jungle. It treated all creatures smaller than itself harshly and was very rude to them. One day, the ant family set out on their daily hunt for food. On their way, they met the elephant who had a naughty idea and decided to cause the ant family trouble.\n" +
            "Suddenly, it sprayed the ants with water. The ants became very angry and one of them cried, “This is wrong! You cannot harm others like this.” The rude elephant replied, “You silly ant! I will crush you under my foot if you speak again.” The clever ant then decided to teach the elephant a lesson so that it learns to respect every creature.\n" +
            "The tiny ant quietly crept inside the elephant’s trunk and started biting it. The elephant jumped and blowed its trunk to get rid of the ant but it did not stop. The big elephant kept crying in pain. Finally, it said “I am sorry. I have realised my mistake. I promise I will be good and not trouble anyone.” The ant then stopped biting it and came out. The elephant had learnt a lesson and was good to everyone thereafter. The elephant and the ant became great friends.";
    private int[] sounds = {R.raw.antandelephant};
    private int counter = 0;
    private Handler handler;
    private Runnable updateTextRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_story5);
        TrackActivities.trackActivity("Reading Story 5 Activity");

        // Initialize ImageButtons
        btnPrevious = findViewById(R.id.btnPrevious);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);

        // Initialize ImageView, TextViews, and ScrollView
        bgImage = findViewById(R.id.bgImage);
        storyImage = findViewById(R.id.storyImage);
        tvStoryTitle = findViewById(R.id.tvStoryTitle);
        tvStory = findViewById(R.id.tvStory);
        tvMoral = findViewById(R.id.tvMoral);

        // Set text and image
        tvStoryTitle.setText(getString(R.string.title8));
        storyImage.setImageResource(R.drawable.image10);
        tvStory.setText(storyText);
        tvMoral.setText(getString(R.string.moral3));

        // Set click listeners
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter > 0) {
                    counter--;
                    playSound();
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter < sounds.length - 1) {
                    counter++;
                    playSound();
                }
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                } else {
                    playSound();
                    btnPlay.setImageResource(R.drawable.pause);
                }
            }
        });

        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer.start();
            }
        });
        prepareMediaPlayer(counter); // Prepare media player with initial sound

        handler = new Handler();
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
