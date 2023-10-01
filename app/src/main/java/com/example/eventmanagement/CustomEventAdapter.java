package com.example.eventmanagement;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomEventAdapter extends ArrayAdapter<Event> {

    private List<Event> eventList;

    public CustomEventAdapter(@NonNull Context context, List<Event> eventList) {
        super(context, 0, eventList);
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adminviewbookingrelated, parent, false);
        }

        final Event event = getItem(position);


        if (event != null) {
            TextView eventNameTextView = convertView.findViewById(R.id.eventName);
            TextView eventHotelTextView = convertView.findViewById(R.id.eventHotels);
            TextView eventServiceTextView = convertView.findViewById(R.id.eventService);
            TextView eventEntryDate = convertView.findViewById(R.id.eventEntryDate);
            TextView eventExitDate = convertView.findViewById(R.id.eventExitDate);

            if (eventNameTextView != null) {
                eventNameTextView.setText(event.getEventName());
            }

            if (eventHotelTextView != null) {
                eventHotelTextView.setText(event.getSelectedHotel());
            }
            if (eventServiceTextView != null) {
                eventServiceTextView.setText(event.getSelectedServices());
            }
            if (eventEntryDate != null) {
                eventEntryDate.setText(event.getEntryDate());
            }
            if (eventExitDate != null) {
                eventExitDate.setText(event.getExitDate());
            }

            // Assuming the User class has a getEmail() method
            Event.User user = event.getUser(); // Get the User associated with this Event

            if (user != null) {
                String userEmail = user.getEmail();
                // Display the user's email in a TextView (replace 'userEmailTextView' with your actual TextView)
                TextView userEmailTextView = convertView.findViewById(R.id.userEmailTextView);
                if (userEmailTextView != null) {
                    userEmailTextView.setText("User Email: " + userEmail);
                }
            }



//        if (event != null) {
//            TextView eventNameTextView = convertView.findViewById(R.id.eventName);
//            TextView eventHotelTextView = convertView.findViewById(R.id.eventHotels);
//
//            eventNameTextView.setText(event.getEventName());
//            eventHotelTextView.setText(event.getSelectedHotel());

            // Set an OnClickListener for each item
//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    // Handle item click here
//                    String userID = event.getUserId();
//                    // You can now use the userID as needed
//                    Toast.makeText(getContext(), "User ID: " + userID, Toast.LENGTH_SHORT).show();
//                }
//            });
        }

        return convertView;
    }
}
