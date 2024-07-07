package com.lingolearner.LingoLearner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button login;
    Button signup;
    TextView getstarted;
    TextView startsl;
    TextView lingotxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getstarted = findViewById(R.id.GetStarted);
        startsl = findViewById(R.id.startSL);
        lingotxt=findViewById(R.id.lingolearner);

        Animation waveAnimation = AnimationUtils.loadAnimation(this, R.anim.waveanim);
        Animation lingoanim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        getstarted.startAnimation(waveAnimation);
        lingotxt.startAnimation(lingoanim);
        // Transparent ActionBar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        signup = findViewById(R.id.btnsignup);
        signup.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "SignUp has been clicked", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        });

        login = findViewById(R.id.btnlogin);
        login.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Login has been clicked", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        });

        // Start the text animation when the activity is created
        startTextAnimation("Start with SignUp or Login");
    }

    private void startTextAnimation(String text) {
        startsl.setText("");  // Clear the TextView before starting the animation

        Handler handler = new Handler();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Append the next character
                    startsl.append(String.valueOf(text.charAt(index)));

                }
            }, i * 250);  // 200ms delay for each character
        }
    }
}

