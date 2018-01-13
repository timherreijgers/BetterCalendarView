/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tk.timtim3001.bettercalendarview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Class for the handling of the CalendarEvents queries.
 *
 * @author Tim
 * @version 1.0
 */
class CalendarEventDAO {

    private static CalendarEventsDbHelper dbHelper;

     CalendarEventDAO(Context context) {

        if(dbHelper == null)
            dbHelper = new CalendarEventsDbHelper(context);

    }

     long addCalendarEvent(CalendarEvent event){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MySQLContracts.CalendarEvents.COLUMN_NAME_DESCRIPTION, event.getDescription());
        values.put(MySQLContracts.CalendarEvents.COLUMN_NAME_SMALL_DESCRIPTION, event.getSmallDescription());
        values.put(MySQLContracts.CalendarEvents.COLUMN_NAME_DAY, event.getDate().getDay());
        values.put(MySQLContracts.CalendarEvents.COLUMN_NAME_MONTH, event.getDate().getMonth());
        values.put(MySQLContracts.CalendarEvents.COLUMN_NAME_YEAR, event.getDate().getYear());
        return database.insert(MySQLContracts.CalendarEvents.TABLE_NAME, null, values);
    }

     boolean deleteById(long id){

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String[] selection =  {Long.toString(id)};
        if(database.delete(MySQLContracts.CalendarEvents.TABLE_NAME, MySQLContracts.CalendarEvents._ID + " LIKE ?", selection) == 0)
            return false;
        return true;

    }

     Cursor getCalendarEvents(){

        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String[] projection = {
                MySQLContracts.CalendarEvents._ID,
                MySQLContracts.CalendarEvents.COLUMN_NAME_DAY,
                MySQLContracts.CalendarEvents.COLUMN_NAME_MONTH,
                MySQLContracts.CalendarEvents.COLUMN_NAME_YEAR,
                MySQLContracts.CalendarEvents.COLUMN_NAME_SMALL_DESCRIPTION,
                MySQLContracts.CalendarEvents.COLUMN_NAME_DESCRIPTION
        };

        Cursor cursor = database.query(MySQLContracts.CalendarEvents.TABLE_NAME, projection, null, null, null, null, null);

        return cursor;
    }


}
