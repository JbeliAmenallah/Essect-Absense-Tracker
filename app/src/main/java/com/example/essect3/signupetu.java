package com.example.essect3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signupetu extends AppCompatActivity {

    private FirebaseAuth mAuth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Etudiant");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupetu);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        Button signup = findViewById(R.id.button3);
        signup.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToDB();
            }
        }));
    }

    private void writeToDB() {
        // Capture other user details
        String value1 = ((EditText) findViewById(R.id.nom)).getText().toString();
        String value2 = ((EditText) findViewById(R.id.pwd)).getText().toString();
        String value3 = ((EditText) findViewById(R.id.grp)).getText().toString();


        // Capture user's email
        String userEmail = ((EditText) findViewById(R.id.email)).getText().toString();

        // Check if all fields are filled
        if (!value1.isEmpty() && !value2.isEmpty() && !userEmail.isEmpty() && !value3.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(userEmail, value2)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // User created successfully, proceed to write to the database
                                Etudiant etu = new Etudiant(value1, userEmail, value2, value3);

                                // Insert data into Firebase Realtime Database
                                String key = databaseReference.push().getKey();
                                if (key != null) {
                                    databaseReference.child(key).setValue(etu);
                                    sendVerificationEmail(); // Send verification email
                                    navigateToHome();
                                } else {
                                    Toast.makeText(signupetu.this, "Failed to insert data.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // Registration failed
                                Toast.makeText(signupetu.this, "Registration failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(signupetu.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Email sent successfully
                                Toast.makeText(signupetu.this, "Verification email sent!", Toast.LENGTH_SHORT).show();
                                // Navigate to the activity or fragment that displays the verification message
                                // showVerificationLayout(); // Uncomment this line if you have a verification layout
                            } else {
                                // Email sending failed
                                Toast.makeText(signupetu.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void navigateToHome() {
        Intent homeIntent = new Intent(signupetu.this, login.class);
        startActivity(homeIntent);
        finish(); // Finish the current activity if needed
    }
}

