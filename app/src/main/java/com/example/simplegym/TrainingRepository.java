package com.example.simplegym;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TrainingRepository {

    private final TrainingDAO trainingDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public TrainingRepository(Context context) {
        AppDatabase db = AppDatabase.getDatabase(context);
        this.trainingDao = db.trainingDao();
    }

    public void getAllDates(Callback<List<String>> callback) {
        executor.execute(() -> {
            List<String> dates = trainingDao.getAllDates();
            mainHandler.post(() -> callback.onResult(dates));
        });
    }

    public void getTrainingByDate(String date, Callback<Training> callback) {
        executor.execute(() -> {
            List<Training> list = trainingDao.getTrainingsByDate(date);
            Training result = list.isEmpty() ? null : list.get(0);
            mainHandler.post(() -> callback.onResult(result));
        });
    }

    public void save(Training training, String date, Callback<Training> callback) {
        executor.execute(() -> {
            if (training.id == 0) {
                trainingDao.insert(training);
                List<Training> saved = trainingDao.getTrainingsByDate(date);
                Training result = saved.isEmpty() ? null : saved.get(0);
                mainHandler.post(() -> callback.onResult(result));
            } else {
                trainingDao.update(training);
                mainHandler.post(() -> callback.onResult(training));
            }
        });
    }
    public void delete(Training training, Callback<Void> callback) {
        executor.execute(() -> {
            trainingDao.delete(training);
            mainHandler.post(() -> callback.onResult(null));
        });
    }
    public interface Callback<T> {
        void onResult(T result);
    }
}