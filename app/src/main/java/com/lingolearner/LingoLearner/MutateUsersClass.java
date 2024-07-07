package com.lingolearner.LingoLearner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.gridlayout.widget.GridLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MutateUsersClass extends AppCompatActivity {
    List<String> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutate_users);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );



        mDatabase.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    String username = userSnapshot.child("username").getValue(String.class);
                    String email = userSnapshot.child("email").getValue(String.class);
                    // Add the user to your list
                    userList.add(username);
                }
                // Update your UI with the list of users
                updateUI(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle the error
            }
        });
    }

    private void updateUI(List<String> userList) {
        // Create a GridLayout or RecyclerView to display the list of users
        GridLayout gridLayout = findViewById(R.id.mutateUsersGrid);

        for (String user : userList) {
            // Create a CardView for each user
            CardView cardView = new CardView(this);
            GridLayout.LayoutParams cardViewLayoutParams = new GridLayout.LayoutParams();
            cardViewLayoutParams.setMargins(12, 12, 12, 12);
            cardView.setLayoutParams(cardViewLayoutParams);
            cardView.setRadius(16);
            cardView.setElevation(5);

            // Create a LinearLayout to hold the user's information
            // Create a LinearLayout to hold the user's information
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER); // Add this line to center the content
            LinearLayout.LayoutParams linearLayoutLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            linearLayout.setLayoutParams(linearLayoutLayoutParams);
            linearLayout.setPadding(16, 0, 16, 16);

            // Create an ImageView to display the user's image
            ImageView userImageView = new ImageView(this);
            LinearLayout.LayoutParams userImageViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 300);
            userImageViewLayoutParams.gravity = Gravity.CENTER_HORIZONTAL; // Add this line to center the ImageView
            userImageView.setLayoutParams(userImageViewLayoutParams);

            // Generating Unique IDS for each image
            int imageID = View.generateViewId();
            userImageView.setId(imageID);
            userImageView.setImageResource(R.drawable.user); // Replace with the actual image resource
            linearLayout.addView(userImageView);

            // Create a TextView to display the user's username
            TextView usernameTextView = new TextView(this);
            usernameTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            usernameTextView.setText(user);
            usernameTextView.setTextSize(18);
            usernameTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            usernameTextView.setPadding(0, 12, 0, 0);
            linearLayout.addView(usernameTextView);

            // Add the LinearLayout to the CardView
            cardView.addView(linearLayout);

            // Add the CardView to the GridLayout
            gridLayout.addView(cardView);

            // Add a click listener to the ImageView
            userImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle the click event
                    // You can access the username of the clicked user using the 'user' variable
                    Toast.makeText(MutateUsersClass.this, "You clicked on " + user, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MutateUsersClass.this, MutateUserInfo.class);
                    intent.putExtra("Username", user);
                    startActivity(intent);
                }
            });
        }
    }
}