package com.example.eventmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserThirdActivity extends AppCompatActivity {
    private LinearLayout servicesLayout;
    private Button saveButton;
    private String selectedServices = "";
    private DatabaseReference eventsRef;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_third);
        servicesLayout = findViewById(R.id.servicesLayout);
        saveButton = findViewById(R.id.save);

        firebaseAuth = FirebaseAuth.getInstance();
        eventsRef = FirebaseDatabase.getInstance().getReference().child("Events");

        View.OnClickListener serviceClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button selectedButton = (Button) v;
                String serviceName = selectedButton.getText().toString();
                if (selectedServices.contains(serviceName)) {
                    selectedServices = selectedServices.replace(serviceName + ", ", "");
                    selectedButton.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                } else {
                    selectedServices += serviceName + ", ";
                    selectedButton.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
                }
            }
        };

        for (int i = 0; i < servicesLayout.getChildCount(); i++) {
            View child = servicesLayout.getChildAt(i);
            if (child instanceof Button) {
                Button serviceButton = (Button) child;
                serviceButton.setOnClickListener(serviceClickListener);
            }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the trailing comma and space
                if (selectedServices.length() > 0) {
                    selectedServices = selectedServices.substring(0, selectedServices.length() - 2);
                }

                // Save the selected services to the database
                saveSelectedServicesToDatabase();
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveSelectedServicesToDatabase() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            if (selectedServices.isEmpty()) {
                showToast("Please select at least one service.");
                return; // Exit the method without saving data
            }


            Event event = new Event();
            event.setSelectedHotel(selectedServices);

            eventsRef.child(userId).child("selectedServices").setValue(selectedServices)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Data saved successfully, show toast message
                            showToast("Data saved successfully!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Data failed to save, show an error message if needed
                            showToast("Error saving data. Please try again.");
                        }
                    });
        }
    }

    private void showToast(String message) {
        Toast.makeText(UserThirdActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}