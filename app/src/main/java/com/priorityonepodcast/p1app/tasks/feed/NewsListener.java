package com.priorityonepodcast.p1app.tasks.feed;

import com.priorityonepodcast.p1app.model.NewsItem;
import com.priorityonepodcast.p1app.tasks.ExceptionListener;

import java.util.List;

/**
 * Created by hjones on 2015-03-12.
 */
public interface NewsListener extends ExceptionListener {
    void notifyNewsItems(List<NewsItem> list);
}
