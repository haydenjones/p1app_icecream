package com.priorityonepodcast.p1app.managers;

import com.priorityonepodcast.p1app.model.SummaryPodcastItem;
import com.priorityonepodcast.p1app.util.Close;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by hjones on 2015-03-06.
 */
public class ShowsMgr {
    private final File file;

    public ShowsMgr() {
        this(null);
    }

    ShowsMgr(File f) {
        super();
        file = f;
    }

    public List<SummaryPodcastItem> getShowSummaries() throws IOException {
        if (file == null) {
            return SummaryPodcastItem.EMPTY_LIST;
        }
        else {
            InputStream stream = null;
            try {
                stream = new FileInputStream(file);
                RetrieveShows task = new RetrieveShows(stream);
                return task.call();
            }
            finally {
                Close.safely(stream);
            }
        }
    }
}
