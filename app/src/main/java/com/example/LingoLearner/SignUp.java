package com.example.LingoLearner;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class SignUp extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    ProgressBar progressbar;
    TextInputEditText editTextUsername, editTextEmail, editTextPassword, editTextConfirmPassword, editTextDateOfBirth;
    Button signupnow;
    TextView regbtn;

    TextView welcometxt;
    TextView signuptxt;  // TextView for animation

    boolean isPasswordVisible = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        progressbar = findViewById(R.id.pgrbar);
        editTextUsername = findViewById(R.id.username);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);
        editTextDateOfBirth = findViewById(R.id.dateOfBirth);
        signupnow = findViewById(R.id.btnsignup);
        regbtn = findViewById(R.id.loginnow);
        mAuth = FirebaseAuth.getInstance();
        welcometxt = findViewById(R.id.HelloUsers);
        signuptxt = findViewById(R.id.SignUpTxt);  // Initialize the TextView for animation

        // Load the animations
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        Animation bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        Animation slideInAnimation = AnimationUtils.loadAnimation(this, R.anim.slidein);
        Animation combinedAnimation = AnimationUtils.loadAnimation(this, R.anim.combinedanim);
        Animation waveAnimation = AnimationUtils.loadAnimation(this, R.anim.waveanim);

        // Apply an animation to the TextView
        signuptxt.startAnimation(slideInAnimation);

        // Start the text animation
        startTextAnimation("Hello! let's join with us");

        signupnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });

        regbtn.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            intent.putExtra("Users", username);
            startActivity(intent);
        });

        editTextPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_END = 2; // Index of drawableEnd

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextPassword.getRight() - editTextPassword.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                        // Tapped on the eye icon
                        if (isPasswordVisible) {
                            // Hide Password
                            editTextPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, R.drawable.showpassword, 0);
                            isPasswordVisible = false;
                        } else {
                            // Show Password
                            editTextPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            editTextPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, R.drawable.showpassword, 0);
                            isPasswordVisible = true;
                        }
                        // Move the cursor to the end of the text
                        editTextPassword.setSelection(editTextPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });

        editTextConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_END = 2; // Index of drawableEnd

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editTextConfirmPassword.getRight() - editTextConfirmPassword.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                        // Tapped on the eye icon
                        if (isPasswordVisible) {
                            // Hide Password
                            editTextConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            editTextConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, R.drawable.showpassword, 0);
                            isPasswordVisible = false;
                        } else {
                            // Show Password
                            editTextConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            editTextConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password, 0, R.drawable.showpassword, 0);
                            isPasswordVisible = true;
                        }
                        // Move the cursor to the end of the text
                        editTextConfirmPassword.setSelection(editTextConfirmPassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });

        editTextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                SignUp.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        editTextDateOfBirth.setText(selectedDate);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void signUpUser() {
        String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        String dateOfBirth = editTextDateOfBirth.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword) || TextUtils.isEmpty(dateOfBirth)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords does not match", Toast.LENGTH_SHORT).show();
            return;
        }

        progressbar.setVisibility(View.VISIBLE);

        Users users = new Users(username, email, password, confirmPassword, dateOfBirth);
        database = FirebaseDatabase.getInstance();
        FirebaseUser firebaseUser= mAuth.getCurrentUser();
        reference = database.getReference("Users");
        reference.child(username).setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressbar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    editTextUsername.setText("");
                    editTextEmail.setText("");
                    editTextPassword.setText("");
                    editTextConfirmPassword.setText("");
                    editTextDateOfBirth.setText("");
                    Toast.makeText(SignUp.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                    // Start the login activity
                    Intent intent = new Intent(SignUp.this, Login.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                    //Update display name of user
                    UserProfileChangeRequest profileChangeRequest= new UserProfileChangeRequest.Builder().setDisplayName(username).build();
                    firebaseUser.updateProfile(profileChangeRequest);
                } else {
                    Toast.makeText(SignUp.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });



        checkUsernameAvailability(username, email, password, dateOfBirth);
    }

    private void checkUsernameAvailability(final String username, final String email, final String password, final String dateOfBirth) {
        DatabaseReference usernameRef = database.getReference("username").child(username);

        usernameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Username is already taken
                    progressbar.setVisibility(View.GONE);
                    Toast.makeText(SignUp.this, "Username is already taken", Toast.LENGTH_SHORT).show();
                } else {
                    // Username is available, proceed with sign up
                    createUserWithEmailAndPassword(email, password, username, dateOfBirth);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(SignUp.this, "Database Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createUserWithEmailAndPassword(String email, String password, String username, String dateOfBirth) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressbar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // Set the display username
                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(username)
                                                .build();

                                        user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    saveUserDataToDatabase(user.getUid(), email, username, dateOfBirth);
                                                } else {
                                                    Toast.makeText(SignUp.this, "Failed to update user profile", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(SignUp.this, "User authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        showEmailInUseDialog(email);
                                    } else {
                                        Toast.makeText(SignUp.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                    }
                });
    }

    private void showEmailInUseDialog(String email) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isFinishing() && !isDestroyed()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this);
                    builder.setTitle("Email Already in Use")
                            .setMessage("The email address " + email + " is already in use. Would you like to sign in instead?")
                            .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(SignUp.this, Login.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("Recover Account", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    sendPasswordResetEmail(email);
                                }
                            })
                            .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    builder.create().show();
                }
            }
        });
    }

    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Password reset email sent.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(SignUp.this, "Failed to send password reset email.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }

    private void saveUserDataToDatabase(String userId, String email, String username, String dateOfBirth) {
        DatabaseReference usersRef = database.getReference("Users");
        Users user = new Users(username, email,dateOfBirth);

        usersRef.child(userId).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Failed to save user data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void startTextAnimation(String text) {
        welcometxt.setText("");  // Clear the TextView before starting the animation

        Handler handler = new Handler();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Append the next character
                    welcometxt.append(String.valueOf(text.charAt(index)));
                }
            }, i * 250);  // 250ms delay for each character
        }
    }
}
