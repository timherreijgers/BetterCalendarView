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

    private static final String TAG = "SexPositionCalenderView";

    private final String months[] = {"Jan", "Feb", "Mar", "Apr","May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    //Attributes for all layout elements
    private LinearLayout daysLayout;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private TextView monthView;
    private TextView mondayView;
    private TextView tuesdayView;
    private TextView wednesDayView;
    private TextView thursdayView;
    private TextView fridayView;
    private TextView saterdayView;
    private TextView sundayView;
    private List<TextView> dayList = new ArrayList<>();

    //Attributs for basic functionality
    private Calendar calendar;
    private boolean dayIsColored = false;
    private ClickHandler clickHandler = new ClickHandler();
    private Day selectedDay = null;
    private int totalMonthDifference = 0;
    private Context context;
    private AttributeSet attrs;

    //Attributes for customizability
    private OnDateChangedListener dateChangedListener = null;
    private boolean colorCurrentDay = true;

    //Attributes for theme-ing
    private final int DARK = 0;
    private final int LIGHT = 1;
    private int currentDayColor;
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
            changeColorScheme(checkThemeColor(themeArray.getColor(0, Color.WHITE)));
        }finally {
            typedArray.recycle();
        }

        CalendarEventDAO calendarEventDAO = new CalendarEventDAO(context);
        CalendarEvent event = new CalendarEvent("Test", "SmallTest", new Day(21, 2, 2018));
        calendarEventDAO.addCalendarEvent(event);

        init(context);
        populateGrid();
        setCurrentMonth();

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
        wednesDayView = (TextView) findViewById(R.id.wednesdayView);
        thursdayView = (TextView) findViewById(R.id.thursdayView);
        fridayView = (TextView) findViewById(R.id.fridayView);
        saterdayView = (TextView) findViewById(R.id.saturdayView);
        sundayView = (TextView) findViewById(R.id.sundayView);

        mondayView.setTextColor(dayColor);
        tuesdayView.setTextColor(dayColor);
        wednesDayView.setTextColor(dayColor);
        thursdayView.setTextColor(dayColor);
        fridayView.setTextColor(dayColor);
        saterdayView.setTextColor(dayColor);
        sundayView.setTextColor(dayColor);
        monthView.setTextColor(dayColor);

        DrawableCompat.setTint(nextButton.getDrawable(), dayColor);
        DrawableCompat.setTint(prevButton.getDrawable(), dayColor);

        prevButton.setOnClickListener(clickHandler);
        nextButton.setOnClickListener(clickHandler);

        calendar = Calendar.getInstance();
        monthView.setText(months[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR));
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

        if(colorCurrentDay) {
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

        return calendar.get(Calendar.MONTH);
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
     * Sets the OnDateChangedListener. If you want to remove the listener you can pass null
     * <p/>
     * @see OnDateChangedListener
     * @param dateChangedListener The listener that has to be set
     */
    public void setDateChangedListener(@Nullable OnDateChangedListener dateChangedListener) {
        this.dateChangedListener = dateChangedListener;
    }

    /**
     * Checks whether the view has an OnDateChangedListener.
     * <p/>
     * @see OnDateChangedListener
     * @return If the view has an OnDateChangedListener
     */
    public boolean hasOnDateChangedListener(){
        return dateChangedListener != null;
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
                selectedDay = new Day(Integer.parseInt(textView.getText().toString()), getMonth(), getYear());
                if(dateChangedListener != null){
                    dateChangedListener.onDateChanged(selectedDay);
                }
            }else if(v instanceof ImageButton){
                if(v.getId() == nextButton.getId())
                    difference = 1;
                if(v.getId() == prevButton.getId())
                    difference = -1;
            }
            changeMonth(difference);
            if(selectedDay != null) {
                if (getMonth() == selectedDay.getMonth() && getYear() == selectedDay.getYear())
                    lastSelectedView.setBackgroundColor(selectedColor);
                else
                    lastSelectedView.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * Interface definition for a callback when the date is changed
     */
    public interface OnDateChangedListener{
        /**
         * Callback that's called when the date is changed
         *
         * @param selectedDay The newly selected day
         */
        public void onDateChanged(Day selectedDay);
    }
}