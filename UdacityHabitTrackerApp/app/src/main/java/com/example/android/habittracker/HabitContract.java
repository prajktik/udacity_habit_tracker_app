package com.example.android.habittracker;

import android.provider.BaseColumns;

public final class HabitContract{


    private HabitContract(){
    }

    public static final class HabitEntry implements BaseColumns{

        public static final String TABLE_NAME = "habit";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_EXERCISE = "exercise";
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_DURATION = "duration";

        public static final int EXERCISE_ENDURANCE = 0;
        public static final int EXERCISE_STRENGTH = 1;
        public static final int EXERCISE_BALANCE = 2;
        public static final int EXERCISE_FLEXIBILITY = 3;
    }
}
