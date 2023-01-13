package com.svalero.caroffice.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.caroffice.domain.Office;

import java.util.List;

@Dao
public interface OfficeDao {

    @Query("SELECT * FROM office")
    List<Office> getAll();

    @Query("SELECT * FROM office WHERE name = :name")
    Office getByName(String name);

    @Query("DELETE FROM office WHERE name = :name")
    void deleteByName(String name);

    @Insert
    void insert(Office office);

    @Delete
    void delete(Office office);

    @Update
    void update(Office office);

}
