package com.example.simplegym;

import java.util.List;
import android.widget.TextView;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();

    private Training currentTraining = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // создание экрана

        EdgeToEdge.enable(this); // наложение системного времени и полей

        setContentView(R.layout.activity_training_activity); // сопряжение java с xml
        String date = getIntent().getStringExtra("date");

        TextView dateTitle = findViewById(R.id.dateTitle);
        dateTitle.setText(formatDateNicely(date));

        AppDatabase db = AppDatabase.getDatabase(this);
        TrainingDAO trainingDao = db.trainingDao();

        EditText trainingText = findViewById(R.id.trainingText); // поиск в xml trainingtext

        Button saveButton = findViewById(R.id.saveButton); // поиск в xml кнопки

        databaseExecutor.execute(() -> {
            List<Training> existing = trainingDao.getTrainingsByDate(date);
            runOnUiThread(() -> {
                if (!existing.isEmpty()) {
                    currentTraining = existing.get(0);        // берём заметку за этот день
                    trainingText.setText(currentTraining.notes); // показываем её текст в поле
                }
            });
        });

        saveButton.setOnClickListener(v -> {
            String notesText = trainingText.getText().toString();

            databaseExecutor.execute(() -> {
                if (currentTraining == null) {
                    // заметки ещё не было — создаём новую
                    Training training = new Training(date, notesText);
                    trainingDao.insert(training);
                    // перечитываем, чтобы получить объект с настоящим id из базы
                    List<Training> saved = trainingDao.getTrainingsByDate(date);
                    if (!saved.isEmpty()) {
                        currentTraining = saved.get(0);
                    }
                } else {
                    // заметка уже была — обновляем её текст
                    currentTraining.notes = notesText;
                    trainingDao.update(currentTraining);
                }
            });

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