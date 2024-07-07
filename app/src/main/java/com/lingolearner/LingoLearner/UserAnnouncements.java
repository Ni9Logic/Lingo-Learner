package com.lingolearner.LingoLearner;

import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserAnnouncements extends AppCompatActivity {

    private LinearLayout announcementsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        announcementsLayout = findViewById(R.id.activity_announcements_layout);

        // Retrieve the user's email from the database
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // Fetch announcements from the database
        retrieveCurrentUsername();
    }

    private void fetchAnnouncements(String username) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(username).child("announcements").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {};
                    ArrayList<String> announcements = dataSnapshot.getValue(t);
                    displayAnnouncements(announcements);
                } else {
                    // Handle the case where the user has no announcements
                    Toast.makeText(UserAnnouncements.this, "No announcements found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Log.e("Firebase", "Error fetching announcements", databaseError.toException());
            }
        });
    }

    private void retrieveCurrentUsername() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!= null) {
            String userEmail = user.getEmail();
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userDBEmail = userSnapshot.child("email").getValue(String.class);
                        if (userDBEmail!= null && userDBEmail.equals(userEmail)) {
                            String username = userSnapshot.child("username").getValue(String.class);
                            fetchAnnouncements(username);
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

    private void displayAnnouncements(ArrayList<String> announcements) {
        for (String announcement : announcements) {
            CardView cardView = new CardView(UserAnnouncements.this);
            cardView.setRadius(16);
            cardView.setElevation(5);
            cardView.setMaxCardElevation(5);
            cardView.setContentPadding(18, 18, 18, 18);
            cardView.setCardBackgroundColor(getResources().getColor(R.color.white));

            LinearLayout linearLayout = new LinearLayout(UserAnnouncements.this);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(18, 18, 18, 18);

            TextView textView = new TextView(UserAnnouncements.this);
            textView.setText(announcement);
            textView.setTextSize(18);
            textView.setTextColor(getResources().getColor(R.color.colorAccent));
            textView.setGravity(Gravity.CENTER);
            linearLayout.addView(textView);

            cardView.addView(linearLayout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.topMargin = 10;
            params.bottomMargin = 10;
            cardView.setLayoutParams(params);
            announcementsLayout.addView(cardView);
        }

        Button markAsReadButton = new Button(UserAnnouncements.this);
        markAsReadButton.setText("Mark as Read");
        markAsReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user!= null) {
                    String userEmail = user.getEmail();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                    mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                String userDBEmail = userSnapshot.child("email").getValue(String.class);
                                if (userDBEmail!= null && userDBEmail.equals(userEmail)) {
                                    String username = userSnapshot.child("username").getValue(String.class);
                                    mDatabase.child("Users").child(username).child("announcements").removeValue();
                                    Toast.makeText(UserAnnouncements.this, "Announcements marked as read", Toast.LENGTH_SHORT).show();
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
        });
        announcementsLayout.addView(markAsReadButton);
    }
}