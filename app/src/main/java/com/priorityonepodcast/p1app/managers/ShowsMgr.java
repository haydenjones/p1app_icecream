package com.priorityonepodcast.p1app.managers;

import com.priorityonepodcast.p1app.util.Close;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.priorityonepodcast.p1app.model.NewsItem;

/**
 * Created by hjones on 2015-03-06.
 */
public class ShowsMgr {
    public static final String FEED_URL = "http://priorityonepodcast.com/feed/";

    public ShowsMgr() {
        super();
    }

    public List<NewsItem> getShowSummaries() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(FEED_URL).build();

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
}
