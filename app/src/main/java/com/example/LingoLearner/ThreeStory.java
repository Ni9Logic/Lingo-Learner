package com.example.LingoLearner;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ThreeStory extends AppCompatActivity {

    private VideoView videoView;
    private int currentVideoIndex = 0;
    private int[] videoResources = {R.raw.grade3v1, R.raw.grade3v2, R.raw.grade3v3, R.raw.grade3v4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_story);

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
        MediaController mediaController = new MediaController(ThreeStory.this);
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

    public void playNextVideo(View view) {
        playNextVideo();
    }
}
