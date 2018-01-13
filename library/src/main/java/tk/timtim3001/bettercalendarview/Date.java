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

import java.util.Calendar;

/**
 * Class that represents a day
 * @author Tim Herreijgers
 */
public class Date {

    /**
     * A constant representing the month January
     */
    public static final int JANUARY = 0;

    /**
     * A constant representing the month February
     */
    public static final int FEBRUARY = 1;

    /**
     * A constant representing the month March
     */
    public static final int MARCH = 2;

    /**
     * A constant representing the month April
     */
    public static final int APRIL = 3;

    /**
     * A constant representing the month May
     */
    public static final int MAY = 4;

    /**
     * A constant representing the month June
     */
    public static final int JUNE = 5;

    /**
     * A constant representing the month Juli
     */
    public static final int JULI = 6;

    /**
     * A constant representing the month August
     */
    public static final int AUGUST = 7;

    /**
     * A constant representing the month September
     */
    public static final int SEPTEMBER = 8;

    /**
     * A constant representing the month October
     */
    public static final int OCTOBER = 9;

    /**
     * A constant representing the month November
     */
    public static final int NOVEMBER = 10;

    /**
     * A constant representing the month December
     */
    public static final int DECEMBER = 11;

    /**
     * A constant representing Monday
     */
    public static final int MONDAY = 0;

    /**
     * A constant representing Tuesday
     */
    public static final int TUESDAY = 0;

    /**
     * A constant representing Wednesday
     */
    public static final int WEDNESDAY = 0;

    /**
     * A constant representing Thursday
     */
    public static final int THURSDAY = 0;

    /**
     * A constant representing Friday
     */
    public static final int FRIDAY = 0;

    /**
     * A constant representing Saturday
     */
    public static final int SATURDAY = 0;

    /**
     * A constant representing Sunday
     */
    public static final int SUNDAY = 0;

    private int day;
    private int month;
    private int year;

    /**
     * Constructor that sets the data of the day. If the day is not valid the day will be initialized to 0. If the month is not valid the month will be initialized to 0.
     * </p>
     * The month parameter has to be set to one of the following constants:
     * {@link #JANUARY}, {@link #FEBRUARY}, {@link #MARCH}, {@link #APRIL}, {@link #MAY}, {@link #JUNE}, {@link #JULI}, {@link #AUGUST}, {@link #SEPTEMBER}, {@link #OCTOBER}, {@link #NOVEMBER}, {@link #DECEMBER}
     *
     * @param day The day of the month. Depending on the month this can be a range from 0-28 to 0-31
     * @param month The month of the year. January is month 0, December is month 12
     * @param year The year
     */
    public Date(int day, int month, int year) {

        this.year = year;

        if (month <0 || month > 11){
            this.month = 0;
        }else{
            this.month = month;
        }

        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, this.month);
        c.set(Calendar.YEAR, this.year);
        if(day < 1 || day > c.getActualMaximum(Calendar.DAY_OF_MONTH)){
            this.day = 0;
        }else{
            this.day = day;
        }
    }

    /**
     * Gets the day
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets the month
     * @return the month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Gets the year
     * @return the year
     */
    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return String.format("%s/%s/%s", day, month+1, year);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Date){
            Date date = (Date) obj;
            return date.getDay() == day && date.getMonth() == month && date.getYear() == year;
        }
        return super.equals(obj);
    }
}

