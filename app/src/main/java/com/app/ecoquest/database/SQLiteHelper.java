package com.app.ecoquest.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.app.ecoquest.models.Stats;
import com.app.ecoquest.models.Task;
import com.app.ecoquest.utils.Methods;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ecoquest.db";
    private static final String TABLE_TASK = "Tasks";
    private static final String TABLE_STATS = "Stats";
    private static final String KEY_ID = "Id";
    private static final String KEY_NAME = "Name";
    private static final String KEY_DATE = "Date";
    private static final String KEY_TIME = "Time";
    private static final String KEY_IS_COMPLETE = "Completed";
    private static final String KEY_USER_ID = "UserId";
    private static final String KEY_LEVEL = "Level";
    private static final String KEY_EXP = "Exp";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_TASK + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT," +
                KEY_DATE + " TEXT," +
                KEY_TIME + " TEXT," +
                KEY_IS_COMPLETE + " INTEGER" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        String CREATE_TABLE2 = "CREATE TABLE " + TABLE_STATS + "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_USER_ID + " INTEGER," +
                KEY_LEVEL + " INTEGER," +
                KEY_EXP + " INTEGER" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STATS);
        onCreate(sqLiteDatabase);
    }

    public long createTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_DATE, task.getDate());
        values.put(KEY_TIME, task.getTime());
        values.put(KEY_IS_COMPLETE, task.isComplete() ? 1 : 0);
        return db.insert(TABLE_TASK, null, values);
    }

    public boolean updateTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, task.getName());
        values.put(KEY_DATE, task.getDate());
        values.put(KEY_TIME, task.getTime());
        int result = db.update(TABLE_TASK, values, KEY_ID + " = ?", new String[]{String.valueOf(task.getId())});
        return result != -1;
    }

    public Task readTask(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_TASK + " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        Task task = null;
        if (cursor.moveToFirst()) {
            task = new Task(cursor.getLong(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3),
                    cursor.getLong(4) == 1);
        }
        cursor.close();
        return task;
    }

    public List<Task> readTasks(boolean isCompleted) {
        int complete = isCompleted ? 1 : 0;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> tasks = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASK + " WHERE " + KEY_IS_COMPLETE + " = " + complete + " ORDER BY " + KEY_DATE + " DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(
                        cursor.getLong(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        cursor.getLong(4) == 1
                );
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public List<Task> readTasksByDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Task> tasks = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TASK + " WHERE " + KEY_DATE + " = '" + date + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(
                        cursor.getLong(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        cursor.getLong(4) == 1
                );
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return tasks;
    }

    public boolean completeTask(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_IS_COMPLETE, 1);
        int result = db.update(TABLE_TASK, values, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        return result != -1;
    }

    public boolean deleteTask(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_TASK, KEY_ID + " = ?", new String[]{String.valueOf(id)});
        return rowsDeleted > 0;
    }

    public long createStats(Stats stats) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, stats.getUserId());
        values.put(KEY_LEVEL, stats.getLevel());
        values.put(KEY_EXP, stats.getExp());
        return db.insert(TABLE_STATS, null, values);
    }

    public boolean updateStats(Stats stats) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_ID, stats.getUserId());
        values.put(KEY_LEVEL, stats.getLevel());
        values.put(KEY_EXP, stats.getExp());
        int result = db.update(TABLE_STATS, values, KEY_ID + " = ?", new String[]{String.valueOf(stats.getId())});
        return result != -1;
    }

    public void addStats() {
        Stats stats = readStats(1);
        int level = stats.getLevel();
        int totalExp = Methods.getTotalExp(level);
        int exp = stats.getExp() + 35;
        if (exp >= totalExp) {
            exp = exp - totalExp;
            level++;
        }
        stats.setExp(exp);
        stats.setLevel(level);
        updateStats(stats);
    }

    public Stats readStats(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_STATS + " WHERE " + KEY_USER_ID + " = " + userId;
        Cursor cursor = db.rawQuery(selectQuery, null);
        Stats stats = null;
        if (cursor.moveToFirst()) {
            stats = new Stats(cursor.getLong(0), cursor.getLong(1),
                    cursor.getInt(2), cursor.getInt(3));
        }
        cursor.close();
        return stats;
    }
}
