package com.lingolearner.LingoLearner;

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
import java.util.Objects;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageButton buttonDrawerToggle;
    FirebaseAuth auth;
    TextView NavEmail;
    TextView NavUserName;

    ImageView nurseryCard;
    ImageView prepCard;
    ImageView oneCard;
    ImageView twoCard;
    ImageView threeCard;

    SearchView searchView;
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> dataList;

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
                if (selectedItem != null) {
                    resetHighlightState();
                    switch (selectedItem) {
                        case "Drawing":
                            highlightDrawingActivity();
                            break;
                        case "Writing":
                            highlightWritingActivity();
                            break;
                        case "Games":
                            highlightGamesActivity();
                            break;
                        case "Shapes":
                            highlightShapesActivity();
                            break;
                        case "Rhythms":
                            highlightRhythmActivity();
                            break;
                        case "Reading":
                            highlightReadingActivity();
                            break;
                        case "Listening":
                            highlightListeningActivity();
                            break;
                        case "Weather":
                            highlightWeatherActivity();
                            break;
                        case "Four Seasons":
                            highlightFourSeasonsActivity();
                            break;
                        case "Months":
                            highlightMonthsActivity();
                            break;
                        case "Weekdays":
                            highlightWeekdaysActivity();
                            break;
                        case "Vegetables Name":
                            highlightVegetablesActivity();
                            break;
                        case "Vocabulary":
                            highlightVocabularyActivities();
                            break;
                        case "Alphabet":
                            highlightAlphabetActivities();
                            break;
                        case "Counting":
                            highlightCountingActivities();
                            break;
                        case "Phonic":
                            highlightPhonicsActivities();
                            break;
                        case "Animals Name":
                            highlightAnimalsActivity();
                            break;
                        case "Birds Name":
                            highlightBirdsActivity();
                            break;
                        case "Colors":
                            highlightColorsActivity();
                            break;
                        case "Fruits Name":
                            highlightFruitsActivity();
                            break;
                        case "Fun Activity":
                            highlightFunActivity();
                            break;
                        case "Grammar":
                            highlightGrammarActivity();
                            break;
                        case "Parts of Body":
                            highlightPartsOfBodyActivity();
                            break;
                        case "Short Stories":
                            highlightShortStoriesActivity();
                            break;
                        default:
                            Intent intent = null;
                            if (selectedItem.equals("Counting")) {
                                intent = new Intent(Home.this, NurseryCounting.class);
                            }
                            if (intent != null) {
                                startActivity(intent);
                            }
                            break;
                    }
                }
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

        View headerView = navigationView.getHeaderView(0);
        NavUserName = headerView.findViewById(R.id.textUsername);
        NavEmail = headerView.findViewById(R.id.useremail);
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
            String userEmail = currentUser.getEmail();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.orderByChild("email").equalTo(userEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String username = userSnapshot.child("username").getValue(String.class);
                        String email = userSnapshot.child("email").getValue(String.class);

                        NavUserName.setText(username);
                        NavEmail.setText(email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w("Error", "Error getting user data", databaseError.toException());
                }
            });
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
            TrackActivities.trackActivity("View Announcements Activity");
            Toast.makeText(this, "Announcements Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Home.this, UserAnnouncements.class);
            startActivity(intent);
        } else if (id == R.id.action_setting) {
            Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Home.this, EditProfile.class);
            startActivity(intent);
        } else if (id == R.id.action_logout) {
            auth.signOut();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (id == R.id.admin_panel) {
            // Check if user is an admin
            FirebaseUser user = auth.getCurrentUser();
            if (user != null) {
                final String userEmail = user.getEmail();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean isAdmin = false;
                        for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                            String userEmailFromDB = userSnapshot.child("email").getValue(String.class);
                            if (userEmailFromDB != null && userEmailFromDB.equals(userEmail)) {
                                String UserRole = userSnapshot.child("Role").getValue(String.class);
                                assert UserRole != null;
                                if (UserRole.equals("Admin")) {
                                    isAdmin = true;
                                    break;
                                }
                            }
                        }
                        if (isAdmin) {
                            Intent intent = new Intent(Home.this, AdminPanel.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Home.this, "You are not an administrator", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("Error", "Error getting user role", databaseError.toException());
                    }
                });
            } else {
                Toast.makeText(Home.this, "You are not an administrator", Toast.LENGTH_SHORT).show();
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void updateNavigationHeaderFromDatabase() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String username = dataSnapshot.child("username").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);

                    View headerView = navigationView.getHeaderView(0);
                    TextView navUsername = headerView.findViewById(R.id.textUsername);
                    TextView navEmail = headerView.findViewById(R.id.useremail);

                    if (username != null && email != null) {
                        navUsername.setText(username);
                        navEmail.setText(email);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("Error", "Error loading user data", databaseError.toException());
                }
            });
        }
    }

    private void highlightDrawingActivity(){
        threeCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightWritingActivity(){
        oneCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
        twoCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
        threeCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightGamesActivity(){
        threeCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightShortStoriesActivity(){
        twoCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
        threeCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightShapesActivity(){
        nurseryCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightRhythmActivity(){
        prepCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }

    private void highlightReadingActivity(){
        threeCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightListeningActivity(){
        twoCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightWeatherActivity(){
        twoCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightFourSeasonsActivity(){
        twoCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightMonthsActivity(){
        oneCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightWeekdaysActivity(){
        oneCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightPartsOfBodyActivity(){
        oneCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightVegetablesActivity(){
        prepCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightVocabularyActivities(){
        oneCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
        twoCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
        threeCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightGrammarActivity(){
        oneCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
        twoCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
        threeCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void highlightFunActivity(){
        prepCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }
    private void resetHighlightState() {
        nurseryCard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        prepCard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        oneCard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        twoCard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        threeCard.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void highlightAlphabetActivities() {
        nurseryCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
        prepCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }

    private void highlightCountingActivities() {
        nurseryCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
        prepCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
        oneCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }

    private void highlightPhonicsActivities() {
        nurseryCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
        prepCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }

    private void highlightAnimalsActivity(){
        prepCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }

    private void highlightBirdsActivity(){
        oneCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }

    private void highlightColorsActivity(){
        nurseryCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));
    }

    private void highlightFruitsActivity(){
        prepCard.setBackgroundColor(getResources().getColor(R.color.highlight_color));

    }
}
