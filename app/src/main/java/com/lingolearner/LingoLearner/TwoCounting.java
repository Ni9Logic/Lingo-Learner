package com.lingolearner.LingoLearner;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class TwoCounting extends AppCompatActivity {

    private VideoView videoView;
    private int currentVideoIndex = 0;
    private int[] videoResources = {R.raw.countinggrade2v1, R.raw.countinggrade2v2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_two_counting);
        TrackActivities.trackActivity("Counting Activity");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videoView = findViewById(R.id.videoview);
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playNextVideo();
            }
        });

        playVideo();
    }

    private void playVideo() {
        if (currentVideoIndex < videoResources.length) {
            videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + videoResources[currentVideoIndex]));

            videoView.requestFocus();
            videoView.start();
        }
    }

    private void playNextVideo() {
        currentVideoIndex++;
        if (currentVideoIndex >= videoResources.length) {
            currentVideoIndex = 0; // Reset to the first video if all videos played
        }
        playVideo();
    }

    private void playPreviousVideo() {
        currentVideoIndex--;
        if (currentVideoIndex < 0) {
            currentVideoIndex = videoResources.length - 1; // Set to the last video if at the beginning
        }
        playVideo();
    }

    public void playNextVideo(View view) {
        playNextVideo();
    }

    public void playPreviousVideo(View view) {
        playPreviousVideo();
    }

    public void navigateToHome(View view) {
        Intent intent = new Intent(this, ClassTwo.class);
        startActivity(intent);
        finish();
    }
}
