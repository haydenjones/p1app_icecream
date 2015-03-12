package com.priorityonepodcast.p1app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.priorityonepodcast.p1app.model.NewsItem;
import com.priorityonepodcast.p1app.tasks.feed.FeedTask;
import com.priorityonepodcast.p1app.tasks.feed.NewsListener;

import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends ActionBarActivity implements NewsListener {

    private volatile ArrayAdapter<String> adapter = null;
    private final List<String> newsItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ListView listView = (ListView) findViewById(R.id.listview_news);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newsItems);
        listView.setAdapter(adapter);
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
        Log.i("tag", "msg");
        FeedTask task = new FeedTask(this);
        task.execute();
    }

    public void notifyException(String ctx, Exception e) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
    }

    public void notifyNewsItems(List<NewsItem> items) {
        if (adapter != null) {
            newsItems.clear();
            for (NewsItem ni : items) {
                newsItems.add(ni.toString());
            }
            adapter.notifyDataSetChanged();
        }
    }
}
