package com.example.simplegym;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TrainingActivity extends AppCompatActivity {
    ArrayList<Training> trainings = new ArrayList<>();
    Training currentTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // создание экрана

        EdgeToEdge.enable(this); // наложение системного времени и полей

        setContentView(R.layout.activity_training_activity); // сопряжение java с xml

        String date = getIntent().getStringExtra("date");
        Toast.makeText(
                this,
                date,
                Toast.LENGTH_SHORT
        ).show();

        EditText trainingText = findViewById(R.id.trainingText); // поиск в xml trainingtext

        Button saveButton = findViewById(R.id.saveButton); // поиск в xml кнопки

        saveButton.setOnClickListener(v -> { // при клике происходит вывод текста снизу
            Training training = new Training( "09.07.2026",
                    trainingText.getText().toString());

            trainings.add(training);
            Training lastTraining = trainings.get(trainings.size() - 1);

            Toast.makeText(
                    this,
                    lastTraining.notes,
                    Toast.LENGTH_SHORT
            ).show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}