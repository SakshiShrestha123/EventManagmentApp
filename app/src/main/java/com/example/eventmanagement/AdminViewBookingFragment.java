package com.example.eventmanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class AdminViewBookingFragment extends Fragment {

    DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("Events");
    // Button confirmbutton;
    private List<Event> eventList = new ArrayList<>();
    private CustomEventAdapter customAdapter;



    public AdminViewBookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_view_booking, container, false);

        //confirmButton = view.findViewById(R.id.confirmbutton);

        // Initialize ListView and CustomEventAdapter
        ListView listView = view.findViewById(R.id.adminlistview);


        // Retrieve data from Firebase and update the adapter
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                eventList.clear(); // Clear the list before adding new data
                for (DataSnapshot eventSnapshot : dataSnapshot.getChildren()) {
                    Event event = eventSnapshot.getValue(Event.class);
                    if (event != null) {
                        eventList.add(event);
                    }
                }
                customAdapter = new CustomEventAdapter(getActivity(), eventList);
                Log.d("lists of events",eventList.toString());
                listView.setAdapter(customAdapter);
                //customAdapter.notifyDataSetChanged(); // Notify the CustomEventAdapter of data changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors, if any
            }
        });

        return view;
    }
}