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

package com.timtim3001.bettercalendarview;

import java.util.Calendar;

/**
 * Class that represents a day
 * @author Tim Herreijgers
 * @version 1.0
 */
public class Day {

    /**
     * A constant representing the month January
     * @since 1.0
     */
    public static final int JANUARY = 0;

    /**
     * A constant representing the month February
     * @since 1.0
     */
    public static final int FEBRUARY = 1;

    /**
     * A constant representing the month March
     * @since 1.0
     */
    public static final int MARCH = 2;

    /**
     * A constant representing the month April
     * @since 1.0
     */
    public static final int APRIL = 3;

    /**
     * A constant representing the month May
     * @since 1.0
     */
    public static final int MAY = 4;

    /**
     * A constant representing the month June
     * @since 1.0
     */
    public static final int JUNE = 5;

    /**
     * A constant representing the month Juli
     * @since 1.0
     */
    public static final int JULI = 6;

    /**
     * A constant representing the month August
     * @since 1.0
     */
    public static final int AUGUST = 7;

    /**
     * A constant representing the month September
     * @since 1.0
     */
    public static final int SEPTEMBER = 8;

    /**
     * A constant representing the month October
     * @since 1.0
     */
    public static final int OCTOBER = 9;

    /**
     * A constant representing the month November
     * @since 1.0
     */
    public static final int NOVEMBER = 10;

    /**
     * A constant representing the month December
     * @since 1.0
     */
    public static final int DECEMBER = 11;

    /**
     * A constant representing Monday
     * @since 1.0
     */
    public static final int MONDAY = 0;

    /**
     * A constant representing Tuesday
     * @since 1.0
     */
    public static final int TUESDAY = 0;

    /**
     * A constant representing Wednesday
     * @since 1.0
     */
    public static final int WEDNESDAY = 0;

    /**
     * A constant representing Thursday
     * @since 1.0
     */
    public static final int THURSDAY = 0;

    /**
     * A constant representing Friday
     * @since 1.0
     */
    public static final int FRIDAY = 0;

    /**
     * A constant representing Saturday
     * @since 1.0
     */
    public static final int SATURDAY = 0;

    /**
     * A constant representing Sunday
     * @since 1.0
     */
    public static final int SUNDAY = 0;

    private int day;
    private int month;
    private int year;

    /**
     * Constructor that sets the data of the day. If the day is not valid the day will be initialized to 0. If the month is not valid the month will be initialized to 0.
     * @param day The day of the month. Depending on the month this can be a range from 0-28 to 0-31
     * @param month The month of the year. January is month 0, December is month 12
     * @param year The year
     * @since 1.0
     */
    public Day(int day, int month, int year) {

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
     * @since 1.0
     */
    public int getDay() {
        return day;
    }

    /**
     * Gets the month
     * @return the month
     * @since 1.0
     */
    public int getMonth() {
        return month;
    }

    /**
     * Gets the year
     * @return the year
     * @since 1.0
     */
    public int getYear() {
        return year;
    }
}

