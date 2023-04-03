package com.example.focusapp;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Habit implements Serializable {
    @Exclude
    private String key;



    private int id;
    private String habit_title;
    private String habit_description;
    private String start_time;

    public Habit(){}
    public Habit(int id, String habit_title, String habit_description, String start_time) {
        this.id = id;
        this.habit_title = habit_title;
        this.habit_description = habit_description;
        this.start_time = start_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHabit_title() {
        return habit_title;
    }

    public void setHabit_title(String habit_title) {
        this.habit_title = habit_title;
    }

    public String getHabit_description() {
        return habit_description;
    }

    public void setHabit_description(String habit_description) {
        this.habit_description = habit_description;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
