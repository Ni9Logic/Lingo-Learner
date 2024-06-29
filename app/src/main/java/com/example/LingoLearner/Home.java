package com.example.LingoLearner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton buttonDrawerToggle;
    FirebaseAuth auth;

    ImageView nurseryCard;
    ImageView prepCard;
    ImageView oneCard;
    ImageView twoCard;
    ImageView threeCard;

    SearchView searchView;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> dataList;

    private boolean isAlphabetActivitiesHighlighted = false;
    private boolean isCountingActivitiesHighlighted = false;
    private boolean isPhonicsActivitiesHighlighted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = FirebaseAuth.getInstance();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        buttonDrawerToggle = findViewById(R.id.buttonDrawerToggleAdmin);
        nurseryCard = findViewById(R.id.mutateUsersCards);
        prepCard = findViewById(R.id.prepCard);
        oneCard = findViewById(R.id.oneCard);
        twoCard = findViewById(R.id.twoCard);
        threeCard = findViewById(R.id.threeCard);

        searchView = findViewById(R.id.searchViewAdmin);
        listView = findViewById(R.id.listViewAdmin);

        dataList = new ArrayList<>();
        dataList.add("Animals Name");
        dataList.add("Alphabet");
        dataList.add("Birds Name");
        dataList.add("Colors");
        dataList.add("Counting");
        dataList.add("Fruits Name");
        dataList.add("Fun Activity");
        dataList.add("Grammar");
        dataList.add("Vocabulary");
        dataList.add("Vegetables Name");
        dataList.add("Parts of Body");
        dataList.add("Weekdays");
        dataList.add("Months");
        dataList.add("Four Seasons");
        dataList.add("Weather");
        dataList.add("Listening");
        dataList.add("Phonic");
        dataList.add("Reading");
        dataList.add("Rhythms");
        dataList.add("Shapes");
        dataList.add("Short Stories");
        dataList.add("Games");
        dataList.add("Writing");
        dataList.add("Drawing");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                listView.setVisibility(newText.isEmpty() ? View.GONE : View.VISIBLE);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = adapter.getItem(position);

                // Check if the selected item is "Alphabet", "Counting", or "Phonic"
                if (selectedItem.equals("Alphabet")) {
                    // Show the alphabet-related activities
                    showAlphabetActivities();
                } else if (selectedItem.equals("Counting")) {
                    // Show the counting-related activities
                    showCountingActivities();
                } else if (selectedItem.equals("Phonic")) {
                    // Show the phonics-related activities
                    showPhonicsActivities();
                } else {
                    // Start the activity based on the selected item
                    Intent intent = null;
                    switch (selectedItem) {
                        case "Counting":
                            intent = new Intent(Home.this, NurseryCounting.class);
                            break;
                        // Add more cases for other items as needed
                    }
                    if (intent != null) {
                        startActivity(intent);
                    }
                }
                // Handle item click action here, e.g., display a toast
                Toast.makeText(Home.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        buttonDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleDrawer();
            }
        });

        nurseryCard.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, NurseryClass.class);
            startActivity(intent);
        });

        prepCard.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, PrepClass.class);
            startActivity(intent);
        });

        oneCard.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, ClassOne.class);
            startActivity(intent);
        });

        twoCard.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, ClassTwo.class);
            startActivity(intent);
        });

        threeCard.setOnClickListener(v -> {
            Intent intent = new Intent(Home.this, ClassThree.class);
            startActivity(intent);
        });

        navigationView.setNavigationItemSelectedListener(this);
        updateNavigationHeaderFromDatabase();
    }

    private void toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, Login.class));
            finish();
        } else {
            updateNavigationHeaderFromDatabase();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_announcement) {
            Toast.makeText(this, "Announcements Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_setting) {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_logout) {
            auth.signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else if (id == R.id.admin_panel){
            Intent intent = new Intent(Home.this, AdminPanel.class);
            startActivity(intent);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateNavigationHeaderFromDatabase() {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(uid);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String username = dataSnapshot.child("username").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);

                        Log.d("HomeActivity", "Username: " + username);
                        Log.d("HomeActivity", "Email: " + email);

                        updateNavigationHeader(username, email);
                    } else {
                        Log.d("HomeActivity", "No data found for user");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Home.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
                    Log.e("HomeActivity", "DatabaseError: " + databaseError.getMessage());
                }
            });
        }
    }

    private void updateNavigationHeader(String username, String email) {
        View headerView = navigationView.getHeaderView(0);
        TextView textUsername = headerView.findViewById(R.id.textUsername);
        TextView textEmail = headerView.findViewById(R.id.useremail);

        textUsername.setText(username);
        textEmail.setText(email);
    }

    private void showAlphabetActivities() {
        // Toggle the highlight state
        isAlphabetActivitiesHighlighted = !isAlphabetActivitiesHighlighted;

        // Update the UI to reflect the highlight state
        updateHighlightState();
    }

    private void showCountingActivities() {
        // Toggle the highlight state
        isCountingActivitiesHighlighted = !isCountingActivitiesHighlighted;

        // Update the UI to reflect the highlight state
        updateHighlightState();
    }

    private void showPhonicsActivities() {
        // Toggle the highlight state
        isPhonicsActivitiesHighlighted = !isPhonicsActivitiesHighlighted;

        // Update the UI to reflect the highlight state
        updateHighlightState();
    }

    private void updateHighlightState() {
        // Update the UI to reflect the highlight state of alphabet-related activities
        if (isAlphabetActivitiesHighlighted) {
            nurseryCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
            prepCard.setBackgroundColor(getResources().getColor(R.color.another_highlight_color));
            nurseryCard.setAlpha(0.5f);
            prepCard.setAlpha(0.5f);
        } else {
            nurseryCard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            prepCard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            nurseryCard.setAlpha(1.0f);
            prepCard.setAlpha(1.0f);
        }

        // Update the UI to reflect the highlight state of counting-related activities
        if (isCountingActivitiesHighlighted) {
            nurseryCard.setBackgroundColor(getResources().getColor(R.color.counting_highlight_color));
            prepCard.setBackgroundColor(getResources().getColor(R.color.another_counting_highlight_color));
            oneCard.setBackgroundColor(getResources().getColor(R.color.yet_another_counting_highlight_color));
            nurseryCard.setAlpha(0.5f);
            prepCard.setAlpha(0.5f);
            oneCard.setAlpha(0.5f);
        } else {
            nurseryCard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            prepCard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            oneCard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            nurseryCard.setAlpha(1.0f);
            prepCard.setAlpha(1.0f);
            oneCard.setAlpha(1.0f);
        }

        // Update the UI to reflect the highlight state of phonics-related activities
        if (isPhonicsActivitiesHighlighted) {
            nurseryCard.setBackgroundColor(getResources().getColor(R.color.phonics_highlight_color));
            prepCard.setBackgroundColor(getResources().getColor(R.color.another_phonics_highlight_color));
            nurseryCard.setAlpha(0.5f);
            prepCard.setAlpha(0.5f);
        } else {
            nurseryCard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            prepCard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            nurseryCard.setAlpha(1.0f);
            prepCard.setAlpha(1.0f);
        }

    }

}
