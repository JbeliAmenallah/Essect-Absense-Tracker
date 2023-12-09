package com.example.essect3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class editabsense extends AppCompatActivity {

    private TextView textCounter;
    private Button btnPlus, btnMinus;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editabsense);

        textCounter = findViewById(R.id.textCounter);
        btnPlus = findViewById(R.id.btnPlus);
        btnMinus = findViewById(R.id.btnMinus);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementCounter();
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrementCounter();
            }
        });

        // Set initial counter value
        updateCounter();
    }

    private void incrementCounter() {
        counter++;
        updateCounter();
    }

    private void decrementCounter() {
        if (counter > 0) {
            counter--;
            updateCounter();
        }
    }

    private void updateCounter() {
        textCounter.setText(String.valueOf(counter));
    }
}
