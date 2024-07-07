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

public class ReadingStory4 extends AppCompatActivity {

    private ImageButton btnPrevious, btnPlay, btnNext;
    private MediaPlayer mediaPlayer;
    private ImageView bgImage, storyImage;
    private TextView tvStoryTitle, tvStory, tvMoral;
    private String storyText = "One afternoon a fox was walking through the forest and spotted a bunch of grapes hanging from over a lofty branch.\n" +
            "\"Just the thing to quench my thirst,\" he thought.\n" +
            "Taking a few steps back, the fox jumped and just missed the hanging grapes. Again the fox took a few paces back and tried to reach them but still failed.\n" +
            "Finally, giving up, the fox turned up his nose and said, \"They're probably sour anyway,\" and proceeded to walk away.";
    private int[] sounds = {R.raw.thefoxngrapes};
    private int counter = 0;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_story4);
        TrackActivities.trackActivity("Reading Story 4 Activity");

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
        tvStoryTitle.setText(getString(R.string.title7));
        storyImage.setImageResource(R.drawable.image5);
        tvStory.setText(storyText);
        tvMoral.setText(getString(R.string.moral2));

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
