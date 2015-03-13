package com.priorityonepodcast.p1app.tasks.feed;

import com.priorityonepodcast.p1app.model.CalendarEvent;
import com.priorityonepodcast.p1app.model.NewsItem;
import com.priorityonepodcast.p1app.tasks.ExceptionListener;

import java.util.List;

/**
 * Created by hjones on 2015-03-12.
 */
public interface CalendarEventListener extends ExceptionListener {
    void notifyCalendarEventItems(List<CalendarEvent> list);
}
