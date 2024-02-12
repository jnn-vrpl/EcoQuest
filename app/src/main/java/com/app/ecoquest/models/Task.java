package com.app.ecoquest.models;

public class Task {
    private long id;
    private String name, date, time;

    private boolean isComplete;

    public Task(String name, String date, String time, boolean isComplete) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.isComplete = isComplete;
    }

    public Task(long id, String name, String date, String time, boolean isComplete) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
        this.isComplete = isComplete;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
