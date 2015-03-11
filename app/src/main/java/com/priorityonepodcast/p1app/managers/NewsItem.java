package com.priorityonepodcast.p1app.managers;

import java.util.HashMap;
import java.util.Map;

public class NewsItem {
    static Map<String, XmlFields> TAG_MAP = new HashMap<>();

    public enum XmlFields {
        ITEM("item") {
            @Override
            void apply2(Builder b, String value) {
            }
        },
        TITLE("title") {
            @Override
            void apply2(Builder b, String value) {
                b.title(value);
            }
        },
        LINK("link") {
            @Override
            void apply2(Builder b, String value) {
                b.link(value);
            }
        };

        private final String qName;

        XmlFields(String xmlFieldName) {
            qName = xmlFieldName;
        }

        abstract void apply2(Builder b, String content);

        boolean apply(Builder b, String content) {
            apply2(b, content);
            return (this == ITEM);
        }
    }

    static {
        for (XmlFields field : XmlFields.values()) {
            TAG_MAP.put(field.qName, field);
        }
    }

    public static class Builder {
        private String title = "";
        private String link = "";

        public void reset() {
            title = "";
            link = "";
        }

        public Builder() {
            super();
        }

        public void link(String value) {
            link = value;
        }

        public void title(String value) {
            title = value;
        }

        public boolean endTag(String qName, StringBuilder builder) {
            XmlFields field = TAG_MAP.get(qName);
            if (field == null) {
                return false;
            }

            return field.apply(this, builder.toString().trim());
        }

        public NewsItem build() {
            return new NewsItem(this);
        }
    }

    // --- Constants and Variables

    private final String title;
    private final String link;

    // --- Constructor and Initialization Methods

    private NewsItem(Builder b) {
        super();
        title = b.title;
        link = b.link;
    }

    // --- Core and Helper Methods
    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods

    @Override
    public String toString() {
        return String.format("%s %s", title, link);
    }
}
