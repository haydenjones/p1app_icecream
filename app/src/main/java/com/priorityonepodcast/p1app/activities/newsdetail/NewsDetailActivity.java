package com.priorityonepodcast.p1app.activities.newsdetail;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.priorityonepodcast.p1app.R;
import com.priorityonepodcast.p1app.model.NewsItem;

import java.util.concurrent.atomic.AtomicReference;


public class NewsDetailActivity extends ActionBarActivity {
    public static final AtomicReference<NewsItem> HOLDER = new AtomicReference<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        final TextView tv = (TextView) findViewById(R.id.textView_news_detail);
        NewsItem ni = HOLDER.get();
        if (ni != null) {
//            HttpImageGetter hig = new HttpImageGetter(this, tv, ni.getContentEncoded());
//            tv.setText(Html.fromHtml(ni.getContentEncoded(), hig, null));

            UrlImageParser p = new UrlImageParser(tv, this);
            Spanned htmlSpan = Html.fromHtml(ni.getContentEncoded(), p, null);
            tv.setText(htmlSpan);
        }
        else {
            tv.setText("Nothing in Holder!", TextView.BufferType.NORMAL);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_detail, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
