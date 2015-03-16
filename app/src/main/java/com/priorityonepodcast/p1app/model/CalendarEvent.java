package com.priorityonepodcast.p1app.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hjones on 2015-03-13.
 */
public class CalendarEvent {
    public static enum XmlFields {
        ENTRY("entry") {
            @Override
            void apply2(Builder b, String value) {

            }
        },
        TITLE("title") {
            @Override
            void apply2(Builder b, String value) {
                b.title(value);
            }
        };

        private final String qName;

        XmlFields(String qn) {
            qName = qn;
        }

        abstract void apply2(Builder b, String value);

        public boolean apply(Builder b, String value) {
            apply2(b, value);
            return (this == ENTRY);
        }
    }

    static final Map<String, XmlFields> MAP_FIELDS = new HashMap<>();
    static {
        for (XmlFields f : XmlFields.values()) {
            MAP_FIELDS.put(f.qName, f);
        }
    }

    public static class Builder {
        public Builder() {
            super();
        }

        private String title = "";
        private java.util.Date startTime = null;
        private java.util.Date endTime = null;
        private String uid = "";
        private String description = "";
        private String location = "";
        private String status = "";

        public void reset() {
            title = "";
            startTime = null;
            endTime = null;
            uid = "";
            description = "";
            location = "";
            status = "";
        }

        public void startTime(java.util.Date d) {
            startTime = d;
        }

        public void endTime(Date d) {
            endTime = d;
        }

        public void title(String s) {
            title = s;
        }

        public void uid(String s) {
            uid = s;
        }

        public void description(String s) {
            description = s;
        }

        public void location(String s) {
            location = s;
        }

        public void status(String s) {
            status = s;
        }

        public boolean endElement(String qName, StringBuilder sb) {
            XmlFields xf = MAP_FIELDS.get(qName);
            if (xf == null) {
                return false;
            }
            return xf.apply(this, sb.toString().trim());
        }

        public CalendarEvent build() {
            return new CalendarEvent(this);
        }
    }

    private final String title;
    private final java.util.Date startTime;
    private final java.util.Date endTime;
    private final String uid;
    private final String description;
    private final String location;
    private final String status;

    private CalendarEvent(Builder b) {
        super();
        title = b.title;
        startTime = b.startTime;
        endTime = b.endTime;
        uid = b.uid;
        description = b.description;
        location = b.location;
        status = b.status;
    }


    public String getTitle() {
        return title;
    }

    public java.util.Date getStartTime() {
        return startTime;
    }

    public java.util.Date getEndTime() {
        return endTime;
    }

    public String getUid() {
        return uid;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Event: " + title + " " + startTime;
    }
}
