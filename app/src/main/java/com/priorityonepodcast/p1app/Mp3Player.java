package com.priorityonepodcast.p1app;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


public class Mp3Player extends ActionBarActivity {

    private Button buttonPlayStop;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_player);
        initViews();
    }


    // This method set the setOnClickListener and method for it (buttonClick())
    private void initViews() {
        buttonPlayStop = (Button) findViewById(R.id.ButtonPlayStop);
        buttonPlayStop.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {buttonClick();}});

        mediaPlayer = MediaPlayer.create(this, R.raw.pca15b);
        Log.i("mp " + mediaPlayer, "mp " + mediaPlayer);
        seekBar = (SeekBar) findViewById(R.id.SeekBar01);
        seekBar.setMax(mediaPlayer.getDuration());
        seekBar.setOnTouchListener(new View.OnTouchListener() {@Override public boolean onTouch(View v, MotionEvent event) {
            seekChange(v);
            return false; }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mp3_player, menu);
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

}
