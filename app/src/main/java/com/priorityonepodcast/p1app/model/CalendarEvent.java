package com.priorityonepodcast.p1app.model;

/**
 * Created by hjones on 2015-03-13.
 */
public class CalendarEvent {
    public static class Builder {
        public Builder() {
            super();
        }

        public void reset() {

        }

        public boolean endElement(String qName, StringBuilder sb) {
            return false;
        }

        public CalendarEvent build() {
            return new CalendarEvent(this);
        }
    }

    private CalendarEvent(Builder b) {
        super();
    }
}
