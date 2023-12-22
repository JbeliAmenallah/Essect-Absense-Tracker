package com.example.essect3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Home_prof extends AppCompatActivity {

    private FirebaseAuthManager firebaseAuthManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_prof);

        // Initialize Firebase Authentication Manager
        firebaseAuthManager = FirebaseAuthManager.getInstance();

        // Set up click listener for LinearLayout to navigate to dashboard_add
        LinearLayout addLinearLayout = findViewById(R.id.layoutDashboardAdd);
        addLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToDashboardAdd();

            }

        });
        LinearLayout abs = findViewById(R.id.layoutAbsence);
        abs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToAbs();

            }

        });

        // Set up click listener for Logout Button
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    // Method to navigate to DashboardAddActivity
    private void navigateToDashboardAdd() {
        Intent intent = new Intent(Home_prof.this, dashboard_add.class);
        startActivity(intent);
    }
    private void navigateToAbs() {
        Intent intent = new Intent(Home_prof.this, absence.class);
        startActivity(intent);
    }


    // Method to handle logout
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
