package com.priorityonepodcast.p1app.activities.news;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.priorityonepodcast.p1app.R;
import com.priorityonepodcast.p1app.activities.news.MenuUtil;
import com.priorityonepodcast.p1app.activities.news.NewsItemArrayAdapter;
import com.priorityonepodcast.p1app.model.NewsItem;
import com.priorityonepodcast.p1app.tasks.feed.FeedTask;
import com.priorityonepodcast.p1app.tasks.feed.NewsListener;

import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends ActionBarActivity implements NewsListener {

    private volatile ArrayAdapter<NewsItem> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ListView listView = (ListView) findViewById(R.id.listview_news);
        adapter = new NewsItemArrayAdapter(this, new ArrayList<NewsItem>());
        listView.setAdapter(adapter);
        AdapterView.OnItemClickListener listener = (AdapterView.OnItemClickListener) adapter;
        listView.setOnItemClickListener(listener);
        sendMessage(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
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

    public void sendMessage(View view) {
        FeedTask task = new FeedTask(this);
        task.execute();
    }

    public void notifyException(String ctx, Exception e) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
    }

    public void notifyNewsItems(List<NewsItem> items) {
        if (adapter != null) {
            adapter.clear();
            adapter.addAll(items);
            adapter.notifyDataSetChanged();
        }
    }
}
