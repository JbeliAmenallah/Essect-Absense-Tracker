package com.example.essect3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class addetu extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addetu);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Etudiant");

        Button add = findViewById(R.id.btnAddStudent);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToDB();
            }
        });
    }

    private void writeToDB() {
        // Capture other user details
        String value1 = ((EditText) findViewById(R.id.nom)).getText().toString();
        String value3 = ((EditText) findViewById(R.id.grp)).getText().toString();
        String userEmail = ((EditText) findViewById(R.id.email)).getText().toString();
        String defaultpwd = "12345678";

        // Check if all fields are filled
        if (!value1.isEmpty() && !value3.isEmpty() && !userEmail.isEmpty()) {
            mAuth.createUserWithEmailAndPassword(userEmail, defaultpwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // User created successfully, proceed to write to the database
                        String key = databaseReference.push().getKey();
                        if (key != null) {
                            Etudiant etu = new Etudiant(userEmail, value1, defaultpwd, value3);
                            databaseReference.child(key).setValue(etu);
                            sendVerificationEmail(); // Send verification email
                            showAddStudentPopup(); // Show the popup here
                            writeStudentDetailsToCSV(etu); // Write student details to CSV
                        } else {
                            Toast.makeText(addetu.this, "Failed to insert data.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Registration failed
                        Toast.makeText(addetu.this, "Registration failed. " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(addetu.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendVerificationEmail() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Email sent successfully
                        Toast.makeText(addetu.this, "Verification email sent!", Toast.LENGTH_SHORT).show();
                        // Navigate to the activity or fragment that displays the verification message
                        // showVerificationLayout(); // Uncomment this line if you have a verification layout
                    } else {
                        // Email sending failed
                        Toast.makeText(addetu.this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showAddStudentPopup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Student");
        builder.setMessage("Student added with default password: 12345678 Please tell him to change the password ASAP!");

        // Add a button to dismiss the dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                navigateToHome();
            }
        });

        // Create and show the AlertDialog
        builder.create().show();
    }

    private void navigateToHome() {
        Intent homeIntent = new Intent(addetu.this, Home_prof.class);
        startActivity(homeIntent);
        finish(); // Finish the current activity if needed
    }

    private void writeStudentDetailsToCSV(Etudiant etudiant) {
        // Get a reference to the CSV file in Firebase Storage
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference csvFileRef = storageRef.child("gs://essectapp-4c894.appspot.com/etudiant.csv");

        // Read the existing CSV data
        csvFileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Assuming the CSV file is small, read the content into a string
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }

                    // Append the new student details
                    stringBuilder.append(etudiant.getEmail()).append(",")
                            .append(etudiant.getNom()).append(",")
                            .append(etudiant.getGroup()).append("\n");

                    // Write the updated content back to the CSV file
                    byte[] data = stringBuilder.toString().getBytes();
                    csvFileRef.putBytes(data)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    showToast("Student details added to CSV");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    showToast("Failed to update CSV file");
                                }
                            });

                } catch (IOException e) {
                    e.printStackTrace();
                    showToast("Error reading CSV file");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                showToast("Failed to read CSV file");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}

