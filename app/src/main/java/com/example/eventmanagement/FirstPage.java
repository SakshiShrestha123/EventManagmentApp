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

public class FirstPage extends AppCompatActivity {
    Button signup, login;
    TextView fpw;
    EditText email, password;
    FirebaseAuth auth;

    // Static admin credentials
    private static final String ADMIN_EMAIL = "admin@gmail.com";
    private static final String ADMIN_PASSWORD = "admin123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.btnSignup);
        fpw = findViewById(R.id.forgotpw);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FirstPage.this, SignupPage.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (valid()) {
                    String enteredEmail = email.getText().toString();
                    String enteredPassword = password.getText().toString();

                    // Check if the entered credentials match the admin credentials
                    if (enteredEmail.equals(ADMIN_EMAIL) && enteredPassword.equals(ADMIN_PASSWORD)) {
                        Toast.makeText(FirstPage.this, "Admin login successful", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(FirstPage.this, AdminActivity.class);
                        startActivity(i);
                    } else {
                        // Perform user login
                        auth.signInWithEmailAndPassword(enteredEmail, enteredPassword)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(FirstPage.this, "User login successful", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(FirstPage.this, UserActivity.class);
                                        startActivity(i);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(FirstPage.this, "User login failure", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
            }

            private boolean valid() {
                boolean validation = true;

                if (TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("Email cannot be empty");
                    validation = false;
                }

                if (TextUtils.isEmpty(password.getText().toString())) {
                    password.setError("Password cannot be empty");
                    validation = false;
                }

                return validation;
            }
        });

        fpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FirstPage.this, ForgotPassword.class);
                startActivity(i);
            }
        });
    }
}




