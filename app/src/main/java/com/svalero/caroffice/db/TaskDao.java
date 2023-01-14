package com.svalero.caroffice.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.caroffice.domain.Car;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM Car")
    List<Car> getAll();

    @Query("SELECT * FROM Car WHERE name = :name")
    Car getByName(String name);

    @Query("DELETE FROM Car WHERE name = :name")
    void deleteByName(String name);

    @Insert
    void insert(Car car);

    @Delete
    void delete(Car car);

    @Update
    void update(Car car);

}
