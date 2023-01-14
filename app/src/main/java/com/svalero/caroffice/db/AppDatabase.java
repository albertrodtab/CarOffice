package com.svalero.caroffice.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.caroffice.domain.Car;

@Database(entities = {Car.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
}

