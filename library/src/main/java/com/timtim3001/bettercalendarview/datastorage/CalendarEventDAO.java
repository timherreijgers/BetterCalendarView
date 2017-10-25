package com.timtim3001.bettercalendarview.datastorage;

import com.timtim3001.bettercalendarview.datastorage.MySQLContracts.CalendarEvents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.timtim3001.bettercalendarview.CalendarEvent;

/**
 * Class for the handling of the CalendarEvents queries.
 *
 * @author Tim
 * @version 1.0
 */
public class CalendarEventDAO {

    private static CalendarEventsDbHelper dbHelper;

    public CalendarEventDAO(Context context) {

        if(dbHelper == null)
            dbHelper = new CalendarEventsDbHelper(context);

    }

    public long addCalendarEvent(CalendarEvent event){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CalendarEvents.COLUMN_NAME_DESCRIPTION, event.getDescription());
        values.put(CalendarEvents.COLUMN_NAME_SMALL_DESCRIPTION, event.getSmallDescription());
        values.put(CalendarEvents.COLUMN_NAME_DAY, event.getDate().getDay());
        values.put(CalendarEvents.COLUMN_NAME_MONTH, event.getDate().getMonth());
        values.put(CalendarEvents.COLUMN_NAME_YEAR, event.getDate().getYear());
        return database.insert(CalendarEvents.TABLE_NAME, null, values);
    }

    public Cursor getCalendarEvents(){

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] projection = {
                CalendarEvents._ID,
                CalendarEvents.COLUMN_NAME_DAY,
                CalendarEvents.COLUMN_NAME_MONTH,
                CalendarEvents.COLUMN_NAME_YEAR,
                CalendarEvents.COLUMN_NAME_SMALL_DESCRIPTION,
                CalendarEvents.COLUMN_NAME_DESCRIPTION
        };

        Cursor cursor = database.query(CalendarEvents.TABLE_NAME, projection, null, null, null, null, null);

        return cursor;
    }


}
