package com.example.toastinterceptor.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ToastDao {

    @Query("select * from toast_table")
    LiveData<List<ToastEntity>> getAllToasts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ToastEntity toastEntity);

    @Delete
    void delete(ToastEntity toastEntity);
}
