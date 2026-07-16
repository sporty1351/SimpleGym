package com.example.simplegym;

import android.widget.TextView;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class TrainingActivity extends AppCompatActivity {
    // Менеджер фоновых задач — один поток, выполняющий задачи по очереди
    private TrainingRepository repository;
    private Training currentTraining = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // создание экрана

        EdgeToEdge.enable(this); // наложение системного времени и полей

        setContentView(R.layout.activity_training_activity); // сопряжение java с xml
        repository = new TrainingRepository(this);
        String date = getIntent().getStringExtra("date");

        TextView dateTitle = findViewById(R.id.dateTitle);
        dateTitle.setText(formatDateNicely(date));


        EditText trainingText = findViewById(R.id.trainingText); // поиск в xml trainingtext

        Button saveButton = findViewById(R.id.saveButton); // поиск в xml кнопки

        repository.getTrainingByDate(date, training -> {
            if (training != null) {
                currentTraining = training;
                trainingText.setText(training.notes);
            }
        });

        saveButton.setOnClickListener(v -> {
            String notesText = trainingText.getText().toString().trim();

            if (notesText.isEmpty()) {
                if (currentTraining != null) {
                    repository.delete(currentTraining, result -> currentTraining = null);
                    Toast.makeText(this, "Запись удалена", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            if (currentTraining == null) {
                currentTraining = new Training(date, notesText);
            } else {
                currentTraining.notes = notesText;
            }

            repository.save(currentTraining, date, saved -> currentTraining = saved);
            Toast.makeText(this, "Сохранено!", Toast.LENGTH_SHORT).show();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private String formatDateNicely(String rawDate) {
        String[] months = {
                "января", "февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря"
        };
        try {
            String[] parts = rawDate.split("\\.");
            int day = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            String year = parts[2];
            return day + " " + months[month - 1] + " " + year + " г.";
        } catch (Exception e) {
            return rawDate;
        }
    }
}