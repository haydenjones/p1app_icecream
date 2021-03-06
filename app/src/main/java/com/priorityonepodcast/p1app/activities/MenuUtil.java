package com.priorityonepodcast.p1app.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import com.priorityonepodcast.p1app.activities.coe.CalendarActivity;
import com.priorityonepodcast.p1app.activities.gcm.DemoActivity;
import com.priorityonepodcast.p1app.P1MainActivity;
import com.priorityonepodcast.p1app.R;
import com.priorityonepodcast.p1app.activities.news.NewsActivity;

/**
 * Created by hjones on 2015-03-12.
 */
public class MenuUtil {
    private MenuUtil() {
        super();
    }

    public static boolean startActivity(ActionBarActivity activity, MenuItem item) {
        final int id = item.getItemId();
        Log.d("menu", "" + id);

        final Intent intent;

        switch (id) {
            case R.id.action_podcast:
                intent = new Intent(activity, P1MainActivity.class);
                break;
            case R.id.action_events:
                intent = new Intent(activity, CalendarActivity.class);
                break;
            case R.id.action_news:
                intent = new Intent(activity, NewsActivity.class);
                break;
            case R.id.action_push:
                intent = new Intent(activity, DemoActivity.class);
                break;
            case R.id.action_social:
                intent = new Intent(activity, SocialActivity.class);
                break;
        default:
        intent = null;
        break;
        }

        if (intent != null) {
            activity.startActivity(intent);
            return true;
        }
        return false;
    }

}
