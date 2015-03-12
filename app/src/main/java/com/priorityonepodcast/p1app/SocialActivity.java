package com.priorityonepodcast.p1app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.net.Uri;

import com.priorityonepodcast.p1app.managers.ShowsMgr;

public class SocialActivity extends ActionBarActivity {

    public void onClick(View view) {
        int id = view.getId();
        String link = null;

        switch (id) {
            case R.id.link_facebook:
                link = "http://facebook.com/priorityonepodcast";
                break;
            case R.id.link_twitter:
                link = "http://twitter.com/stopriorityone";
                break;
            case R.id.link_youtube:
                link = "http://youtube.com/p1network";
                break;
            default:
                link = null;
                break;
        }

        if (link != null) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_social, menu);
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
}
