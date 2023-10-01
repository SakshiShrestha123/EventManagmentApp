package com.example.eventmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserViewDetailsFragment extends Fragment {

    private DatabaseReference userRef;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Database reference
        userRef = FirebaseDatabase.getInstance().getReference("Events");
        //getReference().child("Events");


        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_view_details, container, false);

        final TextView selectedEventTextView = view.findViewById(R.id.selectedevent);
        final TextView selectedHotelTextView = view.findViewById(R.id.selectedhotel);
        final TextView selectedServicesTextView = view.findViewById(R.id.services);

        final TextView eventNameTextView = view.findViewById(R.id.eventname1);
        final TextView guestsTextView = view.findViewById(R.id.guests1);
        final TextView entryDateTextView = view.findViewById(R.id.entrydate1);
        final TextView exitDateTextView = view.findViewById(R.id.exitdate1);

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            Log.e("userId", "-"+userId);
            // Retrieve the user's data based on their UID
            userRef.child(userId).addValueEventListener
                    //addListenerForSingleValueEvent
                            (new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        Log.e("dataSnapshot", "exists");
                                        // Data exists for the user
                                        Event event = dataSnapshot.getValue(Event.class);

//                        Log.e("event", event);



                                        // Populate UI elements with selected event details
                                        selectedEventTextView.setText("Selected Event: " + event.getSelectedEvent());
                                        selectedHotelTextView.setText("Selected Hotel: " + event.getSelectedHotel());
                                        selectedServicesTextView.setText("Selected Services: " + event.getSelectedServices());

                                        // Additional details
                                        eventNameTextView.setText("Event Name: " + event.getEventName());
                                        guestsTextView.setText("Number of Guests: " + event.getNumberOfGuests());
                                        entryDateTextView.setText("Entry Date: " + event.getEntryDate());
                                        exitDateTextView.setText("Exit Date: " + event.getExitDate());
                                    } else {
                                        Log.e("dataSnapshot", "doesn't exists");
                                        // Data does not exist for the user
                                        // Handle this case (e.g., show an error message)
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    // Handle any errors that may occur during data retrieval
                                }
                            });
        }

        return view;
    }
}