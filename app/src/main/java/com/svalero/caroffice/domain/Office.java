package com.svalero.caroffice.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity
public class Office {

    @PrimaryKey
    @NonNull
    private String name;
    @ColumnInfo
    private String description;
    @ColumnInfo
    private String owner;
    @ColumnInfo
    private boolean done;

    public Office(String name, String description, String owner, boolean b) {
        this.name = name;
        this.description = this.description;
        this.owner = this.owner;
        done = false;
    }

    public Office(String name){
        this.name = name;
        done = false;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
