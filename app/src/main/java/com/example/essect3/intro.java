package com.example.essect3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Button getStartedButton = findViewById(R.id.button1);

    }
    public void onButtonClick(View view) {
        // Créer une intention (Intent) pour démarrer l'activité login
        Intent intent = new Intent(this, Welcome.class);
        startActivity(intent);
    }
}