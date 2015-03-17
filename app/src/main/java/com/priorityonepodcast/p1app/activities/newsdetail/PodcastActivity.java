package com.priorityonepodcast.p1app.activities.newsdetail;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.priorityonepodcast.p1app.R;
import com.priorityonepodcast.p1app.activities.MenuUtil;
import com.priorityonepodcast.p1app.model.NewsItem;

import java.util.concurrent.atomic.AtomicReference;


public class PodcastActivity extends ActionBarActivity {

    public static final AtomicReference<NewsItem> HOLDER = new AtomicReference<>();

    public static final String NEWS_ITEM_ID = "news_item_id";

    private Button buttonPlayStop;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;

    private final Handler handler = new Handler();

    private String link = "";
    private String mediaUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast);

        final NewsItem ni = HOLDER.get();
        if (ni != null) {
            TextView tv = (TextView) findViewById(R.id.lbl_title);
            tv.setText(ni.getTitle());

            tv = (TextView) findViewById(R.id.lbl_pubDate);
            tv.setText(ni.getPublicationDesc());

            tv = (TextView) findViewById(R.id.lbl_creator);
            tv.setText(ni.getCreator());

            tv = (TextView) findViewById(R.id.lbl_content);
            tv.setText(Html.fromHtml(ni.getContentEncoded(), new ImageGetter(this), null));

            link = ni.getLink();
            mediaUrl = ni.getEnlosureUrl();
        }
        else {
            link = "";
            mediaUrl = null;
        }

        initViews();
    }


    // This method set the setOnClickListener and method for it (buttonClick())
    private void initViews() {
        buttonPlayStop = (Button) findViewById(R.id.btn_playStop);
        buttonPlayStop.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {buttonClick();}});

        // mediaPlayer = MediaPlayer.create(this, R.raw.pca15b);
        Uri uri = Uri.parse(mediaUrl);
        if (uri != null) {
            mediaPlayer = MediaPlayer.create(this, uri);
            Log.i("mp " + mediaPlayer, "mp " + mediaPlayer);
            seekBar = (SeekBar) findViewById(R.id.seekbar_podcast);
            seekBar.setMax(mediaPlayer.getDuration());
            seekBar.setOnTouchListener(new View.OnTouchListener() {@Override public boolean onTouch(View v, MotionEvent event) {
                                           seekChange(v);
                                           return false;
                                       }
                                       }
            );
        }
    }

    public void startPlayProgressUpdater() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        if (mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                public void run() {
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification,1000);
        }else{
            mediaPlayer.pause();
            buttonPlayStop.setText(getString(R.string.play_str));
            seekBar.setProgress(0);
        }
    }

    // This is event handler thumb moving event
    private void seekChange(View v){
        if(mediaPlayer.isPlaying()){
            SeekBar sb = (SeekBar)v;
            mediaPlayer.seekTo(sb.getProgress());
        }
    }

    // This is event handler for buttonClick event
    private void buttonClick(){
        if (buttonPlayStop.getText() == getString(R.string.play_str)) {
            buttonPlayStop.setText(getString(R.string.pause_str));
            try{
                mediaPlayer.start();
                startPlayProgressUpdater();
            }catch (IllegalStateException e) {
                mediaPlayer.pause();
            }
        }else {
            buttonPlayStop.setText(getString(R.string.play_str));
            mediaPlayer.pause();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_podcast, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean found = MenuUtil.startActivity(this, item);
        if (found) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickTitle(View view) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
            startActivity(i);
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No Application can handle this request, please install a web browser.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
