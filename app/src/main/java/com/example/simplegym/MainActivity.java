package com.example.simplegym;

import android.widget.Toast;
import android.widget.GridLayout;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {

    private TrainingRepository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // создание экрана

        EdgeToEdge.enable(this); // наложение системного времени и полей

        setContentView(R.layout.activity_main); // сопряжение java с xml

        repository = new TrainingRepository(this);

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
    @Override
    protected void onResume() {
        super.onResume();
        loadCalendar();
    }
    private void loadCalendar() {
        GridLayout calendarGrid = findViewById(R.id.calendarGrid);
        repository.getAllDates(filledDates ->
                CalendarHelper.createCalendar(this, calendarGrid, filledDates));
    }

}