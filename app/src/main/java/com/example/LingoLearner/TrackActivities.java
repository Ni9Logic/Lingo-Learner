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

import java.util.ArrayList;
import java.util.List;

public class TrackActivities {
    public static void trackGamesRecord(String userClass, String gameName, double userScore, double userCompletionTime) {
        // Get the current user's email
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String userEmail = user.getEmail();

            // Search for the user in the Users reference
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

            Double finalUserCompletionTime = userCompletionTime;
            mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        if (userSnapshot.child("email").getValue(String.class).equals(userEmail)) {
                            DatabaseReference funActivitiesReference = userSnapshot.child(userClass).getRef();

                            // Check if userClass exists
                            funActivitiesReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.exists()) {
                                        // Create userClass if it doesn't exist
                                        funActivitiesReference.setValue(true);
                                    }

                                    DatabaseReference gameReference = funActivitiesReference.child(gameName).getRef();

                                    // Check if gameName exists
                                    gameReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot gameSnapshot) {
                                            if (!gameSnapshot.exists()) {
                                                // Create gameName if it doesn't exist
                                                gameReference.setValue(true);
                                            }

                                            DatabaseReference scoreReference = gameReference.child("userScore").getRef();
                                            DatabaseReference timeReference = gameReference.child("userCompletionTime").getRef();

                                            // Update or create userScore array
                                            scoreReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot scoreSnapshot) {
                                                    List<Double> scores = new ArrayList<>();
                                                    if (scoreSnapshot.exists()) {
                                                        for (DataSnapshot score : scoreSnapshot.getChildren()) {
                                                            scores.add(score.getValue(Double.class));
                                                        }
                                                    }
                                                    scores.add(userScore);
                                                    scoreReference.setValue(scores);

                                                    // Calculate average userScore
                                                    double avgScore = 0;
                                                    for (double score : scores) {
                                                        avgScore += score;
                                                    }
                                                    avgScore /= scores.size();

                                                    // Set average userScore
                                                    gameReference.child("avgUserScore").setValue(avgScore);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    // Handle the error
                                                    Log.e("Firebase", "Error updating scores", error.toException());
                                                }
                                            });

                                            // Update or create userCompletionTime array
                                            timeReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot timeSnapshot) {
                                                    List<Double> times = new ArrayList<>();
                                                    if (timeSnapshot.exists()) {
                                                        for (DataSnapshot time : timeSnapshot.getChildren()) {
                                                            times.add(time.getValue(Double.class));
                                                        }
                                                    }
                                                    times.add(finalUserCompletionTime);
                                                    timeReference.setValue(times);

                                                    // Calculate average userCompletionTime
                                                    double avgTime = 0;
                                                    for (double time : times) {
                                                        avgTime += time;
                                                    }
                                                    avgTime /= times.size();

                                                    // Set average userCompletionTime
                                                    gameReference.child("avgUserCompletionTime").setValue(avgTime);
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    // Handle the error
                                                    Log.e("Firebase", "Error updating completion times", error.toException());
                                                }
                                            });
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            // Handle the error
                                            Log.e("Firebase", "Error checking game existence", error.toException());
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Handle the error
                                    Log.e("Firebase", "Error checking class existence", error.toException());
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the error
                    Log.e("Firebase", "Error fetching users", error.toException());
                }
            });
        }
    }
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