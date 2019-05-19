package com.example.toastinterceptor.db;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Objects;

@Entity(tableName = "toast_table")
public class ToastEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "toast_id")
    private int id;

    @ColumnInfo(name = "package_name")
    private String packageName;

    @ColumnInfo(name = "toast_content")
    private String content;

    @ColumnInfo(name = "event_time")
    private Date date;

    ToastEntity() {
    }


    @Ignore
    public ToastEntity(String packageName, String content, Date date) {
        this.packageName = packageName;
        this.content = content;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ToastEntity)) return false;
        ToastEntity that = (ToastEntity) o;
        return getId() == that.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Toast: {" +
                "id=" + id +
                ", packageName='" + packageName + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}
