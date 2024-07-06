package com.example.LingoLearner;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";

    TextInputEditText editTextUsernameEmail, editTextPassword;
    Button loginNow;
    ProgressBar progressBar;
    TextView signupNow, forgetPassword;
    FirebaseAuth mAuth;
    DatabaseReference usersRef;
    TextView welcome;
    TextView heytxt;
    boolean isPasswordVisible = false;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("Users");

        editTextUsernameEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        progressBar = findViewById(R.id.pgrbar);
        signupNow = findViewById(R.id.signupnow);
        loginNow = findViewById(R.id.btn_loginnow);
        forgetPassword = findViewById(R.id.forgotpassword);
        welcome = findViewById(R.id.WelcomebackTxt);
        heytxt = findViewById(R.id.Heytxt);

        // Load the animations
        Animation fliprotateAnimation = AnimationUtils.loadAnimation(this, R.anim.fliprotateanim);

        // Apply an animation to the TextView
        welcome.startAnimation(fliprotateAnimation);


        // Start the text animation
        startTextAnimation("Hey! Good to see you again");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        signupNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        loginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ResetPassword.class);
                startActivity(intent);
            }
        });

        editTextPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_END = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                        if (isPasswordVisible) {
                            editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, R.drawable.showpassword, 0);
                            isPasswordVisible = false;
                        } else {
                            editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, R.drawable.showpassword, 0);
                            isPasswordVisible = true;
                        }
                        editTextPassword.setSelection(editTextPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void loginUser() {
//        String email = editTextUsernameEmail.getText().toString().trim();
//        String password = editTextPassword.getText().toString().trim();

//        For debugging
        String email = "rehmanhassan117@gmail.com";
        String password = "HASsan@4r";
        if (TextUtils.isEmpty(email)) {

            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "signInWithEmail:success");
                            Intent intent = new Intent(Login.this, Home.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(Login.this, "Invalid credentials. Please check your email and password.", Toast.LENGTH_SHORT).show();
                            } else if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(Login.this, "No account found with this email. Please sign up.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login.this, "Authentication Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startTextAnimation(String text) {
        heytxt.setText("");  // Clear the TextView before starting the animation

        Handler handler = new Handler();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Append the next character
                    heytxt.append(String.valueOf(text.charAt(index)));
                }
            }, i * 250);  // 250ms delay for each character
        }
    }
}
