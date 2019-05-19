package com.example.toastinterceptor.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {ToastEntity.class}, version = 1)
@TypeConverters({DateTypeConvertor.class})
abstract class ToastDatabase extends RoomDatabase {

    abstract ToastDao getToastDao();

    private static volatile ToastDatabase INSTANCE;

    static ToastDatabase getDatabaseInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (ToastDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            ToastDatabase.class, "Toast_DB")
                            .fallbackToDestructiveMigration()
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}