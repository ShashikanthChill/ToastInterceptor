package com.example.toastinterceptor.db;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateTypeConvertor {
    @TypeConverter
    public static Date fromTimestampToDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long fromDateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}