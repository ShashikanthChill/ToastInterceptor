package com.example.toastinterceptor;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.toastinterceptor.db.ToastEntity;
import com.example.toastinterceptor.db.ToastRepository;

import java.util.List;

public class ToastViewModel extends AndroidViewModel {
    private ToastRepository toastRepository;
    private LiveData<List<ToastEntity>> toasts;

    public ToastViewModel(@NonNull Application application) {
        super(application);
        toastRepository = new ToastRepository(application);
        toasts = toastRepository.getAllToasts();
    }

    public LiveData<List<ToastEntity>> getAllToasts() {
        return toasts;
    }

    public void insert(ToastEntity toastEntity) {
        toastRepository.insert(toastEntity);
    }
}
