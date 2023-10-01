package com.example.eventmanagement;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class UserProfileFragment extends Fragment {
    private TextView textViewName;
    private TextView textViewAddress;
    private TextView textViewEmail;
    private TextView textViewPhoneNo;
    private TextView textViewPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,null);





        // Initialize the TextView fields
        textViewName = view.findViewById(R.id.editTextName);
        textViewAddress = view.findViewById(R.id.editaddress);
        textViewEmail = view.findViewById(R.id.editTextEmail);
        textViewPhoneNo = view.findViewById(R.id.editphoneno);
        textViewPassword = view.findViewById(R.id.editTextPassword);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {


                    // Log retrieved data  //later
                    Log.d("FirebaseData", "Snapshot: " + snapshot.toString());

                    String address = snapshot.child("Address").getValue(String.class);
                    String password = snapshot.child("Password").getValue(String.class);
                    String phoneNo = snapshot.child("PhoneNumber").getValue(String.class);
                    String email = snapshot.child("UserEmail").getValue(String.class);
                    String username = snapshot.child("Username").getValue(String.class);

                    Log.d("FirebaseData", "Username: " + username);
                    Log.d("FirebaseData", "Address: " + address);
                    Log.d("FirebaseData", "Email: " + email);
                    Log.d("FirebaseData", "PhoneNo: " + phoneNo);
                    Log.d("FirebaseData", "Password: " + password);

                    // Set the retrieved values to the respective TextViews

                    textViewAddress.setText("Address: " + address);
                    textViewPassword.setText("Password: " + password);
                    textViewPhoneNo.setText("PhoneNo: " + phoneNo);
                    textViewEmail.setText("Email: " + email);
                    textViewName.setText("Username: " + username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error

            }
        });


        return view;
    }
}



