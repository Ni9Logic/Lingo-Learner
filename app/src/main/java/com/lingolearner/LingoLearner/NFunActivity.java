package com.lingolearner.LingoLearner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NFunActivity extends AppCompatActivity {

    Button alphabet, game1, game2, rhyme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfun);
        TrackActivities.trackActivity("Fun Activity");
        alphabet = findViewById(R.id.alphabet);
        game1 = findViewById(R.id.game1);
        game2 = findViewById(R.id.game2);
        rhyme = findViewById(R.id.rhyme);

        alphabet.setOnClickListener(v -> {
            if (alphabet.getId() == R.id.alphabet) {
                Intent intent = new Intent( NFunActivity.this, NAlphFunActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "This content is not available", Toast.LENGTH_SHORT).show();
            }
        });

        game1.setOnClickListener(v -> {
            if (game1.getId() == R.id.game1) {
                Intent intent = new Intent(NFunActivity.this, NursryGame1.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "This content is not available", Toast.LENGTH_SHORT).show();
            }
        });

        game2.setOnClickListener(v -> {
            if (game2.getId() == R.id.game2) {
                Intent intent = new Intent(NFunActivity.this, NursryGame2.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "This content is not available", Toast.LENGTH_SHORT).show();
            }
        });

        rhyme.setOnClickListener(v -> {
            if (rhyme.getId() == R.id.rhyme) {
                Intent intent = new Intent(NFunActivity.this, NFunRhyme.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "This content is not available", Toast.LENGTH_SHORT).show();
            }
        });

    }

}