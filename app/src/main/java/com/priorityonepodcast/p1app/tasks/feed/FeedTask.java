package com.priorityonepodcast.p1app.tasks.feed;

import android.os.AsyncTask;
import android.util.Log;

import com.priorityonepodcast.p1app.P1MainActivity;
import com.priorityonepodcast.p1app.managers.ShowsMgr;

import java.util.ArrayList;
import java.util.List;

import com.priorityonepodcast.p1app.model.NewsItem;

/**
 * Created by hjones on 2015-03-11.
 */
public class FeedTask extends AsyncTask<Void, Void, NewsListener> {
    private final NewsListener activity;

    private final List<NewsItem> list = new ArrayList<>();
    private volatile Exception thrown = null;

    public FeedTask(NewsListener gui) {
        super();
        activity = gui;
    }

    @Override
    protected NewsListener doInBackground(Void... params) {
        Log.i("tag", "msg");

        try {
            ShowsMgr mgr = new ShowsMgr();
            mgr.fakeIt();
            List<NewsItem> items = mgr.getShowSummaries();
            list.addAll(items);
        }
        catch (Exception e) {
            Log.e("", "", e);
            thrown = e;
        }
        return activity;
    }

    @Override
    protected void onPostExecute(NewsListener gui) {
        Log.i("tag", "onPostExecute " + gui);
        if (thrown != null) {
            gui.notifyException("FeedTask", thrown);
        }
        else {
            gui.notifyNewsItems(list);
        }
    }
}
