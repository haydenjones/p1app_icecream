package com.priorityonepodcast.p1app.activities.newsdetail;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.priorityonepodcast.p1app.R;
import com.priorityonepodcast.p1app.activities.MenuUtil;


public class PodcastActivity extends ActionBarActivity {

    public static final String NEWS_ITEM_ID = "news_item_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

        String show = "?";

        if (savedInstanceState == null) {
            Bundle bundle = this.getIntent().getExtras();
            if (bundle == null) {
                show = "No Bundle :-(";
            }
            else {
                show = bundle.getString(NEWS_ITEM_ID, "Unknown");
            }
        }
        else {
            savedInstanceState.getString(NEWS_ITEM_ID, "n/a");
        }

        TextView tv = (TextView) findViewById(R.id.hayden);
        tv.setText(show);
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
