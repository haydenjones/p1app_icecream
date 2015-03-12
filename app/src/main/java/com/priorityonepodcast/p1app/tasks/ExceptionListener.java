package com.priorityonepodcast.p1app.tasks;

/**
 * Created by hjones on 2015-03-12.
 */
public interface ExceptionListener {
    void notifyException(String context, Exception e);
}
