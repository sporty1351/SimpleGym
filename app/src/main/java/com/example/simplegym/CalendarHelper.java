package com.example.simplegym;

import java.util.Locale;
import java.util.Calendar;
import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.content.Intent;
import androidx.core.content.ContextCompat;
public class CalendarHelper {

    public static void createCalendar(Context context, GridLayout calendarGrid) {

        String[] weekDays = {
                "Пн",
                "Вт",
                "Ср",
                "Чт",
                "Пт",
                "Сб",
                "Вс"
        };


        // Создаем названия дней недели
        for (String weekDay : weekDays) {
            TextView textView = new TextView(context);
            textView.setText(weekDay);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(ContextCompat.getColor(context, R.color.text_muted));
            textView.setTextSize(12);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 90;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            textView.setLayoutParams(params);

            calendarGrid.addView(textView);
        }

        // кнопки с датами
        for (int day = 1; day <= 31; day++) {
            int currentDay = day;

            Button button = new Button(context);
            button.setText(String.valueOf(day));
            button.setTextColor(ContextCompat.getColor(context, R.color.text_light));
            button.setBackgroundResource(R.drawable.day_button);
            button.setPadding(0, 0, 0, 0);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 130;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(6, 6, 6, 6);   // расстояние между плитками
            button.setLayoutParams(params);

            button.setOnClickListener(v -> {
                Calendar calendar = Calendar.getInstance();
                String fullDate = String.format(
                        Locale.US,
                        "%02d.%02d.%04d",
                        currentDay,
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.YEAR)
                );
                Intent intent = new Intent(context, TrainingActivity.class);
                intent.putExtra("date", fullDate);
                context.startActivity(intent);
            });

            calendarGrid.addView(button);
        }
    }
}