package com.priorityonepodcast.p1app.activities.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.priorityonepodcast.p1app.R;
import com.priorityonepodcast.p1app.model.NewsItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by hjones on 2015-03-12.
 */
public class NewsItemArrayAdapter extends ArrayAdapter<NewsItem> implements AdapterView.OnItemClickListener {
    private final List<NewsItem> newsItem;
    private final Context context;

    public NewsItemArrayAdapter(Context ctx, List<NewsItem> items) {
        super(ctx, R.layout.news_summary, items);
        newsItem = items;
        context = ctx;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.news_summary, parent, false);

        TextView tvTitle = (TextView) rowView.findViewById(R.id.lineTitle);
        TextView tvTeaser = (TextView) rowView.findViewById(R.id.linesDesc);
        TextView tvPubDate = (TextView) rowView.findViewById(R.id.linePubDate);

        String ttl = "";
        String tease = "";
        String pub = "";

        NewsItem ni = null;
        if ((position >= 0) && (position < newsItem.size())) {
            ni = newsItem.get(position);
            ttl = ni.getTitle();
            pub = ni.getPublicationDesc();
            tease = ni.getDescription();
        }

        tvTitle.setText(ttl, TextView.BufferType.NORMAL);
        tvTeaser.setText(tease, TextView.BufferType.NORMAL);
        tvPubDate.setText(pub, TextView.BufferType.NORMAL);

        return rowView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, position + " " + id, Toast.LENGTH_LONG).show();
    }
}
