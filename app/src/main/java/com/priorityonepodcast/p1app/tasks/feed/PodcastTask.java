package com.priorityonepodcast.p1app.tasks.feed;

import android.os.AsyncTask;
import android.util.Log;

import com.priorityonepodcast.p1app.managers.ShowsMgr;
import com.priorityonepodcast.p1app.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjones on 2015-03-11.
 */
public class PodcastTask extends AsyncTask<Void, Void, NewsListener> {
    private final NewsListener activity;

    private final List<NewsItem> list = new ArrayList<>();
    private volatile Exception thrown = null;

    public PodcastTask(NewsListener gui) {
        super();
        activity = gui;
    }

    @Override
    protected NewsListener doInBackground(Void... params) {
        Log.i("tag", "msg");

        try {
            ShowsMgr mgr = new ShowsMgr();
            List<NewsItem> items = mgr.getPodcasts();
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
