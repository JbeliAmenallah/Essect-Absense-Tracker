package com.example.essect3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeEtu extends AppCompatActivity {
    private FirebaseAuthManager firebaseAuthManager;

    private int presentCount = 0;
    private int absentCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_etu);
        firebaseAuthManager = FirebaseAuthManager.getInstance();

        final EditText editTextEmail = findViewById(R.id.editTextEmail);
        Button btnSubmit = findViewById(R.id.btnSubmit);
        final TextView countTextView = findViewById(R.id.countTextView);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentEmail = editTextEmail.getText().toString();
                String presentOrAbsent = studentEmail.contains("present") ? "Present" : "Absent";

                insertDataIntoCsv(studentEmail, "YourSubject", presentOrAbsent, getCurrentDate());
                updateCount(presentOrAbsent);
                displayCounts(countTextView);
            }
        });

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void insertDataIntoCsv(String studentName, String subject, String presentOrAbsent, String currentDate) {
        try {
            File file = new File(getExternalFilesDir(null), "absence.csv");
            FileWriter writer = new FileWriter(file, true);

            // Append data to the CSV file
            writer.append(studentName).append(",").append(subject).append(",").append(presentOrAbsent).append(",").append(currentDate).append("\n");

            // Close the writer
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void updateCount(String presentOrAbsent) {
        if ("Present".equals(presentOrAbsent)) {
            presentCount++;
        } else {
            absentCount++;
        }
    }

    private void displayCounts(TextView countTextView) {
        String countText = "Present: " + presentCount + ", Absent: " + absentCount;
        countTextView.setText(countText);
    }

    private void logout() {
        // Sign the user out of Firebase
        firebaseAuthManager.signOut();

        // Example: Navigating back to the introduction page (intro)
        Intent intent = new Intent(this, intro.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Optional: finish the current activity
    }
}
