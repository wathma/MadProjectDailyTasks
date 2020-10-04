package com.example.easy_app;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "todo";
    private static final String TABLE_NAME = "todo";

    // Column names of todo table in todo database
    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String STARTED = "started";
    private static final String FINISHED = "finished";

    public DbHandler(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String TABLE_CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" " +
                "("
                +ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +TITLE + " TEXT,"
                +DESCRIPTION + " TEXT,"
                +STARTED+ " TEXT,"
                + FINISHED +" TEXT" +
                ");";

        db.execSQL(TABLE_CREATE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        // Drop older table if exists
        db.execSQL(DROP_TABLE_QUERY);
        //Re - create table
        onCreate(db);
    }


    // Add a single task
    public void addTasks(Daily daily){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, daily.getTaskTitle());
        contentValues.put(DESCRIPTION, daily.getTaskDescription());
        contentValues.put(STARTED, daily.getStarted());
        contentValues.put(FINISHED, daily.getFinished());

        //save to table
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        // close database
        sqLiteDatabase.close();
    }

    // Count records
    public int countTasks(){
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }

    // Get all tasks into a list
    public List<Daily> getAllTasks(){

        List<Daily> dailies = new ArrayList();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME;

        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do {
                // Create new Daily object
                Daily daily = new Daily();

                daily.setTaskID(cursor.getInt(0));
                daily.setTaskTitle(cursor.getString(1));
                daily.setTaskDescription(cursor.getString(2));
                daily.setStarted(cursor.getLong(3));
                daily.setFinished(cursor.getLong(4));


                dailies.add(daily);
            }while (cursor.moveToNext());
        }
        return dailies;
    }

    // Delete task
    public void deleteTasks(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME,"id =?",new String[]{String.valueOf(id)});
        db.close();
    }

    // Get a single task
    public Daily getSingleTask(int id){
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABLE_NAME,new String[]{ID,TITLE,DESCRIPTION,STARTED, FINISHED},
                ID + "= ?",new String[]{String.valueOf(id)}
                ,null,null,null);

        Daily daily;
        if(cursor != null){
            cursor.moveToFirst();
            daily = new Daily(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getLong(4)
            );
            return daily;
        }
        return null;
    }

    // Update a single task
    public int updateSingleTask(Daily daily){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(TITLE, daily.getTaskTitle());
        contentValues.put(DESCRIPTION, daily.getTaskDescription());
        contentValues.put(STARTED, daily.getStarted());
        contentValues.put(FINISHED, daily.getFinished());

        int status = db.update(TABLE_NAME,contentValues,ID +" =?",
                new String[]{String.valueOf(daily.getTaskID())});

        db.close();
        return status;
    }
}
