package com.priorityonepodcast.p1app.managers;

import com.priorityonepodcast.p1app.model.SummaryPodcastItem;
import com.priorityonepodcast.p1app.util.Close;

import junit.framework.TestCase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by hjones on 2015-03-06.
 */
public class RetrieveShows_Test extends TestCase {

    public void testXmlParsing() throws IOException {
        InputStream stream = null;
        File f = new File(".");
        System.out.println(f.getCanonicalPath());
        try {
            stream = new FileInputStream("/feeds.xml");
            RetrieveShows rs = new RetrieveShows(stream);
            List<SummaryPodcastItem> list = rs.call();
            TestCase.assertFalse("We should have some items in our list", list.isEmpty());
        }
        finally {
            Close.safely(stream);
        }
    }
}
