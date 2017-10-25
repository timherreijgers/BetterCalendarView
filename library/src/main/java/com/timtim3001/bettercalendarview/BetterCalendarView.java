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

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timtim3001.bettercalendarview.datastorage.CalendarEventDAO;
import com.timtim3001.bettercalendarview.datastorage.MySQLContracts;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This class is a calendar widget that supports events. You can use it for selecting and displaying
 * dates and making and display calendar events
 * <p>
 * The calendar can be customized and follows the material design guidelines. You can
 * select a date by tapping on it and can scroll either by using the buttons on the top
 * or swipe left and right.
 */
public class BetterCalendarView extends LinearLayout {

    private static final String TAG = "BetterCalendarView";

    private final String months[] = {"Jan", "Feb", "Mar", "Apr","May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    //Attributes for all layout elements
    private LinearLayout daysLayout;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private TextView monthView;
    private TextView mondayView;
    private TextView tuesdayView;
    private TextView wednesdayView;
    private TextView thursdayView;
    private TextView fridayView;
    private TextView saturdayView;
    private TextView sundayView;
    private List<TextView> dayList = new ArrayList<>();

    //Attributs for basic functionality
    private Calendar calendar;
    private boolean dayIsColored = false;
    private ClickHandler clickHandler = new ClickHandler();
    private Date selectedDate = null;
    private int totalMonthDifference = 0;
    private Context context;
    private AttributeSet attrs;
    private List<CalendarEvent> calendarEvents;
    private List<CalendarEvent> eventsToDisplay;
    private CalendarEventDAO calendarEventDAO;

    //Attributes for customizability
    private OnDateSelectedListener dateChangedListener = null;
    private OnEventSelectedListener eventSelectedListener = null;
    private boolean colorCurrentDay = true;

    //Attributes for theme-ing
    private final int DARK = 0;
    private final int LIGHT = 1;
    private int currentDayColor;
    private int eventColor;
    private int dayColor;

    public BetterCalendarView(@NonNull Context context) {
        this(context, null);
    }

    public BetterCalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "BetterCalendarView");

        this.context = context;
        this.attrs = attrs;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BetterCalendarView, 0, 0);
        TypedArray themeArray = context.getTheme().obtainStyledAttributes(new int[] {android.R.attr.colorBackground});
        try{
            colorCurrentDay = typedArray.getBoolean(R.styleable.BetterCalendarView_colorCurrentDay, true);
            //TODO: add attribute
            eventColor = Color.MAGENTA;
            changeColorScheme(checkThemeColor(themeArray.getColor(0, Color.WHITE)));
        }finally {
            typedArray.recycle();
        }

        calendarEventDAO = new CalendarEventDAO(context);

        //TODO: fix the month
        CalendarEvent event = new CalendarEvent("Test", "SmallTest", new Date(21, 10, 2017));
        calendarEventDAO.addCalendarEvent(event);

        init(context);
        populateGrid();
        setCurrentMonth();

    }

    private void getAllEvents(){
        Cursor cursor = calendarEventDAO.getCalendarEvents();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(MySQLContracts.CalendarEvents._ID));
            int day = cursor.getInt(cursor.getColumnIndexOrThrow(MySQLContracts.CalendarEvents.COLUMN_NAME_DAY));
            int month = cursor.getInt(cursor.getColumnIndexOrThrow(MySQLContracts.CalendarEvents.COLUMN_NAME_MONTH));
            int year = cursor.getInt(cursor.getColumnIndexOrThrow(MySQLContracts.CalendarEvents.COLUMN_NAME_YEAR));
            String smallDescription = cursor.getString(cursor.getColumnIndexOrThrow(MySQLContracts.CalendarEvents.COLUMN_NAME_SMALL_DESCRIPTION));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(MySQLContracts.CalendarEvents.COLUMN_NAME_DESCRIPTION));
            calendarEvents.add(new CalendarEvent(itemId, description, smallDescription, new Date(day, month, year)));
        }
        cursor.close();
    }

    private int checkThemeColor(int color){

        if((Color.red(color) + Color.green(color) + Color.blue(color)) / 3 > 175)
            return LIGHT;

        return DARK;

    }

    private void changeColorScheme(int colorScheme){

        if(colorScheme == LIGHT){
            dayColor = Color.DKGRAY;
        }else if(colorScheme == DARK){
            dayColor = Color.WHITE;
        }
        currentDayColor = Color.CYAN;

    }

    private void init(Context context){
        Log.d(TAG, "init");
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_layout, this);

        daysLayout = (LinearLayout) findViewById(R.id.days_layout);
        prevButton = (ImageButton) findViewById(R.id.calendar_prev_button);
        nextButton = (ImageButton) findViewById(R.id.calendar_next_button);
        monthView = (TextView) findViewById(R.id.calendar_date_display);

        mondayView  = (TextView) findViewById(R.id.mondayView);
        tuesdayView = (TextView) findViewById(R.id.tuesdayView);
        wednesdayView = (TextView) findViewById(R.id.wednesdayView);
        thursdayView = (TextView) findViewById(R.id.thursdayView);
        fridayView = (TextView) findViewById(R.id.fridayView);
        saturdayView = (TextView) findViewById(R.id.saturdayView);
        sundayView = (TextView) findViewById(R.id.sundayView);

        mondayView.setTextColor(dayColor);
        tuesdayView.setTextColor(dayColor);
        wednesdayView.setTextColor(dayColor);
        thursdayView.setTextColor(dayColor);
        fridayView.setTextColor(dayColor);
        saturdayView.setTextColor(dayColor);
        sundayView.setTextColor(dayColor);
        monthView.setTextColor(dayColor);

        DrawableCompat.setTint(nextButton.getDrawable(), dayColor);
        DrawableCompat.setTint(prevButton.getDrawable(), dayColor);

        prevButton.setOnClickListener(clickHandler);
        nextButton.setOnClickListener(clickHandler);

        calendar = Calendar.getInstance();
        monthView.setText(months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR));

        calendarEvents = new ArrayList<>();
        getAllEvents();
    }

    private void setCurrentMonth(){
        Log.d(TAG, "setCurrentMonth");
        changeMonth(0);
    }

    private int changeMonth(int difference){
        Log.d(TAG, "changeMonth");
        totalMonthDifference += difference;
        calendar.add(Calendar.MONTH, difference);

        monthView.setText(months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR));
        eventsToDisplay = getAllCalendarEventsCurrentMonth(calendar.get(Calendar.MONTH), calendar.get(Calendar.YEAR));

        int daysInMonth, daysInLastMonth, firstDayOfMonth;

        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.set(Calendar.DAY_OF_MONTH, currentDay);

        daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        calendar.add(Calendar.MONTH, -1);
        daysInLastMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.MONTH, 1);

        switch (firstDayOfMonth){
            case Calendar.MONDAY:
                changeDays(0, daysInMonth, daysInLastMonth);
                break;
            case Calendar.TUESDAY:
                changeDays(1, daysInMonth, daysInLastMonth);
                break;
            case Calendar.WEDNESDAY:
                changeDays(2, daysInMonth, daysInLastMonth);
                break;
            case Calendar.THURSDAY:
                changeDays(3, daysInMonth, daysInLastMonth);
                break;
            case Calendar.FRIDAY:
                changeDays(4, daysInMonth, daysInLastMonth);
                break;
            case Calendar.SATURDAY:
                changeDays(5, daysInMonth, daysInLastMonth);
                break;
            case Calendar.SUNDAY:
                changeDays(6, daysInMonth, daysInLastMonth);
                break;
        }

        drawEvents();

        if(colorCurrentDay) {
            colorCurrentDay();
        }

        return calendar.get(Calendar.MONTH);
    }

    private void colorCurrentDay(){
        if (Calendar.getInstance().get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
            for (int i = 0; i < dayList.size(); i++) {
                if (dayList.get(i).getAlpha() == 1f)
                    if (dayList.get(i).getText().equals(Integer.toString(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)))) {
                        dayList.get(i).setTextColor(currentDayColor);
                        dayIsColored = true;
                    }
            }
        } else {
            if (dayIsColored) {
                for (int i = 0; i < dayList.size(); i++) {
                    dayList.get(i).setTextColor(dayColor);
                }
                dayIsColored = false;
            }
        }
    }

    private void drawEvents(){
        for(TextView view : dayList){
            view.setBackgroundColor(Color.TRANSPARENT);
        }

        for(CalendarEvent event : eventsToDisplay){
            for (int i = 0; i < dayList.size(); i++) {
                if (dayList.get(i).getAlpha() == 1f) {
                    if (dayList.get(i).getText().equals(Integer.toString(event.getDate().getDay()))) {
                        dayList.get(i).setBackgroundColor(eventColor);
                    }
                }
            }
        }
    }

    private void changeDays(int startDay, int maxDays, int maxDaysLastMonth){
        Log.d(TAG, "changeDays");
        int dayOfMonth = 1;

        for(int i = startDay-1;i>=0;i--){
            dayList.get(i).setText(String.format("%s", maxDaysLastMonth - (startDay-1-i)));
            dayList.get(i).setAlpha(0.25f);
            dayList.get(i).setClickable(false);
        }
        for(int i = startDay; dayOfMonth <= maxDays; dayOfMonth++, i++){
            dayList.get(i).setText(String.format("%s", dayOfMonth));
            dayList.get(i).setAlpha(1f);
            dayList.get(i).setClickable(true);
        }
        for(int i = startDay + maxDays;i<dayList.size();i++){
            dayList.get(i).setText(String.format("%s", i - maxDays - startDay + 1));
            dayList.get(i).setAlpha(0.25f);
            dayList.get(i).setClickable(false);
        }
    }

    private void populateGrid(){
        Log.d(TAG, "populateGrid");

        LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(context, attrs);
        textViewParams.width = LayoutParams.MATCH_PARENT;
        textViewParams.height = LayoutParams.MATCH_PARENT;
        textViewParams.weight = 1;

        LayoutParams llParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        llParams.weight = 1;

        for(int y = 0;y<6;y++){
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(HORIZONTAL);
            linearLayout.setLayoutParams(llParams);
            for(int x = 0;x<7;x++){
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER);
                textView.setText("1");
                textView.setOnClickListener(clickHandler);
                textView.setTextColor(dayColor);
                dayList.add(textView);
                linearLayout.addView(textView, textViewParams);
            }
            daysLayout.addView(linearLayout);
        }
    }

    private List<CalendarEvent> getAllCalendarEventsCurrentMonth(int month, int year){
        List<CalendarEvent> eventList = new ArrayList<>();
        for(CalendarEvent event : calendarEvents){
            if(event.getDate().getMonth() == month && event.getDate().getYear() == year){
                eventList.add(event);
            }
        }
        return eventList;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
       changeMonth(totalMonthDifference);
    }

    /**
     * Gets the currently shown month
     * @return The currently shown month
     */
    public int getMonth(){
        return calendar.get(Calendar.MONTH);
    }

    /**
     * Gets the currently shown year
     * @return The currently shown year
     */
    public  int getYear(){
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Gets whether the current day has to be colored a different color.
     * @return Whether current day is colored differently
     */
    public boolean getColorCurrentDay() {
        return colorCurrentDay;
    }

    /**
     * Sets whether the current day has to be colored a different color.
     * @param colorCurrentDay boolean - Whether current day is colored differently
     */
    public void setColorCurrentDay(boolean colorCurrentDay) {
        this.colorCurrentDay = colorCurrentDay;
    }

    /**
     * Sets the OnDateSelectedListener. If you want to remove the listener you can pass null
     * <p/>
     * @see OnDateSelectedListener
     * @param dateChangedListener The listener that has to be set
     */
    public void setDateChangedListener(@Nullable OnDateSelectedListener dateChangedListener) {
        this.dateChangedListener = dateChangedListener;
    }

    /**
     * Checks whether the view has an OnDateSelectedListener.
     * <p/>
     * @see OnDateSelectedListener
     * @return If the view has an OnDateSelectedListener
     */
    public boolean hasOnDateChangedListener(){
        return dateChangedListener != null;
    }

    /**
     * Method that add an {@link CalendarEvent} to the calender view
     * @param event The event that has to be added
     * @return  Whether the event was added successfully
     */
    public boolean addCalendarEvent(CalendarEvent event){

        return false;
    }

    /**
     * Returns all the {@link CalendarEvent}s as an array
     * @return all the {@link CalendarEvent}s
     */
    public CalendarEvent[] getAllCalendarEvents(){
        return (CalendarEvent[]) calendarEvents.toArray();
    }

    private class ClickHandler implements OnClickListener{

        private View lastSelectedView = null;
        private int selectedColor = Color.RED;

        @Override
        public void onClick(View v) {

            int difference = 0;
            if(v instanceof TextView){
                TextView textView = (TextView) v;

                if(lastSelectedView != null){
                    lastSelectedView.setBackgroundColor(Color.TRANSPARENT);
                }

                lastSelectedView = v;
                v.setBackgroundColor(selectedColor);
                selectedDate = new Date(Integer.parseInt(textView.getText().toString()), getMonth(), getYear());
                if(dateChangedListener != null){
                    dateChangedListener.onDateChanged(selectedDate);
                }
            }else if(v instanceof ImageButton){
                if(v.getId() == nextButton.getId())
                    difference = 1;
                if(v.getId() == prevButton.getId())
                    difference = -1;
            }
            changeMonth(difference);
            if(selectedDate != null) {
                if (getMonth() == selectedDate.getMonth() && getYear() == selectedDate.getYear())
                    lastSelectedView.setBackgroundColor(selectedColor);
                else
                    lastSelectedView.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * Interface definition for a callback to be invoked when a date is selected
     */
    public interface OnDateSelectedListener {
        /**
         * called when a date is clicked
         *
         * @param selectedDate The newly selected day
         */
        void onDateChanged(Date selectedDate);
    }

    /**
     * Interface definition for a callback to be invoked when a event is selected
     */
    public interface OnEventSelectedListener{
        /**
         * Called when a event is clicked
         * @param event The selected event
         */
        void onEventSelected(CalendarEvent event);
    }
}