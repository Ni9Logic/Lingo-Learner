package com.lingolearner.LingoLearner;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import java.util.ArrayList;
import java.util.List;

public class PrepAnimalsName extends AppCompatActivity {

    private RecyclerView animalRecycler;
    private List<ImageItem> imageItemList;
    private ImageAdapter adapter;

    private CenterZoomLayoutManager centerZoomLayoutManager;


    private Button previous, play, next;
    private int counter = 0;

    private MediaPlayer mediaPlayer;
    private int[] sounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Setting up the activity for full screen mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prep_animals_name);
        TrackActivities.trackActivity("Animals Name Activity");


        sounds = new int[]{R.raw.alligator, R.raw.bear, R.raw.elephant, R.raw.lion, R.raw.monkey, R.raw.panda, R.raw.rabbit,
                R.raw.snake,  R.raw.squirrel, R.raw.tiger, R.raw.zebra};

        imageItemList = new ArrayList<>();
        initList();
        adapter = new ImageAdapter(this, imageItemList);


        animalRecycler = (RecyclerView) findViewById(R.id.recycler_animals);
        centerZoomLayoutManager = new CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        animalRecycler.setLayoutManager(centerZoomLayoutManager);
        animalRecycler.setItemAnimator(new DefaultItemAnimator());
        animalRecycler.setAdapter(adapter);

        previous = (Button) findViewById(R.id.previous_animals);
        play = (Button) findViewById(R.id.play_animals);
        next = (Button) findViewById(R.id.next_animals);

        counter = Integer.MAX_VALUE / 2;

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = centerZoomLayoutManager.findLastCompletelyVisibleItemPosition();
                counter--;
                animalRecycler.smoothScrollToPosition(counter);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = centerZoomLayoutManager.findLastCompletelyVisibleItemPosition();
                counter++;
                animalRecycler.smoothScrollToPosition(counter);
            }
        });

        animalRecycler.scrollToPosition(counter);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(animalRecycler);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = centerZoomLayoutManager.findLastCompletelyVisibleItemPosition();
                int pos = counter % imageItemList.size();
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }
                mediaPlayer = MediaPlayer.create(getApplicationContext(), sounds[pos]);
                mediaPlayer.start();
            }
        });
    }

    private void initList() {
        imageItemList.add(new ImageItem("Alligator", R.drawable.alligator));
        imageItemList.add(new ImageItem("Bear", R.drawable.bear1));
        imageItemList.add(new ImageItem("Elephant", R.drawable.elephant1));
        imageItemList.add(new ImageItem("Lion", R.drawable.lion));
        imageItemList.add(new ImageItem("Monkey", R.drawable.monkey));
        imageItemList.add(new ImageItem("Panda", R.drawable.panda));
        imageItemList.add(new ImageItem("Rabbit", R.drawable.rabbit1));
        imageItemList.add(new ImageItem("Snake", R.drawable.snake));
        imageItemList.add(new ImageItem("Squirrel", R.drawable.squirrel));
        imageItemList.add(new ImageItem("Tiger", R.drawable.tiger));
        imageItemList.add(new ImageItem("Zebra", R.drawable.zebra));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}