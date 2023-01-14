package com.svalero.caroffice.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Car {
    @PrimaryKey
    @NonNull
    private String name;
    @ColumnInfo
    private String description;
    @ColumnInfo
    private String owner;
    @ColumnInfo
    private boolean done;

    public Car(String name, String description, String owner, boolean b) {
        this.name = name;
        this.description = this.description;
        this.owner = this.owner;
        done = false;
    }

    public Car(String name){
        this.name = name;
        done = false;
    }

    public String getName() { return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isDone() {
        return done;
    }

    public String getOwner() {
        return owner;
    }

    //al montar mi propio adapter para mostrar mis datos yo no me hace falta este método para mostrar las tareas.
/*    @Override
    public String toString() {
        return name;
    }*/
}
