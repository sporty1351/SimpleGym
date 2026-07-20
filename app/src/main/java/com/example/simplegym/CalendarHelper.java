package com.example.simplegym;

import android.view.View;
import java.util.Locale;
import java.util.Calendar;
import android.content.Context;
import android.view.Gravity;
import androidx.appcompat.widget.AppCompatButton;
import android.widget.GridLayout;
import android.widget.TextView;
import android.content.Intent;
import androidx.core.content.ContextCompat;
import java.util.List;
public class CalendarHelper {

    public static void createCalendar(Context context, GridLayout calendarGrid, List<String> filledDates, Calendar displayedMonth) {

        String[] weekDays = {
                "Пн",
                "Вт",
                "Ср",
                "Чт",
                "Пт",
                "Сб",
                "Вс"
        };

        calendarGrid.removeAllViews();
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
        Calendar calendar = (Calendar) displayedMonth.clone();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int emptyCells = (firstDayOfWeek + 5) % 7;

        for (int i = 0; i < emptyCells; i++) {
            View emptyView = new View(context);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 130;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            emptyView.setLayoutParams(params);
            calendarGrid.addView(emptyView);
        }

        // кнопки с датами
        for (int day = 1; day <= daysInMonth; day++) {
            int currentDay = day;

            AppCompatButton button = new AppCompatButton(context);
            button.setText(String.valueOf(day));
            button.setTextColor(ContextCompat.getColor(context, R.color.text_light));
            button.setBackgroundResource(R.drawable.day_button);
            String dayDate = String.format(Locale.US, "%02d.%02d.%04d", day, currentMonth, currentYear);
            if (filledDates.contains(dayDate)) {
                button.setBackgroundResource(R.drawable.day_button_accent);
                button.setBackgroundTintList(ContextCompat.getColorStateList(context, R.color.accent_orange));
                button.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
            button.setPadding(0, 0, 0, 0);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 130;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(6, 6, 6, 6);   // расстояние между плитками
            button.setLayoutParams(params);

            button.setOnClickListener(v -> {
                String fullDate = String.format(
                        Locale.US,
                        "%02d.%02d.%04d",
                        currentDay,
                        currentMonth,
                        currentYear
                );
                Intent intent = new Intent(context, TrainingActivity.class);
                intent.putExtra("date", fullDate);
                context.startActivity(intent);
            });

            calendarGrid.addView(button);
        }
    }
}