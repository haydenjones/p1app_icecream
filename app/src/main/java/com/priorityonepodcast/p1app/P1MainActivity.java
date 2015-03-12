package com.priorityonepodcast.p1app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.priorityonepodcast.p1app.activities.MenuUtil;
import com.priorityonepodcast.p1app.tasks.feed.FeedTask;

import java.util.List;

import com.priorityonepodcast.p1app.model.NewsItem;
import com.priorityonepodcast.p1app.tasks.feed.NewsListener;


public class P1MainActivity extends ActionBarActivity implements NewsListener {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_p1_main);
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

    public void sendMessage(View view) {
        Log.i("tag", "msg");
        FeedTask task = new FeedTask(this);
        task.execute();
    }

    public void notifyException(String ctx, Exception e) {
        Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
    }

    public void notifyNewsItems(List<NewsItem> items) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        EditText toText = (EditText) findViewById(R.id.to_message);

        editText.setText(items.size() + " loaded", TextView.BufferType.NORMAL);
        String toTxt = "";
        if (!items.isEmpty()) {
            toTxt = items.get(0).toString();
        }
        toText.setText(toTxt, TextView.BufferType.NORMAL);
    }
}
