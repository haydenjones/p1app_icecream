package com.priorityonepodcast.p1app.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.xml.sax.Attributes;

public class NewsItem {
    static Map<String, XmlFields> TAG_MAP = new HashMap<>();

    public enum XmlFields {
        ITEM("item") {
            @Override
            void apply2(Builder b, String value) {
            }
        },
        GUID("guid") {
            @Override
            void apply2(Builder b, String value) {
              b.guid(value);
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
        },
        PUB_DATE("pubDate") {
            @Override
            void apply2(Builder b, String value) {
                b.pubDate(value);
            }
        },
        CREATOR("dc:creator") {
            @Override
            void apply2(Builder b, String value) {
                b.creator(value);
            }
        },
        DESCRIPTION("description") {
            @Override
            void apply2(Builder b, String value) {
                b.description(value);
            }
        },
        CONTENT_ENCODED("content:encoded") {
            @Override
            void apply2(Builder b, String value) {
                b.contentEncoded(value);
            }
        },
        ENCLOSURE("enclosure") {
            @Override
            void apply2(Builder b, String value) {

            }

            @Override
            void applyStart(Builder b, String key, String value) {
                if ("url".equals(key)) {
                    b.enclosureUrl(value);
                }
            }
        };

        public final String qName;

        XmlFields(String xmlFieldName) {
            qName = xmlFieldName;
        }

        abstract void apply2(Builder b, String content);

        void applyStart(Builder b, String key, String value) {

        }

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
        // Wed, 11 Mar 2015 15:47:49 +0000
        private final SimpleDateFormat sdfPubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZZ", Locale.US);

        private String guid = "";
        private String creator = "";
        private String title = "";
        private String link = "";
        private long pubMillis = 0;
        private String description = "";
        private String enclosureUrl = "";
        private String contentEncoded = "";

        public void reset() {
            guid = "";
            creator = "";
            title = "";
            link = "";
            pubMillis = 0;
            description = "";
            enclosureUrl = "";
            contentEncoded = "";
        }

        public Builder() {
            super();
        }

        public void creator(String value) { creator = value; }

        public void contentEncoded(String value) { contentEncoded = value; }

        public void guid(String value) { guid = value; }

        public void enclosureUrl(String value) {
            enclosureUrl = value;
        }

        public void description(String value) {
            description = value;
        }

        public void pubDate(String value) {
            try {
                pubDate(sdfPubDate.parse(value));
            }
            catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        public void pubDate(java.util.Date date) {
            pubMillis = date.getTime();
        }

        public void link(String value) {
            link = value;
        }

        public void title(String value) {
            title = value;
        }

        public void startElement(String qName, Attributes attributes) {
            XmlFields field = TAG_MAP.get(qName);
            if (field != null) {
                int length = attributes.getLength();
                for (int i1=0; i1<length; i1++) {
                    String key = attributes.getLocalName(i1);
                    String value = attributes.getValue(i1);
                    field.applyStart(this, key, value);
                }
            }
        }

        public boolean endElement(String qName, StringBuilder builder) {
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

    private final String creator;
    private final String guid;
    private final String title;
    private final String link;
    private final long pubMillis;
    private final String description;
    private final String enclosureUrl;
    private final String contentEncoded;

    // --- Constructor and Initialization Methods

    private NewsItem(Builder b) {
        super();
        creator = b.creator;
        guid = b.guid;
        title = b.title;
        link = b.link;
        pubMillis = b.pubMillis;
        description = b.description;
        enclosureUrl = b.enclosureUrl;
        contentEncoded = b.contentEncoded;
    }

    // --- Core and Helper Methods
    // --- Getter and Setter Methods

    public String getCreator() {
        return creator;
    }

    public String getContentEncoded() {
        return contentEncoded;
    }

    public String getGuid() { return guid; }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getEnlosureUrl() {
        return enclosureUrl;
    }

    public String getLink() { return link; }

    public String getPublicationDesc() {
        return String.valueOf(new java.util.Date(pubMillis));
    }

    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods

    @Override
    public String toString() {
        java.sql.Date d = new java.sql.Date(pubMillis);
        return String.format("%s %s %s", d, title, link);
    }
}
