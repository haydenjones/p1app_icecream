package com.priorityonepodcast.p1app.activities.newsdetail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;

import com.squareup.okhttp.OkHttpClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Created by hjones on 2015-03-18.
 */
public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
    UrlDrawable urlDrawable;
    View container;

    public ImageGetterAsyncTask(UrlDrawable d, View c) {
        urlDrawable = d;
        container = c;
    }

    @Override
    protected Drawable doInBackground(String... params) {
        String source = params[0];
        return fetchDrawable(source);
    }

    @Override
    protected void onPostExecute(Drawable result) {
        urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(), 0 + result.getIntrinsicHeight());
        urlDrawable.drawable = result;
        container.invalidate();
    }

    public Drawable fetchDrawable(String urlString) {
        try {
            InputStream is = fetch(urlString);
            Drawable drawable = Drawable.createFromStream(is, "src");
            drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(), 0 + drawable.getIntrinsicHeight());
            return drawable;
        }
        catch (Exception e) {
            return null;
        }
    }

    private InputStream fetch(String urlString) throws MalformedURLException, IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlString);
        HttpResponse response = httpClient.execute(request);
        return response.getEntity().getContent();
    }
}
