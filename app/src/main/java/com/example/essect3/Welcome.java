package com.example.essect3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button getStartedButton = findViewById(R.id.btnProf);
        Button getStartedButton2 = findViewById(R.id.btnEtudiant);

        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention (Intent) pour démarrer l'activité login
                Intent intent = new Intent(Welcome.this, login.class);
                startActivity(intent);
            }
        });

        getStartedButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer une intention (Intent) pour démarrer l'activité loginetu
                Intent intent = new Intent(Welcome.this, loginetu.class);
                startActivity(intent);
            }
        });
    }
}
