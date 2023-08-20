package com.example.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminAddvenuedetails extends AppCompatActivity {

    private EditText nameEditText, addressEditText, occupancyEditText, emailEditText, numberEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addvenuedetailsadmin);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        DatabaseReference venueRef = FirebaseDatabase.getInstance().getReference("addvenue");

        nameEditText = findViewById(R.id.add_name);
        addressEditText = findViewById(R.id.add_address);
        occupancyEditText = findViewById(R.id.add_occupancy);
        emailEditText = findViewById(R.id.add_email);
        numberEditText = findViewById(R.id.add_number);
        saveButton = findViewById(R.id.save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInput()) {
                    saveVenue(venueRef);
                }
            }
        });
    }

    private void saveVenue(DatabaseReference venueRef) {
        String name = nameEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String occupancy = occupancyEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String number = numberEditText.getText().toString().trim();

        String venueId = venueRef.push().getKey(); // Generate a new venueId
        Venue venue = new Venue(venueId, name, address, occupancy, email, number); // Include venueId

        if (venueId != null) {
            venueRef.child(venueId).setValue(venue)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Data saved successfully, show a toast message
                            Toast.makeText(AdminAddvenuedetails.this, "Venue details saved successfully", Toast.LENGTH_SHORT).show();

                            // Clear input fields after saving
                            nameEditText.setText("");
                            addressEditText.setText("");
                            occupancyEditText.setText("");
                            emailEditText.setText("");
                            numberEditText.setText("");

                            // Open AdminViewvenuedetails activity and pass the venueId
                            Intent intent = new Intent(AdminAddvenuedetails.this, AdminViewvenuedetails.class);
                            intent.putExtra("venueId", venueId);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Failed to save data, show a toast message
                            Toast.makeText(AdminAddvenuedetails.this, "Failed to save venue details", Toast.LENGTH_SHORT).show();
                            Log.e("Failed to save data", e.toString());
                        }
                    });
        }
    }

    private boolean validateInput() {
        String name = nameEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String occupancy = occupancyEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String number = numberEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError("Name is required");
            return false;
        }

        if (address.isEmpty()) {
            addressEditText.setError("Address is required");
            return false;
        }

        if (occupancy.isEmpty()) {
            occupancyEditText.setError("Occupancy is required");
            return false;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email address");
            return false;
        }

        if (number.isEmpty() || !android.util.Patterns.PHONE.matcher(number).matches()) {
            numberEditText.setError("Enter a valid phone number");
            return false;
        }
        return true;
    }
}
