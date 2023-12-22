package com.example.essect3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class absence extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absence);
        TextView userAssTitle = findViewById(R.id.userasstitle);

        userAssTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event, navigate to AddEtudiantActivity
                Intent intent = new Intent(absence.this, fixabsense.class);
                startActivity(intent);
            }
        });
    }
}