package com.example.android.habittracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.habittracker.HabitContract.HabitEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private static final long A_DAY = (1000 * 60 * 60 * 24);

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HabitDbHelper dbHelper = new HabitDbHelper(this);

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
        long time = System.currentTimeMillis() - (3 * A_DAY);
        String date = sdf.format(time);
        dbHelper.insertHabit(HabitEntry.EXERCISE_BALANCE, date, 45);

        time = time + A_DAY;
        date = sdf.format(time);
        dbHelper.insertHabit(HabitEntry.EXERCISE_ENDURANCE, date, 60);

        time = time + A_DAY;
        date = sdf.format(time);
        dbHelper.insertHabit(HabitEntry.EXERCISE_STRENGTH, date, 30);

        time = time + A_DAY;
        date = sdf.format(time);
        dbHelper.insertHabit(HabitEntry.EXERCISE_FLEXIBILITY, date, 45);

        TextView textView = (TextView) findViewById(R.id.text_view);
        String title = HabitEntry._ID+" - "+HabitEntry.COLUMN_EXERCISE + " - "+ HabitEntry
                .COLUMN_DATE + " - "+HabitEntry.COLUMN_DURATION;
        ArrayList<String> data = dbHelper.readFromDb();
        if(data != null){
            textView.setText(title);
            for(String row : data){
                textView.append(row);
            }
        }

    }


}
