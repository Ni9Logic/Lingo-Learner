package com.lingolearner.LingoLearner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Admin_User_Report extends AppCompatActivity {

    private LinearLayout usersLayout;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_report);

        usersLayout = findViewById(R.id.activityUserReport);
        // Get the email from the Intent
        userEmail = getIntent().getStringExtra("email");
        // Retrieve the list of users from the database
        fetchUserActivities();
    }

    private void fetchUserActivities() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String email = userSnapshot.child("email").getValue(String.class);
                        assert email != null;
                        if (email.equals(userEmail)) {
                            String username = userSnapshot.child("username").getValue(String.class);
                            DataSnapshot activitiesSnapshot = userSnapshot.child("activities");
                            if (activitiesSnapshot.exists()) {
                                displayUser(username, email, activitiesSnapshot);
                            } else {
                                displayUser(username, email, null);
                            }
                            break;
                        }
                    }
                } else {
                    // Handle the case where there are no users
                    Toast.makeText(Admin_User_Report.this, "No users found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Log.e("Firebase", "Error fetching users", databaseError.toException());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void displayUser(String username, String email, DataSnapshot activitiesSnapshot) {
        CardView cardView = new CardView(Admin_User_Report.this);
        cardView.setRadius(16);
        cardView.setElevation(5);
        cardView.setMaxCardElevation(5);
        cardView.setContentPadding(18, 18, 18, 18);
        cardView.setCardBackgroundColor(getResources().getColor(R.color.white));

        LinearLayout linearLayout = new LinearLayout(Admin_User_Report.this);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(18, 18, 18, 18);

        // Create a list to store the activities
        List<Map<String, Object>> activitiesList = new ArrayList<>();

        if (activitiesSnapshot != null) {
            for (DataSnapshot activitySnapshot : activitiesSnapshot.getChildren()) {
                String activityName = activitySnapshot.getKey();
                int activityCount = activitySnapshot.getValue(Integer.class);
                Map<String, Object> activityMap = new HashMap<>();
                activityMap.put("name", activityName);
                activityMap.put("count", activityCount);
                activitiesList.add(activityMap);
            }

            // Sort the activities in descending order based on the activityCount
            Collections.sort(activitiesList, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    return ((Integer) Objects.requireNonNull(o2.get("count"))).compareTo((Integer) Objects.requireNonNull(o1.get("count")));
                }
            });

            // Add the sorted activities to the LinearLayout
            for (Map<String, Object> activityMap : activitiesList) {
                String activityName = (String) activityMap.get("name");
                int activityCount = (int) activityMap.get("count");
                TextView activityTextView = new TextView(Admin_User_Report.this);
                activityTextView.setText(activityName + ": " + activityCount);
                activityTextView.setTextSize(18);
                activityTextView.setTextColor(getResources().getColor(R.color.colorAccent));
                activityTextView.setGravity(Gravity.CENTER);
                linearLayout.addView(activityTextView);
            }
        }

        cardView.addView(linearLayout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.topMargin = 10;
        params.bottomMargin = 10;
        cardView.setLayoutParams(params);
        usersLayout.addView(cardView);
    }
}