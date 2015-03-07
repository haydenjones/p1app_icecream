package com.priorityonepodcast.p1app.model;

import java.util.Collections;
import java.util.List;

public class SummaryPodcastItem {
    // --- Constants and Variables

    public static final List<SummaryPodcastItem> EMPTY_LIST = Collections.emptyList();

    private final String title;
    private final String summary;
    private final long millis;

    // --- Constructor and Initialization Methods

    public SummaryPodcastItem(String ttl, String sum, long time) {
        super();
        title = ttl;
        summary = sum;
        millis = time;
    }

    // --- Core and Helper Methods
    // --- Getter and Setter Methods

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public long getMillis() {
        return millis;
    }

    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods
}
