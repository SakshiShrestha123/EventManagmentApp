package com.example.eventmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

public class AdminActivity extends AppCompatActivity {
    FrameLayout fl;
    FragmentTransaction ft;
    DrawerLayout dl;
    NavigationView nv;
    ActionBarDrawerToggle adt;
    Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        // Determine the user type (admin or user)
        boolean isAdmin = true; // Set this based on your user authentication logic

        // Inflate the appropriate header layout
        View headerView;
        if (isAdmin) {
            headerView = LayoutInflater.from(this).inflate(R.layout.headeradmin, null);
        } else {
            headerView = LayoutInflater.from(this).inflate(R.layout.header, null);
        }

        // Set the custom view in the action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(headerView,
                    new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                            ActionBar.LayoutParams.MATCH_PARENT));
            actionBar.setDisplayHomeAsUpEnabled(true);  // Enable the Up button
        }




        fl = findViewById(R.id.framelayout);
        nv = findViewById(R.id.navigation);
        dl = findViewById(R.id.drawerlayout);
        tb = findViewById(R.id.toolbar);
        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.framelayout, new AdminViewBookingFragment());
        ft.commit();


        adt = new ActionBarDrawerToggle(AdminActivity.this, dl, tb, R.string.open, R.string.close);


        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id = item.getItemId();
                if (id == R.id.booking) {
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout,new AdminViewBookingFragment());
                    ft.commit();
                }

                if (id == R.id.services) {
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout, new ServicesFragment());
                    ft.commit();

                }
                if (id == R.id.venues) {
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout, new AdminVenuesFragment());
                    ft.commit();

                }
                if (id == R.id.adminlogout) {
                    ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.framelayout, new AdminLogoutFragment());
                    ft.commit();

                }
                dl.closeDrawers();
                return false;
            }
        });
    }

    @Nullable
    @Override
    protected void onPostCreate (@Nullable Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        adt.syncState();
    }
    }
