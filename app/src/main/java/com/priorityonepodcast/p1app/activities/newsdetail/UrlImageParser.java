package com.priorityonepodcast.p1app.activities.newsdetail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;

/**
 * Created by hjones on 2015-03-18.
 */
public class UrlImageParser implements Html.ImageGetter {
    private final Context c;
    private final View container;

    public UrlImageParser(View t, Context c) {
        this.c = c;
        this.container = t;
    }

    public Drawable getDrawable(String source) {
        UrlDrawable urlDrawable = new UrlDrawable();

        ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(urlDrawable, container);
        asyncTask.execute(source);

        return urlDrawable;
    }
}
