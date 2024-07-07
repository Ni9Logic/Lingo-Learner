package com.lingolearner.LingoLearner;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AdminSendAnnouncements extends AppCompatActivity {
    private Button sendAnnouncementBtn;
    private TextInputEditText sendAnnouncementText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_send_announcement);
        TrackActivities.trackActivity("Create Announcements");


        sendAnnouncementBtn = findViewById(R.id.SendAnnouncementBtn);
        sendAnnouncementText = findViewById(R.id.sendAnnouncementText);

        sendAnnouncementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String announcement = sendAnnouncementText.getText().toString();
                if (!announcement.isEmpty()) {
                    sendAnnouncementToAllUsers(announcement);
                } else {
                    Toast.makeText(AdminSendAnnouncements.this, "Please enter an announcement", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ...
    }

    private void sendAnnouncementToAllUsers(String announcement) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userEmail = userSnapshot.child("email").getValue(String.class);
                    if (userEmail != null) {
                        sendAnnouncementToUser(userEmail, announcement);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Log.e("Firebase", "Error sending announcement", databaseError.toException());
            }
        });
    }

    private void sendAnnouncementToUser(String userEmail, String announcement) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean isSent = false;
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String userDBEmail = userSnapshot.child("email").getValue(String.class);
                    if (userDBEmail != null && userDBEmail.equals(userEmail)) {
                        if (userSnapshot.hasChild("announcements")) {
                            GenericTypeIndicator<ArrayList<String>> t = new GenericTypeIndicator<ArrayList<String>>() {
                            };
                            ArrayList<String> announcements = userSnapshot.child("announcements").getValue(t);
                            assert announcements != null;
                            announcements.add(announcement);
                            userSnapshot.getRef().child("announcements").setValue(announcements);
                            isSent = true;

                        } else {
                            // If announcements do not exist, create a new array with the new announcement
                            ArrayList<String> announcements = new ArrayList<>();
                            announcements.add(announcement);
                            userSnapshot.getRef().child("announcements").setValue(announcements);
                            isSent = true;

                        }
                        break;
                    }
                }

                if (isSent)
                    Toast.makeText(AdminSendAnnouncements.this, "Announcement Created!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(AdminSendAnnouncements.this, "Error Occurred!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Log.e("Firebase", "Error sending announcement to user", databaseError.toException());
            }
        });
    }
}