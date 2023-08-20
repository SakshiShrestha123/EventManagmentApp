package com.example.eventmanagement;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminViewvenuedetails extends AppCompatActivity {

    private TextView nameTextView, addressTextView, occupancyTextView, emailTextView, numberTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewvenuedetailsadmin);

        // Initialize TextViews
        nameTextView = findViewById(R.id.view_name);
        addressTextView = findViewById(R.id.view_address);
        occupancyTextView = findViewById(R.id.view_occupancy);
        emailTextView = findViewById(R.id.view_email);
        numberTextView = findViewById(R.id.view_number);

        // Retrieve venueId from intent
        String venueId = getIntent().getStringExtra("venueId");

        // Initialize Firebase
        DatabaseReference venueRef = FirebaseDatabase.getInstance().getReference("addvenue").child(venueId);

        venueRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Venue venue = snapshot.getValue(Venue.class);
                    if (venue != null) {
                        // Populate the UI elements with the retrieved venue details
                        nameTextView.setText("Name: " + venue.getName());
                        addressTextView.setText("Address: " + venue.getAddress());
                        occupancyTextView.setText("Occupancy: " + venue.getOccupancy());
                        emailTextView.setText("Email: " + venue.getEmail());
                        numberTextView.setText("Number: " + venue.getNumber());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}
