package com.example.eventmanagement;



import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private FirebaseAuth mAuth; // Declare mAuth variable

    private EditText emailEditText;
    private Button resetButton;
    private ProgressBar progressBar;
    String strEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance(); // Initialize mAuth

        emailEditText = findViewById(R.id.recoverEmail);
        resetButton = findViewById(R.id.btnreset);
        progressBar = findViewById(R.id.forgotpw);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strEmail=emailEditText.getText().toString();
                if(!TextUtils.isEmpty(strEmail)){
                    ResetPassword();
                }else{
                    emailEditText.setError("Email field cant be empty");
                }
            }
        });


    }

    private void ResetPassword() {

        progressBar.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.INVISIBLE);

        mAuth.sendPasswordResetEmail(strEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ForgotPassword.this, "Reset Password link has been sent to your mail", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(ForgotPassword.this,FirstPage.class);
                startActivity(intent);
                finish();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ForgotPassword.this, "Error:-", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.VISIBLE);
                        resetButton.setVisibility(View.INVISIBLE);


                    }
                });
        
        
    }

    // ... rest of your code ...
}


