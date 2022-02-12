package com.Deepak_pandey.labexp_9.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TASKS = String.format("create table %s"
            +" (%s integer primary key autoincrement, %s text, %s integer, %s integer, %s integer)",
            DatabaseContract.TABLE_TASKS,
            DatabaseContract.TaskColumns._ID,
            DatabaseContract.TaskColumns.DESCRIPTION,
            DatabaseContract.TaskColumns.IS_COMPLETE,
            DatabaseContract.TaskColumns.IS_PRIORITY,
            DatabaseContract.TaskColumns.DUE_DATE
    );

    public TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + DatabaseContract.TABLE_TASKS);

        onCreate(db);
    }
}
