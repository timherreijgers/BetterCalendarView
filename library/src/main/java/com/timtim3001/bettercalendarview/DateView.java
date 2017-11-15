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
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * An View that is used for the displaying of the days.
 *
 * @author Tim
 */
class DateView extends TextView {

    private Paint backgroundPaint = new Paint();
    private int radius;
    private int lastColor;

    /**
     * Constructor to initialize an BetterCalendarView
     *
     * @param context The context
     */
    public DateView(Context context) {
        super(context);
        backgroundPaint.setColor(Color.TRANSPARENT);
    }

    /**
     * Constructor to initialize an BetterCalendarView
     *
     * @param context The context
     * @param attrs The xml-attributes
     */
    public DateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        backgroundPaint.setColor(Color.TRANSPARENT);
    }

    /**
     * Method that sets the background color
     *
     * @param color The background color
     */
    public void setBackgroundColor(int color){
        backgroundPaint.setColor(color);
        if(lastColor != color){
            invalidate();
        }
        lastColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int middleX = canvas.getWidth() / 2;
        int middleY = canvas.getHeight() / 2;
        canvas.drawCircle(middleX, middleY, radius, backgroundPaint);

        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = (int) Math.round(h / 2.1);
    }
}
