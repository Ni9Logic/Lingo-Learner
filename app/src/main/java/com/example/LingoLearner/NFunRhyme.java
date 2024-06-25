package com.example.LingoLearner;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class NFunRhyme extends AppCompatActivity {

    ListView list;
    String[] s = {"Hickory Dickory Dock","Humpty Dumpty","Johny Johny",
            "Brother John","Twinkle Twinkle"};
    int count = 0;
    MediaPlayer audio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfun_rhyme);

        //    if (list.indexOfChild(list) % 2 == 0) {
        //          list.setBackgroundColor(getColor());
//        }

        list = findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, s);
        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener((adapterView, view, i, l) -> {
            String select = (String) list.getItemAtPosition(i);
            if(count>0){
                audio.stop();
            }
            else{
                count++;
            }
            if (select.compareTo("Hickory Dickory Dock") == 0) {
                audio = MediaPlayer.create(NFunRhyme.this, R.raw.dickory);
                audio.start();
            }
            else if(select.compareTo("Humpty Dumpty")==0){
                audio = MediaPlayer.create(NFunRhyme.this,R.raw.humpty);
                audio.start();

            }
            else if(select.compareTo("Johny Johny")==0){
                audio = MediaPlayer.create(NFunRhyme.this,R.raw.jony);
                audio.start();

            }
            else if(select.compareTo("Brother John")==0){
                audio = MediaPlayer.create(NFunRhyme.this,R.raw.jhon);
                audio.start();

            }
            else if(select.compareTo("Twinkle Twinkle")==0){
                audio = MediaPlayer.create(NFunRhyme.this,R.raw.twinkle);
                audio.start();
            }

        });

    }
    public void onBackPressed() {
        super.onBackPressed();
        if (count > 0) {
            audio.stop();
        }
        Intent intent = new Intent(NFunRhyme.this, NFunActivity.class);
        startActivity(intent);
    }
    public void onPause() {
        if (count > 0) {
            audio.stop();
        }
        super.onPause();
    }
}