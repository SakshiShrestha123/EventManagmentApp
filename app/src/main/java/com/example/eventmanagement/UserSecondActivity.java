package com.example.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.eventmanagement.BookEventActivity;
import com.example.eventmanagement.CardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserSecondActivity extends AppCompatActivity {

    CardView cardview,cardview1,cardview2,cardview3,cardview4,cardview5;

    private String selectHotel;

    // private DatabaseReference userRef;
    private DatabaseReference eventsRef;
    private FirebaseAuth firebaseAuth;

    List<Venue> venueList = new ArrayList<>();
    CustomVenueAdaptor customVenueAdaptor;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_second);
        firebaseAuth = FirebaseAuth.getInstance();

//        cardview=findViewById(R.id.cardview);
//        cardview1=findViewById(R.id.cardview1);
//        cardview2=findViewById(R.id.cardview2);
//        cardview3=findViewById(R.id.cardview3);
//        cardview4=findViewById(R.id.cardview4);
//        cardview5=findViewById(R.id.cardview5);

        // userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        eventsRef = FirebaseDatabase.getInstance().getReference().child("Events");


        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectEvent = getIntent().getStringExtra("selected_event");
                selectHotel = "Hotel Pabera";
                saveSelectedEventAndHotelToDatabase();


                Intent i = new Intent(UserSecondActivity.this, BookEventActivity.class);
                i.putExtra("selected_hotel", selectHotel);
                i.putExtra("selected_event", selectEvent);
                startActivity(i);
            }
        });

        cardview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectEvent = getIntent().getStringExtra("selected_event");
                selectHotel = "Hotel Park Village";
                saveSelectedEventAndHotelToDatabase();
                Intent i = new Intent(UserSecondActivity.this, BookEventActivity.class);
                i.putExtra("selected_hotel",selectHotel);
                i.putExtra("selected_event", selectEvent);
                startActivity(i);

            }
        });

        cardview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectEvent = getIntent().getStringExtra("selected_event");
                selectHotel = "Yak and Yeti Hotel";
                saveSelectedEventAndHotelToDatabase();
                Intent i = new Intent(UserSecondActivity.this, BookEventActivity.class);
                i.putExtra("selected_hotel",selectHotel);
                i.putExtra("selected_event", selectEvent);
                startActivity(i);

            }
        });

        cardview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectEvent = getIntent().getStringExtra("selected_event");
                selectHotel = "Hotel Timila";
                saveSelectedEventAndHotelToDatabase();
                Intent i = new Intent(UserSecondActivity.this, BookEventActivity.class);
                i.putExtra("selected_hotel",selectHotel);
                i.putExtra("selected_event", selectEvent);

                startActivity(i);
            }
        });

        cardview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectEvent = getIntent().getStringExtra("selected_event");
                selectHotel = "Hotel Everest";
                saveSelectedEventAndHotelToDatabase();
                Intent i = new Intent(UserSecondActivity.this, BookEventActivity.class);
                i.putExtra("selected_hotel",selectHotel);
                i.putExtra("selected_event", selectEvent);
                startActivity(i);
            }
        });

        cardview5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectEvent = getIntent().getStringExtra("selected_event");
                selectHotel = "Hotel Marriot";
                saveSelectedEventAndHotelToDatabase();
                Intent i = new Intent(UserSecondActivity.this, BookEventActivity.class);
                i.putExtra("selected_hotel",selectHotel);
                i.putExtra("selected_event", selectEvent);
                startActivity(i);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveSelectedEventAndHotelToDatabase() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();



            // Create a User object with the selected event
            // User user = new User();
            Event event =new Event();
            //  user.setSelectedHotel(selectHotel);
            String selectEvent = null;
            event.setSelectedEvent(selectEvent);
            event.setSelectedHotel(selectHotel);
            // Set other user properties...



            // Save the user data to the database under the user's ID
            // eventsRef.child(userId).child("selectedHotel").setValue(selectHotel); //now
//            eventsRef.child(userId).setValue(event);

        }
    }
    private void viewDataFromDatabase(){
        eventsRef = FirebaseDatabase.getInstance().getReference("addVenue");


        eventsRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                venueList.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    Venue venue = snapshot1.getValue(Venue.class);
                    Log.e("reached", "here");
                    if(venue != null) {
                        Log.e("venue list", venue.getName());
                        venueList.add(venue);
                    }

                }
                customVenueAdaptor = new CustomVenueAdaptor(UserSecondActivity.this, venueList);
                listView.setAdapter(customVenueAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //
            }

        });
    }

}