package com.priorityonepodcast.p1app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.priorityonepodcast.p1app.activities.MenuUtil;
import com.priorityonepodcast.p1app.activities.news.NewsItemArrayAdapter;
import com.priorityonepodcast.p1app.tasks.feed.FeedTask;

import java.util.ArrayList;
import java.util.List;

import com.priorityonepodcast.p1app.model.NewsItem;
import com.priorityonepodcast.p1app.tasks.feed.NewsListener;
import com.priorityonepodcast.p1app.tasks.feed.PodcastTask;


public class P1MainActivity extends ActionBarActivity implements NewsListener {

    private volatile ArrayAdapter<NewsItem> adapter = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_p1_main);

            ListView listView = (ListView) findViewById(R.id.listview_podcasts);
            adapter = new PodcastArrayAdapter(this, new ArrayList<NewsItem>());
            listView.setAdapter(adapter);
            AdapterView.OnItemClickListener listener = (AdapterView.OnItemClickListener) adapter;
            listView.setOnItemClickListener(listener);
            onClick(listView);
        }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_p1_main, menu);
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

    public void onClick(View view) {
        Log.i("tag", "msg");
        PodcastTask task = new PodcastTask(this);
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
