package com.priorityonepodcast.p1app.util;

import java.io.InputStream;

/**
 * Created by hjones on 2015-03-06.
 */
public class Close {
    private Close() {
        super();
    }

    public static void safely(InputStream closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        }
        catch (Exception e) {
            // TODO: Add logging here
        }
    }
}
