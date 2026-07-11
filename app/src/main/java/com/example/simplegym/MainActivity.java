package com.example.simplegym;

import android.view.Gravity;
import android.widget.TextView;
import android.widget.GridLayout;
import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    ArrayList<Training> trainings = new ArrayList<>();
    Training currentTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // создание экрана

        EdgeToEdge.enable(this); // наложение системного времени и полей

        setContentView(R.layout.activity_main); // сопряжение java с xml


        GridLayout calendarGrid = findViewById(R.id.calendarGrid); //Создаем календарь
        CalendarHelper.createCalendar(this, calendarGrid);

        Button openTrainingButton = findViewById(R.id.openTrainingButton);
        openTrainingButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TrainingActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(
                    systemBars.left,
                    systemBars.top,
                    systemBars.right,
                    systemBars.bottom);
            return insets;
        });
    }
}