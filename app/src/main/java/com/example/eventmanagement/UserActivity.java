package com.example.eventmanagement;

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

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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


    private void replaceFragment(Fragment fragment) {
        fl.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.addToBackStack(null);
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

        userRef = FirebaseDatabase.getInstance().getReference().child("Events");


        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                selectEvent = "BachelorParty";
                Intent i = new Intent(UserActivity.this, UserSecondActivity.class);
                i.putExtra("selected_event", selectEvent);
                startActivity(i);
            }
        });

        cv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        //ft = getSupportFragmentManager().beginTransaction();
        //   ft.add(R.id.framelayout, new HomeFragment());
        //    ft.commit();

        adt = new ActionBarDrawerToggle(UserActivity.this, dl, tb, R.string.open, R.string.close);


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id = item.getItemId();
                if (id == R.id.home) {
//                    replaceFragment(new HomeFragment());
                    fl.setVisibility(View.GONE);
                }
                if (id == R.id.profile) {

                    replaceFragment(new UserProfileFragment());
                }


                if (id == R.id.details) {
                    replaceFragment(new UserViewDetailsFragment());
//                    ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.framelayout, new UserViewDetailsFragment());
//                    ft.commit();
                }

                if (id == R.id.changepw) {
                    replaceFragment(new ChangePasswordFragment());
//                    ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.framelayout, new ChangePasswordFragment());
//                    ft.commit();
                }

                if (id == R.id.review) {
                    replaceFragment(new ReviewFragment());

                }


                if (id == R.id.logout) {
                    replaceFragment(new LogoutFragment());
//                    ft = getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.framelayout, new LogoutFragment());
//                    ft.commit();

                }
                dl.closeDrawers();
                return false;
            }
        });
    }

    //later added
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

            // Create a User object with the selected event
            Event event = new Event();
            event.setSelectedEvent(selectEvent);
            // Set other user properties...

            // Save the user data to the database under the user's ID
//            userRef.child(userId).setValue(event);
        }
    }
}
