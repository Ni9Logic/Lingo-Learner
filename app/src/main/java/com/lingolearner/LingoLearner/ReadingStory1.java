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

public class ReadingStory1 extends AppCompatActivity {

    private ImageButton btnPrevious, btnPlay, btnNext;
    private MediaPlayer mediaPlayer;
    private ImageView storyImage;
    private TextView tvStoryTitle, tvStory, tvMoral;
    private int[] sounds = {R.raw.rabbittortise}; // Adjust with actual audio files
    private int counter = 0;
    private Handler handler;
    private Runnable updateTextRunnable;

    private String storyText = "Once upon a time there was a Rabbit and a tortoise. They were good friends. They used to meet and play every day. The rabbit always boasted that he could run faster than the tortoise. So they decided to have a race. They chose a starting and finishing point. The rabbit ran really fast and soon left the tortoise far behind. He thought that tortoise is too slow and he can rest for a while. So he stooped under a tree and went to sleep. Meanwhile, tortoise kept walking the whole time and reached the winning point. When the rabbit woke up, he saw that tortoise has already won the race."; // Updated story text

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_story1);
        TrackActivities.trackActivity("Reading Story 1 Activity");

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
        tvStoryTitle.setText(getString(R.string.title4));
        storyImage.setImageResource(R.drawable.image1);
        tvStory.setText(storyText); // Use the updated story text
        tvMoral.setText(getString(R.string.moral1));

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
                    handler.removeCallbacks(updateTextRunnable); // Stop updating text
                } else {
                    playSound();
                    btnPlay.setImageResource(R.drawable.pause);
                    startUpdatingText(); // Start updating text
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
            handler.removeCallbacks(updateTextRunnable); // Stop updating text
        }
        prepareMediaPlayer(counter);
    }

    private void startUpdatingText() {
        // No text updating in this simplified version
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(updateTextRunnable); // Remove callbacks to prevent memory leaks
    }
}
