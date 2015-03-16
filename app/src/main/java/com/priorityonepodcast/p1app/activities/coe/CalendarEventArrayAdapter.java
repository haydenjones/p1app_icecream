package com.priorityonepodcast.p1app.activities.coe;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.priorityonepodcast.p1app.R;
import com.priorityonepodcast.p1app.model.CalendarEvent;
import com.priorityonepodcast.p1app.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjones on 2015-03-16.
 */
public class CalendarEventArrayAdapter extends ArrayAdapter<CalendarEvent> {
    private final List<CalendarEvent> calendarEvents;
    private final ActionBarActivity context;

    public CalendarEventArrayAdapter(ActionBarActivity gui, List<CalendarEvent> events) {
        super(gui, R.layout.news_summary, events);
        calendarEvents = events;
        context = gui;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("", "inflate.pos " + position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.news_summary, parent, false);

        TextView tvTitle = (TextView) rowView.findViewById(R.id.lineTitle);
        TextView tvTeaser = (TextView) rowView.findViewById(R.id.linesDesc);
        TextView tvPubDate = (TextView) rowView.findViewById(R.id.linePubDate);

        String ttl = "";
        String tease = "";
        String pub = "";

        CalendarEvent ni = null;
        if ((position >= 0) && (position < calendarEvents.size())) {
            ni =  calendarEvents.get(position);
            ttl = ni.getTitle();
            pub = "" + ni.getStartTime();
            tease = ni.getDescription();
        }

        tvTitle.setText(ttl, TextView.BufferType.NORMAL);
        tvTeaser.setText(tease, TextView.BufferType.NORMAL);
        tvPubDate.setText(pub, TextView.BufferType.NORMAL);

        return rowView;
    }
}
