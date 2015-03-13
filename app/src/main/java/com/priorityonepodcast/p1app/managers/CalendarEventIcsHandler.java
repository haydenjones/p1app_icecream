package com.priorityonepodcast.p1app.managers;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.priorityonepodcast.p1app.model.CalendarEvent;

public class CalendarEventIcsHandler {

    // --- Constants and Variables

    public enum IcsFlags {
        /**
         *
         */
        NULL,
        /**
         *
         */
        BEGIN,
        /**
         *
         */
        PRODID,
        /**
         *
         */
        VERSION,
        /**
         *
         */
        CALSCALE,
        /**
         *
         */
        METHOD,
        /**
         *
         */
        X_WR_CALNAME("X-WR-CALNAME"),
        /**
         *
         */
        X_WR_TIMEZONE("X-WR-TIMEZONE"),
        /**
         *
         */
        DTSTART {
            @Override
            public void firstToken(String s, CalendarEventIcsHandler dateParser) {
                // Now I need to parse out the info from 20150327T160000Z
                try {
                    Date d = dateParser.parseDate(s);
                }
                catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        private final String qName;

        IcsFlags() {
            qName = name() + ":";
        }

        IcsFlags(String qn) {
            qName = qn + ":";
        }

        public void firstToken(String s, CalendarEventIcsHandler dateParser) {

        }

        public void first(String s, CalendarEventIcsHandler dateParser) {
            String value = s.substring(qName.length());
            firstToken(value, dateParser);
        }

        public void append(String s) {

        }

        public void done() {
        }
    }

    static final Map<String, IcsFlags> MAP_ICS = new HashMap<>();

    static {
        for (IcsFlags flag : IcsFlags.values()) {
            MAP_ICS.put(flag.name(), flag);
        }
        MAP_ICS.remove(IcsFlags.NULL.name());
    }

    static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    static byte[] readAllBytes(InputStream stream, byte[] bytes) throws IOException {
        final int available = stream.available();
        if (available < 1) {
            return bytes;
        }

        final byte[] read = new byte[available];
        final int justRead = stream.read(read);

        final byte[] out = new byte[bytes.length + justRead];
        System.arraycopy(bytes, 0, out, 0, bytes.length);
        System.arraycopy(read, 0, out, bytes.length, justRead);
        return out;
    }

    private final InputStream stream;
    private final SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd'T'HHmmss'Z'", Locale.US);
    private final StringBuilder sb = new StringBuilder();

    final List<CalendarEvent> list = new ArrayList<>();
    CalendarEvent.Builder builder = new CalendarEvent.Builder();

    // --- Constructor and Initialization Methods
    public CalendarEventIcsHandler(InputStream is) {
        super();
        stream = is;
    }

    // --- Core and Helper Methods

    public Date parseDate(String value) throws ParseException {
        return sdf.parse(value);
    }

    public List<CalendarEvent> call() throws Exception {
        byte[] bytes = readAllBytes(stream, EMPTY_BYTE_ARRAY);
        String full = new String(bytes);

        IcsFlags last = IcsFlags.NULL;

        String[] lines = full.split("\n");

        for (String s : lines) {
            System.out.println(s);
            int firstColon = s.indexOf(":");
            if (firstColon > 0) {
                String key = s.substring(0, firstColon);
                IcsFlags found = MAP_ICS.get(key);
                if (found == null) {
                    last.append(s);
                }
                else {
                    last.done();
                    found.first(s, this);
                    last = found;
                }
            }
            else {
                last.append(s);
            }
        }

        return list;
    }

    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods
}