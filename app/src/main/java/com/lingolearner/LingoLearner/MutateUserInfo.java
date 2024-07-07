package com.lingolearner.LingoLearner;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MutateUserInfo extends AppCompatActivity {
    private EditText userInfoEmail;
    private EditText userInfoDOB;

    private EditText userInfoPassword;
    private EditText userInfoUsername;
    private EditText userInfoRole;

    private String OldUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutate_user_info);

        Intent intent = getIntent();
        String selectedUser = intent.getStringExtra("Username");
        OldUsername = selectedUser;
        userInfoEmail = findViewById(R.id.userInfoEmail);
        userInfoDOB = findViewById(R.id.userInfoDOB);
        userInfoUsername = findViewById(R.id.userInfoUsername);
        userInfoPassword = findViewById(R.id.userInfoPassword);
        userInfoRole = findViewById(R.id.userInfoRole);
        Button userInfoUpdateBtn = findViewById(R.id.userInfoUpdateBtn);
        Button userInfoDeleteBtn = findViewById(R.id.userInfoDeleteBtn);
        userInfoUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = userInfoEmail.getText().toString();
                String newDOB = userInfoDOB.getText().toString();
                String newUsername = userInfoUsername.getText().toString();
                String newPassword = userInfoPassword.getText().toString();
                String newUserRole = userInfoRole.getText().toString();

                // Update the user's information in the database
                updateUserInfoInDatabase(newEmail, newDOB, newUsername, newPassword, newUserRole);
            }
        });

        userInfoDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userInfoUsername.getText().toString();
                deleteUserFromDatabase(username);
            }
        });


        // Retrieve the user's email from the database
        retrieveUserEmailFromDatabase(selectedUser);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


    }

    private void deleteUserFromDatabase(String username) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userUsername = userSnapshot.child("username").getValue(String.class);
                    assert userUsername != null;
                    if (userUsername.equals(username)) {
                        userSnapshot.getRef().removeValue();
                        Toast.makeText(MutateUserInfo.this, "User Deleted!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Log.e("Firebase", "Error deleting data", databaseError.toException());
            }
        });
    }

    private void retrieveUserEmailFromDatabase(String username) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userUsername = userSnapshot.child("username").getValue(String.class);
                    OldUsername = userUsername;
                    assert userUsername != null;
                    if (userUsername.equals(username)) {
                        String userEmail = userSnapshot.child("email").getValue(String.class);
                        String userDOB = userSnapshot.child("dateOfBirth").getValue(String.class);
                        String userPassword = userSnapshot.child("password").getValue(String.class);
                        String userRole = userSnapshot.child("Role").getValue(String.class);
                        // Display the user's email in the EditText
                        userInfoEmail.setText(userEmail);
                        userInfoDOB.setText(userDOB);
                        userInfoPassword.setText(userPassword);
                        userInfoUsername.setText(username);
                        assert userRole != null;
                        if (userRole.length() > 0)
                            userInfoRole.setText(userRole);
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

    private void updateUserInfoInDatabase(String newEmail, String newDOB, String newUsername, String newPassword, String newUserRole) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userUsername = userSnapshot.child("username").getValue(String.class);
                    assert userUsername != null;
                    if (userUsername.equals(OldUsername)) {
                        userSnapshot.getRef().child("email").setValue(newEmail);
                        userSnapshot.getRef().child("dateOfBirth").setValue(newDOB);
                        userSnapshot.getRef().child("password").setValue(newPassword);
                        userSnapshot.getRef().child("username").setValue(newUsername);
                        userSnapshot.getRef().child("Role").setValue(newUserRole);
                        Toast.makeText(MutateUserInfo.this, "User Information Updated!", Toast.LENGTH_SHORT).show();
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