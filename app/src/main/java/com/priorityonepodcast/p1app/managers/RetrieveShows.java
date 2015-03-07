package com.priorityonepodcast.p1app.managers;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.priorityonepodcast.p1app.model.SummaryPodcastItem;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RetrieveShows extends DefaultHandler {
    // --- Constants and Variables

    static final String QNAME_ITEM = "item";

    public enum Tags {
        /**
         *
         */
        TITLE("title") {
            @Override
            void set(String value, RetrieveShows show) {
                show.title = value;
            }
        },
        /**
         *
         */
        PUB_DATE("pubDate") {
            @Override
            void set(String value, RetrieveShows show) throws ParseException {
                System.out.println(value);
                show.pubDate = show.parseDate(value);
            }
        },
        /**
         *
         */
        SUBTITLE("itunes:subtitle") {
            @Override
            void set(String value, RetrieveShows show) {
                show.subtitle = value;
            }
        };

        final String qName;

        Tags(String q) {
            qName = q;
        }

        abstract void set(String value, RetrieveShows show) throws ParseException;
    }

    static final Map<String, Tags> mapHandler = new HashMap<>();

    static {
        for (Tags t : Tags.values()) {
            mapHandler.put(t.qName, t);
        }
    }

    private final List<SummaryPodcastItem> summary = new ArrayList<>();
    private final StringBuilder builder = new StringBuilder();
    private volatile boolean itemBegun = false;

    private String title = "";
    private String subtitle = "";
    private long pubDate = 0;
    private final InputStream stream;

    private final SimpleDateFormat sdf = new SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss ZZZZ", Locale.CANADA);
    
    // --- Constructor and Initialization Methods

    public RetrieveShows(InputStream is) {
        super();
        stream = is;
    }

    // --- Core and Helper Methods

    public List<SummaryPodcastItem> call() {
        process();
        return summary;
    }

    long parseDate(String s) throws ParseException {
        java.util.Date d = sdf.parse(s);
        return d.getTime();
        // Mon, 26 Jan 2015 12:30:50 +0000
        // EE, dd MMM yyyy HH:mm:ss ZZZZ
    }
    
    void process() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(stream, this);
        }
        catch (SAXException e) {
        }
        catch (IOException e) {
        } 
        catch (ParserConfigurationException ex) {
        }
    }

    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (QNAME_ITEM.equals(qName)) {
            itemBegun = true;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (itemBegun) {
            builder.append(ch, start, length);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (QNAME_ITEM.equals(qName)) {
            SummaryPodcastItem spi = new SummaryPodcastItem(title, subtitle, pubDate);
            this.summary.add(spi);
            itemBegun = false;
        }
        else if (itemBegun) {
            Tags tag = mapHandler.get(qName);
            if (tag != null) {
                String value = builder.toString().trim();
                try {
                    tag.set(value, this);
                }
                catch (ParseException pe) {
                    throw new RuntimeException(pe);
                }

                System.out.println("End: " + localName + ", " + qName + " is:");
                System.out.println(builder.toString().trim());
                System.out.println("");
            }
        }

        builder.delete(0, builder.length());
    }

}
