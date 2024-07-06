package com.example.LingoLearner;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ThreeGame extends AppCompatActivity {

    private final String[] animalNames = {"Bird", "Cat", "Cow", "Cock", "Dog", "Elephant", "Eagle", "Goat", "Hen", "Horse", "Leopard", "Lion", "Monkey", "Owl", "Pig", "Snake", "Tiger", "Wolf"};
    private final int[] animalImages = {R.drawable.bird, R.drawable.cat3, R.drawable.cow, R.drawable.cock, R.drawable.dog2, R.drawable.elephant1, R.drawable.eagle, R.drawable.goat, R.drawable.hen, R.drawable.horse, R.drawable.leapord, R.drawable.lion, R.drawable.monkey, R.drawable.owl, R.drawable.pig, R.drawable.snake, R.drawable.tiger, R.drawable.wolf};
    private final int[] animalSounds = {R.raw.birds, R.raw.cat, R.raw.cow, R.raw.cock, R.raw.dog, R.raw.elephant1, R.raw.eagle, R.raw.goat, R.raw.hens, R.raw.horse, R.raw.leopard, R.raw.lion1, R.raw.monkey1, R.raw.owl, R.raw.pig, R.raw.snake1, R.raw.tiger1, R.raw.wolf};
    private String correctWord;
    private ImageView imageViewAnimal;
    private EditText editTextGuess;
    private TextView textViewScore;
    private TextView textViewTimer;
    private int score = 0;
    private Button previous, play, next;
    private MediaPlayer mediaPlayer;
    private CountDownTimer countDownTimer;
    private final long TIMER_DURATION = 30000; // 30 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_game);
        TrackActivities.trackActivity("Fun Activity");

        imageViewAnimal = findViewById(R.id.imageViewCat);
        editTextGuess = findViewById(R.id.editTextGuess);
        textViewScore = findViewById(R.id.textViewScore);
        textViewTimer = findViewById(R.id.textViewTimer);

        previous = findViewById(R.id.previous_animals);
        play = findViewById(R.id.play_animals);
        next = findViewById(R.id.next_animals);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle previous button click
                // Decrement the index to go to the previous animal
                int currentIndex = findIndex(correctWord);
                int previousIndex = (currentIndex - 1 + animalNames.length) % animalNames.length;
                correctWord = animalNames[previousIndex];
                int imageResourceId = animalImages[previousIndex];
                int soundResourceId = animalSounds[previousIndex];

                // Update the ImageView to display the previous animal image
                imageViewAnimal.setImageResource(imageResourceId);

                // Play the sound of the previous animal
                playAnimalSound(soundResourceId);
            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Play the sound of the correct animal
                int currentIndex = findIndex(correctWord);
                int soundResourceId = animalSounds[currentIndex];
                playAnimalSound(soundResourceId);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle next button click
                // Call a method to display the next animal
                selectNewAnimal();
            }
        });
        // Start the game
        startGame();
    }

    private void startGame() {
        // Select a random animal name and corresponding image
        selectNewAnimal();

        // Start the timer
        startTimer();
    }

    private void selectNewAnimal() {
        // Select a new random animal name
        Random random = new Random();
        int index = random.nextInt(animalImages.length);
        correctWord = animalNames[index];
        int imageResourceId = animalImages[index];
        int soundResourceId = animalSounds[index]; // Get the corresponding sound resource ID

        // Update the ImageView to display the selected animal image
        imageViewAnimal.setImageResource(imageResourceId);

        // Play the animal sound
        playAnimalSound(soundResourceId);

        // Update the score text
        textViewScore.setText("Score: " + score);
    }

    private void playAnimalSound(int soundResourceId) {
        // Release any previous MediaPlayer to avoid overlapping sounds
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Create a new MediaPlayer to play the animal sound
        mediaPlayer = MediaPlayer.create(this, soundResourceId);
        mediaPlayer.start(); // Start playing the sound
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(TIMER_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer TextView with the remaining time
                textViewTimer.setText("Time left: " + millisUntilFinished / 1000 + " seconds");
            }

            @Override
            public void onFinish() {
                // Game over when the timer finishes
                endGame();
            }
        }.start();
    }

    public void checkGuess(View view) {
        String userGuess = editTextGuess.getText().toString().trim(); // Trim leading and trailing whitespace

        if (userGuess.equalsIgnoreCase(correctWord)) {
            // If the guess is correct, increment the score
            score++;

            // Display the updated score
            textViewScore.setText("Score: " + score);

            // Get the index of the current correct animal
            int index = findIndex(correctWord);

            // Play the sound of the correct animal
            playAnimalSound(animalSounds[index]);

            // Select a new random animal name and image
            selectNewAnimal();

            // Clear the EditText for the next guess
            editTextGuess.setText("");

        } else {
            // If the guess is incorrect, display a toast message
            Toast.makeText(this, "Incorrect guess. Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void endGame() {
        // Stop the timer
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        // Display final score
        TrackActivities.trackGamesRecord("Class Three", "Guess Word", score, 0.0);
        Toast.makeText(this, "Game over! Final score: " + score, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release MediaPlayer resources
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        // Cancel the timer to avoid memory leaks
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    // Method to find the index of the correct animal in the animalNames array
    private int findIndex(String animalName) {
        for (int i = 0; i < animalNames.length; i++) {
            if (animalNames[i].equalsIgnoreCase(animalName)) {
                return i;
            }
        }
        return -1; // Return -1 if not found
    }
}
