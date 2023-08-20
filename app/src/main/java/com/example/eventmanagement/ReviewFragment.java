package com.example.eventmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class ReviewFragment extends Fragment {

    private MultiAutoCompleteTextView feedbackEditText;
    private RatingBar ratingBar;
    private Button submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_review, container, false); // Replace with your layout resource name

        feedbackEditText = rootView.findViewById(R.id.feedbackEditText);
        ratingBar = rootView.findViewById(R.id.ratingBar);
        submitButton = rootView.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReview();
            }
        });

        return rootView;
    }

    private void submitReview() {
        String feedback = feedbackEditText.getText().toString();
        float rating = ratingBar.getRating();

        // TODO: Perform the submission logic here (e.g., sending data to a server)

        // For demonstration, let's just display the feedback and rating in a toast message
        String message = "Feedback: " + feedback + "\nRating: " + rating;
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
