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

public class ReadingStory3 extends AppCompatActivity {

    private ImageButton btnPrevious, btnPlay, btnNext;
    private MediaPlayer mediaPlayer;
    private ImageView storyImage;
    private TextView tvStoryTitle, tvStory, tvMoral;
    private int[] sounds = {R.raw.uglyducks};
    private int counter = 0;
    private Handler handler;

    private String storyText = "It is a beautiful summer day. The sun shines warmly on an old house near a river. Behind the house a mother duck is sitting on ten eggs. \"Tchick.\" One by one all the eggs break open.\n" +
            "All except one. This one is the biggest egg of all.\n" +
            "Mother duck sits and sits on the big egg. At last it breaks open, \"Tchick, tchick!\"\n" +
            "Out jumps the last baby duck. It looks big and strong. It is grey and ugly.\n" +
            "The next day mother duck takes all her little ducks to the river. She jumps into it. All her baby ducks jump in. The big ugly duckling jumps in too.\n" +
            "They all swim and play together. The ugly duckling swims better than all the other ducklings.\n" +
            "- Quack, quack! Come with me to the farm yard! - says mother duck to her baby ducks and they all follow her there.\n" +
            "The farm yard is very noisy. The poor duckling is so unhappy there. The hens peck him, the rooster flies at him, the ducks bite him, the farmer kicks him.\n" +
            "At last one day he runs away. He comes to a river. He sees many beautiful big birds swimming there. Their feathers are so white, their necks so long, their wings so pretty. The little duckling looks and looks at them. He wants to be with them. He wants to stay and watch them. He knows they are swans. Oh, how he wants to be beautiful like them.\n" +
            "Now it is winter. Everything is white with snow. The river is covered with ice. The ugly duckling is very cold and unhappy.\n" +
            "Spring comes once again. The sun shines warmly. Everything is fresh and green.\n" +
            "One morning the ugly duckling sees the beautiful swans again. He knows them. He wants so much to swim with them in the river. But he is afraid of them. He wants to die. So he runs into the river. He looks into the water. There in the water he sees a beautiful swan. It is he! He is no more an ugly duckling. He is a beautiful white swan.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_story3);
        TrackActivities.trackActivity("Reading Story 3 Activity");

        // Initialize ImageButtons
        btnPrevious = findViewById(R.id.btnPrevious);
        btnPlay = findViewById(R.id.btnPlay);
        btnNext = findViewById(R.id.btnNext);

        // Initialize ImageView, TextViews, and ScrollView
        storyImage = findViewById(R.id.storyImage);
        tvStoryTitle = findViewById(R.id.tvStoryTitle);
        tvStory = findViewById(R.id.tvStory);
        tvMoral = findViewById(R.id.tvMoral);

        // Initialize MediaPlayer
        mediaPlayer = new MediaPlayer();

        // Set text and image
        tvStoryTitle.setText(getString(R.string.title6));
        storyImage.setImageResource(R.drawable.image8);
        tvStory.setText(storyText); // Use the updated story text
        tvMoral.setText(getString(R.string.moral5));

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
