package com.priorityonepodcast.p1app.managers;

import com.priorityonepodcast.p1app.util.Close;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.priorityonepodcast.p1app.model.NewsItem;

/**
 * Created by hjones on 2015-03-06.
 */
public class ShowsMgr {
    public static final String FEED_URL = "http://priorityonepodcast.com/feed/";
    public static final String PODCAST_URL = FEED_URL + "/podcast/";

    private List<NewsItem> fakes = new ArrayList<>();

    public ShowsMgr() {
        super();
    }

    public void fakeIt() {
        fakes.clear();

        NewsItem.Builder nib = new NewsItem.Builder();
        nib.link("#");
        nib.description("Description...");

        nib.title("Title1");
        nib.pubDate(java.sql.Date.valueOf("2015-03-01"));
        fakes.add(nib.build());

        nib.title("Title2");
        nib.pubDate(java.sql.Date.valueOf("2015-03-02"));
        fakes.add(nib.build());

        nib.title("Title3");
        nib.pubDate(java.sql.Date.valueOf("2015-03-03"));
        fakes.add(nib.build());
    }

    public List<NewsItem> getPodcasts() throws IOException {
        return getNews(PODCAST_URL);
    }

    List<NewsItem> getNews(String feedUrl) throws IOException {
        if (!fakes.isEmpty()) {
            return fakes;
        }

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(feedUrl).build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        InputStream stream = null;

        try {
            stream = new ByteArrayInputStream(response.body().bytes());
            NewsHandler task = new NewsHandler(stream);
            return task.call();
        }
        catch (Exception e) {
            throw new IOException(e);
        }
        finally {
            Close.safely(stream);
        }
    }

    public List<NewsItem> getShowSummaries() throws IOException {
        return getNews(FEED_URL);
    }
}
