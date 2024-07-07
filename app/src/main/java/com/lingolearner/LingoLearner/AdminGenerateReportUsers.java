package com.lingolearner.LingoLearner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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


public class AdminGenerateReportUsers extends AppCompatActivity {

    private LinearLayout usersLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report_users);

        usersLayout = findViewById(R.id.activity_generate_reports);
        // Retrieve the list of users from the database
        fetchUsers();
    }

    private void fetchUsers() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String username = userSnapshot.child("username").getValue(String.class);
                        String email = userSnapshot.child("email").getValue(String.class);
                        displayUser(username, email);
                    }
                } else {
                    // Handle the case where there are no users
                    Toast.makeText(AdminGenerateReportUsers.this, "No users found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
                Log.e("Firebase", "Error fetching users", databaseError.toException());
            }
        });
    }

    private void displayUser(String username, String email) {
        CardView cardView = new CardView(AdminGenerateReportUsers.this);
        cardView.setRadius(16);
        cardView.setElevation(5);
        cardView.setMaxCardElevation(5);
        cardView.setContentPadding(18, 18, 18, 18);
        cardView.setCardBackgroundColor(getResources().getColor(R.color.white));

        LinearLayout linearLayout = new LinearLayout(AdminGenerateReportUsers.this);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(18, 18, 18, 18);

        TextView textView = new TextView(AdminGenerateReportUsers.this);
        textView.setText(email);
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
        // Add an OnClickListener to the CardView
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event here
                Toast.makeText(AdminGenerateReportUsers.this, "Card clicked: " + email, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AdminGenerateReportUsers.this, Admin_User_Report.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        usersLayout.addView(cardView);
    }
}