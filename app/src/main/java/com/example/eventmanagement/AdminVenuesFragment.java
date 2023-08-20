package com.example.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

public class AdminVenuesFragment extends Fragment {

    Button addVenueButton, viewVenueButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_venuesadmin, container, false);

        addVenueButton = view.findViewById(R.id.addvenues);
        viewVenueButton = view.findViewById(R.id.viewvenues);

        addVenueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AdminAddvenuedetails.class);
                startActivity(intent);
            }
        });

        viewVenueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Replace this with actual venueId retrieval from your database
                String venueId = "venue_id_placeholder";

                Intent intent = new Intent(getContext(), AdminViewvenuedetails.class);
                intent.putExtra("venueId", venueId);
                startActivity(intent);
            }
        });

        return view;
    }
}
