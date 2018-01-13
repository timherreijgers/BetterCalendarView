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

import android.provider.BaseColumns;

/**
 * Contract class for the database
 *
 * @author Tim
 * @version 1.0
 */
final class MySQLContracts {

    private MySQLContracts(){

    }

    /**
     * Contract for the CalendarEvents table
     *
     * @author Tim
     * @version 1.0
     */
     static class CalendarEvents implements BaseColumns{

         static final String TABLE_NAME = "event";
         static final String COLUMN_NAME_DESCRIPTION = "description";
         static final String COLUMN_NAME_SMALL_DESCRIPTION = "smallDescription";
         static final String COLUMN_NAME_DAY = "day";
         static final String COLUMN_NAME_MONTH = "month";
         static final String COLUMN_NAME_YEAR = "year";

    }

}
