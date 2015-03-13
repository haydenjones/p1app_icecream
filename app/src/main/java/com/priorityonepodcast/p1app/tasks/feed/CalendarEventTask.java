package com.priorityonepodcast.p1app.tasks.feed;

import android.os.AsyncTask;
import android.util.Log;

import com.priorityonepodcast.p1app.managers.ShowsMgr;
import com.priorityonepodcast.p1app.model.CalendarEvent;
import com.priorityonepodcast.p1app.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjones on 2015-03-11.
 */
public class CalendarEventTask extends AsyncTask<Void, Void, CalendarEventListener> {
    private final CalendarEventListener activity;

    private final List<CalendarEvent> list = new ArrayList<>();
    private volatile Exception thrown = null;

    public CalendarEventTask(CalendarEventListener gui) {
        super();
        activity = gui;
    }

    @Override
    protected CalendarEventListener doInBackground(Void... params) {
        Log.i("tag", "msg");

        try {
            ShowsMgr mgr = new ShowsMgr();
            List<CalendarEvent> items = mgr.getCalendarEvents();
            list.addAll(items);
        }
        catch (Exception e) {
            Log.e("", "", e);
            thrown = e;
        }
        return activity;
    }

    @Override
    protected void onPostExecute(CalendarEventListener gui) {
        Log.i("tag", "onPostExecute " + gui);
        if (thrown != null) {
            gui.notifyException("FeedTask", thrown);
        }
        else {
            gui.notifyCalendarEventItems(list);
        }
    }
}
