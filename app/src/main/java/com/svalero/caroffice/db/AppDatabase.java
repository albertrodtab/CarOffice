package com.svalero.caroffice.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.caroffice.domain.Office;

@Database(entities = {Office.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract OfficeDao officeDao();
}
