package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.habittracker.HabitContract.HabitEntry;

import java.util.ArrayList;

class HabitDbHelper extends SQLiteOpenHelper{

    static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "habit.db";

    private static final String SQL_CREATE_HABIT_TABLE = "CREATE TABLE " + HabitEntry.TABLE_NAME +
            " ("
            + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + HabitEntry.COLUMN_EXERCISE + " INTEGER NOT NULL, "
            + HabitEntry.COLUMN_DATE + " TEXT NOT NULL, "
            + HabitEntry.COLUMN_DURATION + " INTEGER NOT NULL);";

    private static final String SQL_DELETE_HABIT_TABLE =
            "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;
    private final Context context;

    HabitDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_HABIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_HABIT_TABLE);
        onCreate(db);
    }

    long insertHabit(int exerciseType, String date, int duration){

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_EXERCISE, exerciseType);
        values.put(HabitEntry.COLUMN_DATE, date);
        values.put(HabitEntry.COLUMN_DURATION, duration);
        return db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    private Cursor queryHabitData(){

        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_EXERCISE,
                HabitEntry.COLUMN_DATE,
                HabitEntry.COLUMN_DURATION
        };

        return db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
    }

    ArrayList<String> readFromDb(){

        Cursor cursor = queryHabitData();

        ArrayList<String> habitsData = null;
        try{
            int count = cursor.getCount();
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int exerciseColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_EXERCISE);
            int dateColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DATE);
            int durationColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_DURATION);
            habitsData = new ArrayList<>(count);
            StringBuilder builder;

            while(cursor.moveToNext()){

                int id = cursor.getInt(idColumnIndex);
                int exercise = cursor.getInt(exerciseColumnIndex);
                String date = cursor.getString(dateColumnIndex);
                int duration = cursor.getInt(durationColumnIndex);

                String exerciseType = getExerciseType(exercise);

                builder = new StringBuilder("\n" + id);
                builder.append(" - ");
                builder.append(exerciseType);
                builder.append(" - ");
                builder.append(date);
                builder.append(" - ");
                builder.append(duration);
                habitsData.add(builder.toString());

            }

        }finally{
            cursor.close();
        }
        return habitsData;
    }


    private String getExerciseType(int exercise){

        String type;

        switch(exercise){
            case HabitEntry.EXERCISE_ENDURANCE:{
                type = context.getString(R.string.exercise_endurance);
            }
            break;
            case HabitEntry.EXERCISE_STRENGTH:{
                type = context.getString(R.string.exercise_strength);
            }
            break;
            case HabitEntry.EXERCISE_BALANCE:{
                type = context.getString(R.string.exercise_balance);
            }
            break;
            case HabitEntry.EXERCISE_FLEXIBILITY:{
                type = context.getString(R.string.exercise_flexibility);
            }
            break;
            default:
                type = context.getString(R.string.exercise);
        }

        return type;
    }
}
