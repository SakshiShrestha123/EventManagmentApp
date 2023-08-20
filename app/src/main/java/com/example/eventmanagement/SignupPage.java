package com.example.eventmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;



import java.util.HashMap;
import java.util.Map;

public class SignupPage extends AppCompatActivity {

    TextView exacc;
    EditText name,address,email,phone,pw;


    Button signup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        exacc=findViewById(R.id.textViewLogin);
        name=findViewById(R.id.editTextName);
        address=findViewById(R.id.editaddress);
        email=findViewById(R.id.editTextEmail);
        phone=findViewById(R.id.editphoneno);
        pw=findViewById(R.id.editTextPassword);

        signup=findViewById(R.id.buttonSignup);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valid()){
                    //user registration
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),pw.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user=firebaseAuth.getCurrentUser();
                            Toast.makeText(SignupPage.this,"User Registration Successful",Toast.LENGTH_SHORT).show();
                            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("Users");
                            DatabaseReference userRef = usersRef.child(user.getUid());

                            userRef.child("Username").setValue(name.getText().toString());
                            userRef.child("Address").setValue(address.getText().toString());
                            userRef.child("UserEmail").setValue(email.getText().toString());
                            userRef.child("PhoneNumber").setValue(phone.getText().toString());
                            userRef.child("Password").setValue(pw.getText().toString());

                            //specify if the user is admin



                            Intent i = new Intent(SignupPage.this,FirstPage.class);
                            startActivity(i);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupPage.this,"User Registration Unsuccessful",Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            private boolean valid() {
                boolean validation=true;
                if(TextUtils.isEmpty(name.getText().toString())){
                    name.setError("Name cannot be empty");
                    validation=false;
                }
                if(TextUtils.isEmpty(address.getText().toString())){
                    address.setError("Address cannot be empty");
                    validation=false;
                }
                if(TextUtils.isEmpty(email.getText().toString())){
                    email.setError("Email cannot be empty");
                    validation=false;
                }
                if(TextUtils.isEmpty(phone.getText().toString())){
                    phone.setError("Phone number cannot be empty");
                    validation=false;
                }
                if(TextUtils.isEmpty(pw.getText().toString())){
                    pw.setError("Password cannot be empty");
                    validation=false;
                }
                return validation;
            }
        });

        exacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignupPage.this,FirstPage.class);
                startActivity(i);
            }
        });
    }
}