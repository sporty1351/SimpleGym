package com.example.simplegym;

import java.util.Calendar;
import android.widget.TextView;
import android.widget.GridLayout;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;


public class MainActivity extends AppCompatActivity {

    private TrainingRepository repository;
    private GestureDetector gestureDetector;
    private final Calendar displayedMonth = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // создание экрана

        EdgeToEdge.enable(this); // наложение системного времени и полей

        setContentView(R.layout.activity_main); // сопряжение java с xml

        repository = new TrainingRepository(this);
        TextView prevMonth = findViewById(R.id.prevMonth);
        TextView nextMonth = findViewById(R.id.nextMonth);

        prevMonth.setOnClickListener(v -> {
            displayedMonth.add(Calendar.MONTH, -1);
            loadCalendar();
        });

        nextMonth.setOnClickListener(v -> {
            displayedMonth.add(Calendar.MONTH, 1);
            loadCalendar();
        });

        gestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        float diffX = e2.getX() - e1.getX();
                        if (Math.abs(diffX) > 100 && Math.abs(velocityX) > 100) {
                            if (diffX < 0) {
                                displayedMonth.add(Calendar.MONTH, 1);
                            } else {
                                displayedMonth.add(Calendar.MONTH, -1);
                            }
                            loadCalendar();
                            return true;
                        }
                        return false;
                    }
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
    @Override
    protected void onResume() {
        super.onResume();
        loadCalendar();
    }
    private void loadCalendar() {
        GridLayout calendarGrid = findViewById(R.id.calendarGrid);
        TextView monthTitle = findViewById(R.id.monthTitle);

        monthTitle.setText(formatMonth(displayedMonth));

        repository.getAllDates(filledDates ->
                CalendarHelper.createCalendar(this, calendarGrid, filledDates, displayedMonth));
    }
    private String formatMonth(Calendar calendar) {
        String[] months = {
                "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
                "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
        };
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        return months[month] + " " + year;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

}