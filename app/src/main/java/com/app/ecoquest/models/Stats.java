package com.app.ecoquest.models;

public class Stats {
    private long id, userId;
    private int level,exp;

    public Stats(long userId, int level, int exp) {
        this.userId = userId;
        this.level = level;
        this.exp = exp;
    }

    public Stats(long id, long userId, int level, int exp) {
        this.id = id;
        this.userId = userId;
        this.level = level;
        this.exp = exp;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }


}
