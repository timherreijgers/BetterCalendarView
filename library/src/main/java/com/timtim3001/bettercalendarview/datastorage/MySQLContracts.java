package com.timtim3001.bettercalendarview.datastorage;

import android.provider.BaseColumns;

/**
 * Contract class for the database
 *
 * @author Tim
 * @version 1.0
 */
public final class MySQLContracts {

    private MySQLContracts(){

    }

    /**
     * Contract for the CalendarEvents table
     *
     * @author Tim
     * @version 1.0
     */
    public static class CalendarEvents implements BaseColumns{

        public static final String TABLE_NAME = "event";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_SMALL_DESCRIPTION = "smallDescription";
        public static final String COLUMN_NAME_DAY = "day";
        public static final String COLUMN_NAME_MONTH = "month";
        public static final String COLUMN_NAME_YEAR = "year";

    }

}
