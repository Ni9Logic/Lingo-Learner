package com.example.LingoLearner;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class TwoListening extends AppCompatActivity {

    private VideoView videoView;
    private int currentVideoIndex = 0;
    private final int[] videoResources = {R.raw.listeningv1, R.raw.listeningv2, R.raw.listeningv3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_listening);
        TrackActivities.trackActivity("Listening Activity");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videoView = findViewById(R.id.listeningvideoview);
        MediaController mediaController = new MediaController(TwoListening.this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);

        videoView.setOnCompletionListener(mediaPlayer -> playNextVideo());

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
