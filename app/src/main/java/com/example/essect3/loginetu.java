package com.example.essect3;

import static com.example.essect3.R.id.textView11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class loginetu extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginetu);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser=mAuth.getCurrentUser();

        if(currentUser != null) {
            startActivity(new Intent(loginetu.this, Home_prof.class));
            finish(); // Close the current activity
        }
        TextView createAccountTextView = findViewById(textView11);
        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent signUpIntent = new Intent(loginetu.this, signupetu.class);
                startActivity(signUpIntent);}
        }


        );

        Button connectButton = findViewById(R.id.connect);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailEditText = findViewById(R.id.email);
                EditText passwordEditText = findViewById(R.id.pwd);

                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(loginetu.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(loginetu.this, Home_prof.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(loginetu.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        Log.e("Authentication", "Failed: " + task.getException());
                                        task.getException().printStackTrace();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(loginetu.this, "Please enter email and password", Toast.LENGTH_SHORT).show();
                }

                emailEditText.getText().clear();
                passwordEditText.getText().clear();
            }



        });
    }
}