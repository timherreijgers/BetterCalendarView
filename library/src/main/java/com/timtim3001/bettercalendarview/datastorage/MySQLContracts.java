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
