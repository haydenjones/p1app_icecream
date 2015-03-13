package com.priorityonepodcast.p1app.activities.newsdetail;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;

import com.priorityonepodcast.p1app.R;

/**
 * Created by hjones on 2015-03-13.
 */
public class ImageGetter implements Html.ImageGetter {
    private final ActionBarActivity activity;

    ImageGetter(ActionBarActivity aba) {
        super();
        activity = aba;
    }

    public Drawable getDrawable(String source) {
        int id = R.drawable.banner;
        if (source.equals("hughjackman.jpg")) {
            // id = R.drawable.hughjackman;
        }
        else {
            return null;
        }

        Drawable d = activity.getResources().getDrawable(id);
        d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
        return d;
    }
}

