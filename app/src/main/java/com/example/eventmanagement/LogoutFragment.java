package com.example.eventmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LogoutFragment extends Fragment {
    public LogoutFragment() {
        // Required empty public constructor
    }





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Perform logout confirmation dialog
        showLogoutConfirmationDialog();

        // Return null as the fragment doesn't have a UI layout
        return null;
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked "Yes", perform logout
                performLogout();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User clicked "No", do nothing or handle as needed
            }
        });
        builder.setCancelable(false); // Prevent dialog dismissal on outside touch or back button
        builder.show();
    }

    private void performLogout() {
        // Perform logout logic
        // Clear session data or perform any necessary cleanup

        // Navigate to the login screen
        Intent intent = new Intent(getActivity(), FirstPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        getActivity().finish(); // Optional: finish the current activity if desired
    }
}



