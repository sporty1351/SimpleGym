package com.example.simplegym;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "trainings")
public class Training {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    public String date;

    public String notes;

    // Конструктор — Room будет использовать именно его для создания объектов
    public Training(@NonNull String date, String notes) {
        this.date = date;
        this.notes = notes;
    }
}