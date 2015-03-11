package com.priorityonepodcast.p1app;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.priorityonepodcast.p1app.managers.ShowsMgr;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;

import temp.NewsItem;


public class P1MainActivity extends ActionBarActivity {

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_podcast) {
            Intent intent = new Intent(this, P1MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_events) {
            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_news) {
            Intent intent = new Intent(this, NewsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_push) {
            Intent intent = new Intent(this, NotificationsActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_podcast) {
            Intent intent = new Intent(this, P1MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_social) {
            Intent intent = new Intent(this, SocialActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_message);
        EditText toText = (EditText) findViewById(R.id.to_message);

        try {
            ShowsMgr mgr = new ShowsMgr();
            List<NewsItem> items = mgr.getShowSummaries();
            editText.setText(items.size() + " loaded", TextView.BufferType.NORMAL);
            String toTxt = "";
            if (!items.isEmpty()) {
                toTxt = items.get(0).toString();
            }
            toText.setText(toTxt, TextView.BufferType.NORMAL);
        }
        catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
