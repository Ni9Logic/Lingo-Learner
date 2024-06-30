package com.example.LingoLearner;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrackActivities {

    public static void trackActivity(String activityName) {
        // Get the current user's email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null) {
            String userEmail = user.getEmail();

            // Search for the user in the Users reference
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        if (userSnapshot.child("email").getValue(String.class).equals(userEmail)) {
                            String username = userSnapshot.child("username").getValue(String.class);

                            // Get the activity count
                            DatabaseReference activityReference = userSnapshot.child("activities").child(activityName).getRef();
                            activityReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        int count = dataSnapshot.getValue(Integer.class);
                                        activityReference.setValue(count + 1);
                                    } else {
                                        activityReference.setValue(1);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle the error
                                    Log.e("Firebase", "Error tracking activity", databaseError.toException());
                                }
                            });

                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error
                    Log.e("Firebase", "Error fetching users", databaseError.toException());
                }
            });
        }
    }
}