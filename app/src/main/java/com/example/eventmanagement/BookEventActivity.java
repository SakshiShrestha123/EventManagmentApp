package com.example.eventmanagement;


import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class BookEventActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText eventNameEditText, numberOfGuestsEditText;
    EditText entryDateEditText, exitDateEditText;

    Button next;
    ImageView entryDateImage, exitDateImage;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    FirebaseAuth auth;

    private FirebaseAuth firebaseAuth; //later
    // private DatabaseReference eventsRef; //l

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_event);


        String selectEvent = null;
        if (getIntent().hasExtra("selected_event")) {
            selectEvent = getIntent().getStringExtra("selected_event");
        }


        firebaseAuth = FirebaseAuth.getInstance(); //later




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null)

        {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //initialize firebase database


        databaseReference = FirebaseDatabase.getInstance().getReference("Events");
        // eventsRef = FirebaseDatabase.getInstance().getReference().child("Events");

        //find view by their ids
        eventNameEditText = findViewById(R.id.eventtext);
        numberOfGuestsEditText = findViewById(R.id.eventtext1);
        entryDateEditText = findViewById(R.id.entryDateEditText);
        exitDateEditText = findViewById(R.id.exitDateEditText);
        next = findViewById(R.id.nextbutton);
        entryDateImage = findViewById(R.id.imgentry);
        exitDateImage = findViewById(R.id.imgexit);
        auth=FirebaseAuth.getInstance();


        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        entryDateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(entryDateEditText);
            }
        });

        exitDateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(exitDateEditText);
            }
        });




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveEventData();
                Intent i = new Intent(BookEventActivity.this,UserThirdActivity.class);
                startActivity(i);

            }
        });


//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveEventData();
//            }
//        });
    }

    private void showDatePickerDialog(final EditText editText) {
        final Calendar entryCalendar = Calendar.getInstance();
        entryCalendar.setTime(calendar.getTime());
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {



                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(year, month, dayOfMonth);

                        Calendar entryCalendar = Calendar.getInstance();
                        entryCalendar.setTime(calendar.getTime());

                        if (selectedCalendar.before(entryCalendar) || selectedCalendar.equals(entryCalendar)) {
                            // Selected date is before or same as the entry date
                            Toast.makeText(BookEventActivity.this, "Invalid exit date", Toast.LENGTH_SHORT).show();
                        } else {
                            calendar.set(year, month, dayOfMonth);
                            editText.setText(dateFormat.format(calendar.getTime()));
                        }
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.getDatePicker().setMinDate(entryCalendar.getTimeInMillis());
        datePickerDialog.show();
    }


    private void saveEventData() {
        String eventName = eventNameEditText.getText().toString().trim();
        String numberOfGuests = numberOfGuestsEditText.getText().toString().trim();
        String entryDate = entryDateEditText.getText().toString().trim();
        String exitDate = exitDateEditText.getText().toString().trim();

        if (eventName.isEmpty() || numberOfGuests.isEmpty() || entryDate.isEmpty() || exitDate.isEmpty()) {
            Toast.makeText(BookEventActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // String eventId = databaseReference.push().getKey();

            String selectedEvent= getIntent().getStringExtra("selected_event");
            String selectedHotel= getIntent().getStringExtra("selected_hotel");
            String selectedServices=getIntent().getStringExtra("selected_services");

            //TODO: actual data
            Event event = new Event(selectedEvent, selectedServices, selectedHotel, eventName, numberOfGuests, entryDate, exitDate);    // before it was this

            databaseReference.child(userId).setValue(event);
            //  User user = new User(eventId, eventName, numberOfGuests, entryDate, exitDate); //later i write this

            //  if (eventId != null) {
            //  databaseReference.child(userId).child(eventId).setValue(event);

            // Toast.makeText(BookEventActivity.this, "Data inserted successfully", Toast.LENGTH_SHORT).show();
            // }
        }

        eventNameEditText.setText("");
        numberOfGuestsEditText.setText("");
        entryDateEditText.setText("");
        exitDateEditText.setText("");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}






