package com.example.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    FrameLayout fl;
    FragmentTransaction ft;
    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle adt;
    Toolbar tb;
    CardView cv1, cv2, cv3, cv4, cv5, cv6;

    private String selectEvent;
    private DatabaseReference userRef;
    private FirebaseAuth firebaseAuth;
    private String userId;
    private String selectedHotelId; // Added selectedHotelId
    private String userFeedback = "Sample Feedback";

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        firebaseAuth = FirebaseAuth.getInstance();

        cv1 = findViewById(R.id.card1);
        cv2 = findViewById(R.id.card2);
        cv3 = findViewById(R.id.card3);
        cv4 = findViewById(R.id.card4);
        cv5 = findViewById(R.id.card5);
        cv6 = findViewById(R.id.card6);
        loadAndDisplayTopRatedHotels();

        userRef = FirebaseDatabase.getInstance().getReference().child("Events");

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedHotelId = getResources().getString(R.string.hotel_id_card1); // Replace with the actual hotel ID
                selectEvent = "Wedding";
                saveSelectedEventToDatabase();

                Intent i = new Intent(UserActivity.this, UserSecondActivity.class);
                i.putExtra("selected_event", selectEvent);
                startActivity(i);
            }
        });


        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedHotelId = getResources().getString(R.string.hotel_id_card2);
                selectEvent = "Anniversary";
                saveSelectedEventToDatabase();
                Intent i = new Intent(UserActivity.this, UserSecondActivity.class);
                i.putExtra("selected_event", selectEvent);
                startActivity(i);
            }
        });

        cv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedHotelId = getResources().getString(R.string.hotel_id_card3);
                selectEvent = "Birthday";
                saveSelectedEventToDatabase();
                Intent i = new Intent(UserActivity.this, UserSecondActivity.class);
                i.putExtra("selected_event", selectEvent);
                startActivity(i);
            }
        });

        cv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedHotelId = getResources().getString(R.string.hotel_id_card4);
                selectEvent = "BachelorParty";
                Intent i = new Intent(UserActivity.this, UserSecondActivity.class);
                i.putExtra("selected_event", selectEvent);
                startActivity(i);
            }
        });

        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedHotelId = getResources().getString(R.string.hotel_id_card5);
                selectEvent = "Meeting";
                saveSelectedEventToDatabase();
                Intent i = new Intent(UserActivity.this, UserSecondActivity.class);
                i.putExtra("selected_event", selectEvent);
                startActivity(i);
            }
        });

        cv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedHotelId = getResources().getString(R.string.hotel_id_card6);
                selectEvent = "Graduation";
                saveSelectedEventToDatabase();
                Intent i = new Intent(UserActivity.this, UserSecondActivity.class);
                i.putExtra("selected_event", selectEvent);
                startActivity(i);
            }
        });

        fl = findViewById(R.id.framelayout);
        nv = findViewById(R.id.navigation);
        dl = findViewById(R.id.drawerlayout);
        tb = findViewById(R.id.toolbar);

        adt = new ActionBarDrawerToggle(UserActivity.this, dl, tb, R.string.open, R.string.close);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.home) {
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout, new HomeFragment());
                    ft.commit();
                }

                if (id == R.id.profile) {
                    replaceFragment(new UserProfileFragment());
                }

                if (id == R.id.details) {
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout, new ViewDetailFragment());
                    ft.commit();
                }

                if (id == R.id.changepw) {
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout, new ChangePasswordFragment());
                    ft.commit();
                }
                if (id == R.id.review) {
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout, new ReviewFragment());
                    ft.commit();
                }
                if (id == R.id.logout) {
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout, new LogoutFragment());
                    ft.commit();
                }
                dl.closeDrawers();
                return false;
            }
        });
    }

    private void loadAndDisplayTopRatedHotels() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getUid();

            DatabaseReference hotelRef = FirebaseDatabase.getInstance().getReference().child("hotels");

            hotelRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<HotelRatingClass> hotelRatings = new ArrayList<>();

                    for (DataSnapshot hotelSnapshot : dataSnapshot.getChildren()) {
                        String hotelId = hotelSnapshot.getKey();
                        float totalRating = 0;
                        int numRatings = 0;

                        for (DataSnapshot reviewSnapshot : hotelSnapshot.child("reviews").getChildren()) {
                            float rating = Float.parseFloat(reviewSnapshot.child("rating").getValue().toString());
                            totalRating += rating;
                            numRatings++;
                        }

                        if (numRatings > 0) {
                            float averageRating = totalRating / numRatings;

                            // Retrieve the Hotel object based on hotelId
                            Hotel hotel = getHotelById(hotelId);
                            if (hotel != null) {
                                HotelRatingClass hotelRating = new HotelRatingClass(userId, hotelId, averageRating, userFeedback);
                                hotelRatings.add(hotelRating);
                            }
                        }
                    }

                    // Sort hotels by average rating (descending)
                    Collections.sort(hotelRatings, new Comparator<HotelRatingClass>() {
                        @Override
                        public int compare(HotelRatingClass hotel1, HotelRatingClass hotel2) {
                            return Float.compare(hotel2.getAverageRating(), hotel1.getAverageRating());
                        }
                    });

                    // Display the top-rated hotels in a RecyclerView or ListView
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    HotelAdapter hotelAdapter = new HotelAdapter(hotelRatings);
                    recyclerView.setAdapter(hotelAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(UserActivity.this));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle any errors here
                }
            });
        }
    }

    private Hotel getHotelById(String hotelId) {
        // Get a reference to the Firebase database
        DatabaseReference hotelRef = FirebaseDatabase.getInstance().getReference().child("hotels").child(hotelId);

        // Attach a listener to retrieve the hotel data
        hotelRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if the hotel data exists
                if (dataSnapshot.exists()) {
                    // Convert the DataSnapshot into a Hotel object
                    Hotel hotel = dataSnapshot.getValue(Hotel.class);

                    // Now you have the hotel object, you can use it as needed
                    if (hotel != null) {
                        // Do something with the hotel data
                    }
                } else {
                    // Handle the case where the hotel data does not exist
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur during the database query
            }
        });
        return null;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Nullable
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        adt.syncState();
    }

    private void saveSelectedEventToDatabase() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            Event event = new Event();
            event.setSelectedEvent(selectEvent);

            userRef.child(userId).setValue(event);
        }
    }
}