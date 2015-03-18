package com.priorityonepodcast.p1app.activities.newsdetail;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by hjones on 2015-03-18.
 */
public class UrlDrawable extends BitmapDrawable {
    protected Drawable drawable = null;

    @Override
    public void draw(Canvas canvas) {
        if (drawable != null) {
            drawable.draw(canvas);
        }
    }
}
