package com.example.essect3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class fixabsense extends AppCompatActivity {

    private TextInputLayout textInputEmail, textInputSubject, textInputnbabs;
    private EditText editTextEmail, editTextSubject, editTextnbabs;
    private Button setBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixabsense);

        textInputEmail = findViewById(R.id.textInputEmail);
        textInputSubject = findViewById(R.id.textInputSubject);
        textInputnbabs = findViewById(R.id.textInputnbabs);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextSubject = findViewById(R.id.editTextSubject);
        editTextnbabs = findViewById(R.id.editTextnbabs);

        setBtn = findViewById(R.id.setbtn);

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get data from input fields
                String studentName = editTextEmail.getText().toString();
                String subject = editTextSubject.getText().toString();
                String presentOrAbsent = editTextnbabs.getText().toString();

                // Get current date
                String currentDate = getCurrentDate();

                // Check if CSV file exists
                if (!isFileExist()) {
                    // Create CSV file
                    createCsvFile();
                }

                // Insert data into CSV file with current date
                insertDataIntoCsv(studentName, subject, presentOrAbsent, currentDate);

                // Optionally, show a toast message to indicate success
                Toast.makeText(fixabsense.this, "Data inserted into CSV file", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isFileExist() {
        File file = new File(getExternalFilesDir(null), "absense.csv");
        return file.exists();
    }

    private void createCsvFile() {
        try {
            File file = new File(getExternalFilesDir(null), "absense.csv");
            FileWriter writer = new FileWriter(file);

            // Write header
            writer.append("NameStudent,SubjectName,State,Date\n");

            // Close the writer
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void insertDataIntoCsv(String studentName, String subject, String presentOrAbsent, String currentDate) {
        try {
            File file = new File(getExternalFilesDir(null), "absense.csv");
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
}
