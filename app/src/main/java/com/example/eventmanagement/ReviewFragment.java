package com.example.eventmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.Toast;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewFragment extends Fragment {

    private DatabaseReference databaseReference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("reviews");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);

        Button submitButton = view.findViewById(R.id.submitButton);
        // Assuming you have set up Firebase Authentication elsewhere in your app

// ...

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the user is authenticated
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    // User is authenticated, get their UID
                    String userUid = currentUser.getUid();

                    // Get the user's rating from the RatingBar
                    RatingBar ratingBar = view.findViewById(R.id.ratingBar);
                    float userRating = ratingBar.getRating();

                    // Check if the user has provided a non-zero rating
                    if (userRating > 0) {
                        // Get the user's feedback from the MultiAutoCompleteTextView
                        MultiAutoCompleteTextView feedbackTextView = view.findViewById(R.id.feedbackEditText);
                        String userFeedback = feedbackTextView.getText().toString();

                        // Create a Review object with the user's UID, rating, and feedback
                        //TODO: get hotelId from F.B. db venue-id
                        //getVenueId()
                        HotelRatingClass review = new HotelRatingClass(userUid, "someHotelId", userRating, userFeedback);


                        // Generate a unique key for the review
                        String reviewId = databaseReference.push().getKey();

                        // Save the review to the database under a unique key
                        databaseReference.child(reviewId).setValue(review);

                        // Show a success message to the user
                        Toast.makeText(getActivity(), "Review submitted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        // Show an error message to the user indicating that a rating is required
                        Toast.makeText(getActivity(), "Please provide a rating", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // User is not authenticated, prompt them to log in or create an account
                    // You can implement the authentication flow here
                    Toast.makeText(getActivity(), "Please log in to submit a review", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}
