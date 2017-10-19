package com.timtim3001.bettercalendarview.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Class for making the MySQLite connection
 *
 * @author Tim
 * @version 1.0
 */

public class CalendarEventsDbHelper extends SQLiteOpenHelper{

    private static final String TAG = "CALENDAREVENTSDBHELPER";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MySQLContracts.CalendarEvents.TABLE_NAME + " (" +
                    MySQLContracts.CalendarEvents._ID + " INTEGER PRIMARY KEY," +
                    MySQLContracts.CalendarEvents.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    MySQLContracts.CalendarEvents.COLUMN_NAME_SMALL_DESCRIPTION + " TEXT," +
                    MySQLContracts.CalendarEvents.COLUMN_NAME_DAY + " INTEGER," +
                    MySQLContracts.CalendarEvents.COLUMN_NAME_MONTH + " INTEGER," +
                    MySQLContracts.CalendarEvents.COLUMN_NAME_YEAR + " INTEGER)";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BetterCalendarView.db";

    public CalendarEventsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        Log.d(TAG, "Database created at " + db.getPath());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Do nothing on upgrade yet
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
