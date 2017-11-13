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

import android.support.annotation.Nullable;

/**
 * Class that represents an CalendarEvent
 *
 * @author Tim
 */
public class CalendarEvent {

    private long id = -1;
    private String description;
    private String smallDescription;
    private Date date;

    /**
     * Constructor to initialize CalendarEvent
     * @param description The description for the event
     * @param smallDescription The shortened description for the event
     * @param date The date of the event
     */
    public CalendarEvent(String description, @Nullable String smallDescription, Date date) {
        this.description = description;
        this.smallDescription = smallDescription;
        this.date = date;
    }

    /**
     * Constructor to initialize CalendarEvent.
     * @param id
     * @param description The description for the event
     * @param smallDescription The shortened description for the event
     * @param date The date of the event
     */
    protected CalendarEvent(long id, String description, String smallDescription, Date date) {
        this.id = id;
        this.description = description;
        this.smallDescription = smallDescription;
        this.date = date;
    }

    /**
     * Gets the description of the event
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the shortened description of the event
     * @return the shortened description
     */
    public String getSmallDescription() {
        return smallDescription;
    }

    /**
     * Gets the {@link Date} of the event
      * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Gets the id of the event
     * @return the id
     */
    protected long getId() {
        return id;
    }

    /**
     * Sets the id of the event
     * @param id the id
     */
    protected void setId(long id) {
        this.id = id;
    }
}
