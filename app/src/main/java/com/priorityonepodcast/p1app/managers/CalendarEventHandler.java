package com.priorityonepodcast.p1app.managers;

import com.priorityonepodcast.p1app.model.CalendarEvent;
import com.priorityonepodcast.p1app.model.NewsItem;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class CalendarEventHandler extends DefaultHandler {

    // --- Constants and Variables

    private final InputStream stream;
    private final StringBuilder sb = new StringBuilder();

    final List<CalendarEvent> list = new ArrayList<>();
    CalendarEvent.Builder builder = new CalendarEvent.Builder();

    // --- Constructor and Initialization Methods
    CalendarEventHandler(InputStream is) {
        super();
        stream = is;
    }

    // --- Core and Helper Methods

    public List<CalendarEvent> call() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        parser.parse(stream, this);

        return list;
    }

    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        boolean done = builder.endElement(qName, sb);

        if (done) {
            list.add(builder.build());
            builder.reset();
        }

        final int length = sb.length();
        if (length > 0) {
            sb.delete(0, length);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        sb.append(ch, start, length);
    }
}
