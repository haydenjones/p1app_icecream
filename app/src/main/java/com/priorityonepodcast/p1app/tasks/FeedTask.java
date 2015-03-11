package com.priorityonepodcast.p1app.tasks;

import android.os.AsyncTask;

import com.priorityonepodcast.p1app.P1MainActivity;
import com.priorityonepodcast.p1app.managers.ShowsMgr;

import java.util.ArrayList;
import java.util.List;

import com.priorityonepodcast.p1app.model.NewsItem;

/**
 * Created by hjones on 2015-03-11.
 */
public class FeedTask extends AsyncTask<Void, Void, P1MainActivity> {
    private final List<NewsItem> list = new ArrayList<>();
    private volatile Exception thrown = null;

    @Override
    protected P1MainActivity doInBackground(Void... params) {
        try {
            ShowsMgr mgr = new ShowsMgr();
            List<NewsItem> items = mgr.getShowSummaries();
            list.addAll(items);
        }
        catch (Exception e) {
            thrown = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(P1MainActivity gui) {
        gui.onNewsItems(list, thrown);
    }
}
