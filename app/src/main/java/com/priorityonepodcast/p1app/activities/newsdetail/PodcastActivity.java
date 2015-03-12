package com.priorityonepodcast.p1app.activities.newsdetail;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.priorityonepodcast.p1app.R;
import com.priorityonepodcast.p1app.activities.MenuUtil;
import com.priorityonepodcast.p1app.model.NewsItem;

import java.util.concurrent.atomic.AtomicReference;


public class PodcastActivity extends ActionBarActivity {

    public static final AtomicReference<NewsItem> HOLDER = new AtomicReference<>();

    public static final String NEWS_ITEM_ID = "news_item_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

        final NewsItem ni = HOLDER.get();
        if (ni != null) {
            TextView tv = (TextView) findViewById(R.id.lbl_title);
            tv.setText(ni.getTitle());

            tv = (TextView) findViewById(R.id.lbl_pubDate);
            tv.setText(ni.getPublicationDesc());

            tv = (TextView) findViewById(R.id.lbl_content);
            tv.setText(ni.getDescription());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_podcast, menu);
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
}
