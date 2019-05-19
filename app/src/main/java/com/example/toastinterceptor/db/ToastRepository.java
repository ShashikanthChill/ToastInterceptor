package com.example.toastinterceptor.db;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ToastRepository {

    private ToastDao toastDao;
    private LiveData<List<ToastEntity>> toasts;

    public ToastRepository(Application application) {
        toastDao = ToastDatabase.getDatabaseInstance(application).getToastDao();
        toasts = toastDao.getAllToasts();
    }

    public LiveData<List<ToastEntity>> getAllToasts() {
        return toasts;
    }


    public void insert(ToastEntity toastEntity) {
        new insertAsyncTask(toastDao).execute(toastEntity);
    }

    public void delete(ToastEntity toastEntity) {
        new deleteAsyncTask(toastDao).execute(toastEntity);
    }

    private static class insertAsyncTask extends AsyncTask<ToastEntity, Void, Void> {

        private ToastDao mAsyncTaskDao;

        insertAsyncTask(ToastDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ToastEntity... entities) {
            mAsyncTaskDao.insert(entities[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<ToastEntity, Void, Void> {

        private ToastDao mAsyncTaskDao;

        deleteAsyncTask(ToastDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ToastEntity... entities) {
            mAsyncTaskDao.delete(entities[0]);
            return null;
        }
    }
}
