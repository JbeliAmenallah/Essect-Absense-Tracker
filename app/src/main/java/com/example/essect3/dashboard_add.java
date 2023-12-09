package com.example.essect3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class dashboard_add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_add);

        TextView userAssTitle = findViewById(R.id.userasstitle);

        userAssTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click event, navigate to AddEtudiantActivity
                Intent intent = new Intent(dashboard_add.this, addetu.class);
                startActivity(intent);
            }
        });
    }
}