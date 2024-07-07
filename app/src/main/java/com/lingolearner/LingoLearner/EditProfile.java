package com.lingolearner.LingoLearner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText userInfoEmail;
    private EditText userInfoDOB;
    private EditText userInfoPassword;
    private EditText userInfoUsername;
    private CircleImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutate_user_own_info);
        TrackActivities.trackActivity("Edit Profile Activity");

        userInfoEmail = findViewById(R.id.userInfoEmail);
        userInfoDOB = findViewById(R.id.userInfoDOB);
        userInfoUsername = findViewById(R.id.userInfoUsername);
        userInfoPassword = findViewById(R.id.userInfoPassword);
        profileImageView = findViewById(R.id.img);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        Button userInfoUpdateBtn = findViewById(R.id.userInfoUpdateBtn);
        userInfoUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = userInfoEmail.getText().toString();
                String newDOB = userInfoDOB.getText().toString();
                String newUsername = userInfoUsername.getText().toString();
                String newPassword = userInfoPassword.getText().toString();

                // Validate user input
                if (newEmail.isEmpty() || newDOB.isEmpty() || newUsername.isEmpty() || newPassword.isEmpty()) {
                    Toast.makeText(EditProfile.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

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

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImageView.setImageBitmap(bitmap);
                uploadImageToDatabase(imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToDatabase(Uri imageUri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("profileImages/" + userEmail);
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    saveImageUrlToDatabase(imageUrl);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfile.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveImageUrlToDatabase(String imageUrl) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
            mDatabase.child(userEmail.replace(".", ",")).child("profileImageUrl").setValue(imageUrl);
            Toast.makeText(EditProfile.this, "Profile image updated!", Toast.LENGTH_SHORT).show();
        }
    }

    private void retrieveUserEmailFromDatabase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
            mDatabase.child(userEmail.replace(".", ",")).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String userUsername = dataSnapshot.child("username").getValue(String.class);
                    String userDOB = dataSnapshot.child("dateOfBirth").getValue(String.class);
                    String userPassword = dataSnapshot.child("password").getValue(String.class);
                    String profileImageUrl = dataSnapshot.child("profileImageUrl").getValue(String.class);

                    // Display the user's email in the EditText
                    userInfoEmail.setText(userEmail);
                    userInfoDOB.setText(userDOB);
                    userInfoPassword.setText(userPassword);
                    userInfoUsername.setText(userUsername);

                    if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                        Glide.with(EditProfile.this).load(profileImageUrl).into(profileImageView);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
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
                    Log.e("Firebase", "Error updating data", databaseError.toException());
                }
            });
        }
    }
}
