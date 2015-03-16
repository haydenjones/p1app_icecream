package com.priorityonepodcast.p1app.activities.coe;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.priorityonepodcast.p1app.PodcastArrayAdapter;
import com.priorityonepodcast.p1app.R;
import com.priorityonepodcast.p1app.activities.MenuUtil;
import com.priorityonepodcast.p1app.model.CalendarEvent;
import com.priorityonepodcast.p1app.model.NewsItem;
import com.priorityonepodcast.p1app.tasks.feed.CalendarEventListener;
import com.priorityonepodcast.p1app.tasks.feed.CalendarEventTask;
import com.priorityonepodcast.p1app.tasks.feed.PodcastTask;

import java.util.ArrayList;
import java.util.List;


public class CalendarActivity extends ActionBarActivity implements CalendarEventListener {

    private volatile ArrayAdapter<CalendarEvent> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ListView listView = (ListView) findViewById(R.id.listview_events);
        adapter = new CalendarEventArrayAdapter(this, new ArrayList<CalendarEvent>());
        listView.setAdapter(adapter);

        onClick(listView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean found = MenuUtil.startActivity(this, item);
        if (found) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void notifyException(String ctx, Exception e) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
    }

    public void notifyCalendarEventItems(List<CalendarEvent> items) {
        String msg = items.size() + " events notified to " + adapter;
        Log.i(msg, msg);
        if (adapter != null) {
            adapter.clear();
            adapter.addAll(items);
            adapter.notifyDataSetChanged();
        }
    }

    public void onClick(View view) {
        Log.i("tag", "msg");
        CalendarEventTask task = new CalendarEventTask(this);
        task.execute();
    }
}
