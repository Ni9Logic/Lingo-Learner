package com.example.LingoLearner;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class EditProfile extends AppCompatActivity {
    private EditText userInfoEmail;
    private EditText userInfoDOB;

    private EditText userInfoPassword;
    private EditText userInfoUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutate_user_own_info);
        TrackActivities.trackActivity("Edit Profile Activity");

        Intent intent = getIntent();
        userInfoEmail = findViewById(R.id.userInfoEmail);
        userInfoDOB = findViewById(R.id.userInfoDOB);
        userInfoUsername = findViewById(R.id.userInfoUsername);
        userInfoPassword = findViewById(R.id.userInfoPassword);
        Button userInfoUpdateBtn = findViewById(R.id.userInfoUpdateBtn);
        userInfoUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = userInfoEmail.getText().toString();
                String newDOB = userInfoDOB.getText().toString();
                String newUsername = userInfoUsername.getText().toString();
                String newPassword = userInfoPassword.getText().toString();

                // Update the user's information in the database
                updateUserInfoInDatabase(newEmail, newDOB, newUsername, newPassword);
            }
        });




        // Retrieve the user's email from the database
        retrieveUserEmailFromDatabase();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


    }



    private void retrieveUserEmailFromDatabase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userDBEmail = userSnapshot.child("email").getValue(String.class);
                        if (userDBEmail != null && userDBEmail.equals(userEmail)) {
                            String userUsername = userSnapshot.child("username").getValue(String.class);
                            String userDOB = userSnapshot.child("dateOfBirth").getValue(String.class);
                            String userPassword = userSnapshot.child("password").getValue(String.class);
                            // Display the user's email in the EditText
                            userInfoEmail.setText(userEmail);
                            userInfoDOB.setText(userDOB);
                            userInfoPassword.setText(userPassword);
                            userInfoUsername.setText(userUsername);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error
                    Log.e("Firebase", "Error reading data", databaseError.toException());
                }
            });
        }
    }

    private void updateUserInfoInDatabase(String newEmail, String newDOB, String newUsername, String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userDBEmail = userSnapshot.child("email").getValue(String.class);
                        if (userDBEmail != null && userDBEmail.equals(userEmail)) {
                            userSnapshot.getRef().child("email").setValue(newEmail);
                            userSnapshot.getRef().child("dateOfBirth").setValue(newDOB);
                            userSnapshot.getRef().child("password").setValue(newPassword);
                            userSnapshot.getRef().child("username").setValue(newUsername);
                            Toast.makeText(EditProfile.this, "Profile Updated!", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error
                    Log.e("Firebase", "Error updating data", databaseError.toException());
                }
            });
        }
    }


}