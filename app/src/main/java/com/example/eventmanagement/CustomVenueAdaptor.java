package com.example.eventmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomVenueAdaptor extends ArrayAdapter<Venue> {
    private List<Venue> venueList;
    private TextView nameTextView, addressTextView, occupancyTextView, emailTextView, numberTextView;
    public CustomVenueAdaptor(@NonNull Context context, List<Venue> venueList) {
        super(context, 0, venueList);
        this.venueList = venueList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_viewvenuedetailsadmin, parent, false);
        }

        final Venue venue = getItem(position);

        if(venue !=null){
            nameTextView = convertView.findViewById(R.id.view_name);
            addressTextView = convertView.findViewById(R.id.view_address);
            occupancyTextView = convertView.findViewById(R.id.view_occupancy);
            emailTextView = convertView.findViewById(R.id.view_email);
            numberTextView = convertView.findViewById(R.id.view_number);

            nameTextView.setText("Name: " + venue.getName());
            addressTextView.setText("Address: " + venue.getAddress());
            occupancyTextView.setText("Occupancy: " + venue.getOccupancy());
            emailTextView.setText("Email: " + venue.getEmail());
            numberTextView.setText("Number: " + venue.getNumber());
        }

        return convertView;
    }
}
