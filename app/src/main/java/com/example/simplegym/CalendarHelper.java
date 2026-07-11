package com.example.simplegym;

import android.content.Context;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.content.Intent;
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

            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();

            params.width = 0;
            params.height = 120;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

            textView.setLayoutParams(params);

            calendarGrid.addView(textView);
        }


        // Создаем кнопки с датами
        for (int day = 1; day <= 31; day++) {

            int currentDay = day;

            Button button = new Button(context);

            button.setText(String.valueOf(day));

            button.setPadding(0, 0, 0, 0);


            GridLayout.LayoutParams params =
                    new GridLayout.LayoutParams();

            params.width = 0;
            params.height = 120;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);

            button.setLayoutParams(params);


            button.setOnClickListener(v -> {

                Intent intent = new Intent(context, TrainingActivity.class);

                intent.putExtra("date", String.valueOf(currentDay));

                context.startActivity(intent);

            });


            calendarGrid.addView(button);
        }
    }
}