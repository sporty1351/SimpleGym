package com.example.simplegym;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import java.util.List;

@Dao
public interface TrainingDAO {

    @Insert
    void insert(Training training);

    @Query("SELECT * FROM trainings ORDER BY id DESC")
    List<Training> getAllTrainings();

    @Query("SELECT * FROM trainings WHERE date = :date")
    List<Training> getTrainingsByDate(String date);

    @Delete
    void delete(Training training);
}