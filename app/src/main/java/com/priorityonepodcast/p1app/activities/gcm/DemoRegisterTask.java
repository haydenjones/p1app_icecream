package com.priorityonepodcast.p1app.activities.gcm;

import android.os.AsyncTask;
import android.util.Log;

import com.priorityonepodcast.p1app.managers.ShowsMgr;
import com.priorityonepodcast.p1app.model.CalendarEvent;
import com.priorityonepodcast.p1app.tasks.feed.CalendarEventListener;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 * Created by hjones on 2015-03-22.
 */
public class DemoRegisterTask extends AsyncTask<Void, Void, Void> {
    private final String path;
    public DemoRegisterTask(String path) {
        super();
        this.path = path;
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.i("tag", "msg");

        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(path).build();
            Response response = client.newCall(request).execute();
            Log.i("drt", "" + response.isSuccessful());
        }
        catch (Exception e) {
            Log.e("drt", "", e);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void v) {
        Log.i("drt", "onPostExecute ");
    }

}
