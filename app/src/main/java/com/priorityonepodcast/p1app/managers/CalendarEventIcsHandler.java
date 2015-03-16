package com.priorityonepodcast.p1app.managers;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.priorityonepodcast.p1app.model.CalendarEvent;

public class CalendarEventIcsHandler {

    // --- Constants and Variables

    public enum IcsFlags {
        /**
         *
         */
        NULL(""),
        /**
         *
         */
        BEGIN_VCALENDAR("BEGIN:VCALENDAR"),
        /**
         *
         */
        PRODID("PRODID:"),
        /**
         *
         */
        VERSION("VERSION:"),
        /**
         *
         */
        CALSCALE("CALSCALE:"),
        /**
         *
         */
        METHOD("METHOD:"),
        /**
         *
         */
        X_WR_CALNAME("X-WR-CALNAME:"),
        /**
         *
         */
        X_WR_TIMEZONE("X-WR-TIMEZONE:"),
        /**
         *
         */
        BEGIN_VEVENT("BEGIN:VEVENT") {
            @Override
            public void firstToken(CalendarEvent.Builder b, String s, CalendarEventIcsHandler dateParser) {
                b.reset();
            }
        },
        /**
         *
         */
        DTSTART("DTSTART:") {
            @Override
            public void firstToken(CalendarEvent.Builder b, String s, CalendarEventIcsHandler dateParser) {
                // Now I need to parse out the info from 20150327T160000Z
                try {
                    Date d = dateParser.parseDate(s);
                    b.startTime(d);
                }
                catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        },
        /**
        *
        */
        DTEND("DTEND:") {
            @Override
            public void firstToken(CalendarEvent.Builder b, String s, CalendarEventIcsHandler dateParser) {
                // Now I need to parse out the info from 20150327T160000Z
                try {
                    Date d = dateParser.parseDate(s);
                    b.endTime(d);
                }
                catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        },
        /**
         *
         */
        DTSTAMP("DTSTAMP:"),
        /**
         *
         */
        UID("UID:") {
            @Override
            public void firstToken(CalendarEvent.Builder b, String s, CalendarEventIcsHandler dateParser) {
                b.uid(s);
            }
        },
        /**
         *
         */
        CREATED("CREATED:"),
        /**
        *
        */
        DESCRIPTION("DESCRIPTION:") {
            @Override
            public void firstToken(CalendarEvent.Builder b, String s, CalendarEventIcsHandler dateParser) {
                b.description(s);
            }
        },
        /**
        *
        */
        LAST_MODIFIED("LAST-MODIFIED:"),
        /**
        *
        */
        LOCATION("LOCATION:") {
            @Override
            public void firstToken(CalendarEvent.Builder b, String s, CalendarEventIcsHandler dateParser) {
                b.location(s);
            }
        },
        /**
        *
        */
        SEQUENCE("SEQUENCE:"),
        /**
        *
        */
        STATUS("STATUS:") {
            @Override
            public void firstToken(CalendarEvent.Builder b, String s, CalendarEventIcsHandler dateParser) {
                b.status(s);
            }
        },
        /**
        *
        */
        SUMMARY("SUMMARY:") {
            @Override
            public void firstToken(CalendarEvent.Builder b, String s, CalendarEventIcsHandler dateParser) {
                b.title(s);
            }
        },
        /**
        *
        */
        TRANSP("TRANSP:"),
        /**
        *
        */
        END_VEVENT("END:VEVENT"),
        /**
        *
        */
        END_VCALENDAR("END:VCALENDAR");

        private final String qName;

        IcsFlags(String qn) {
            qName = qn;
        }

        public boolean first(CalendarEvent.Builder b, String s, CalendarEventIcsHandler dateParser) {
            String value = s.substring(qName.length());
            firstToken(b, value, dateParser);
            return (this == END_VEVENT);
        }

        void firstToken(CalendarEvent.Builder b, String s, CalendarEventIcsHandler dateParser) {

        }

        public void append(CalendarEvent.Builder b, String s) {
            throw new RuntimeException("Append not support for " + this + " called for content: " + s);
        }
    }

    static final NavigableMap<String, IcsFlags> MAP_ICS = new TreeMap<>();

    static {
        for (IcsFlags flag : IcsFlags.values()) {
            MAP_ICS.put(flag.qName, flag);
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

    final List<CalendarEvent> list = new ArrayList<>();
    CalendarEvent.Builder builder = new CalendarEvent.Builder();

    // --- Constructor and Initialization Methods
    public CalendarEventIcsHandler(InputStream is) {
        super();
        stream = is;
    }

    // --- Core and Helper Methods

    public Date parseDate(String value) throws ParseException {
        // "20150327T160000Z";
        value = value.trim();
        int length = value.length();
        if (length != 16) {
            throw new ParseException(value + " not in expected format", 0);
        }

        StringBuilder sb = new StringBuilder();

        sb.append(value.substring(0, 4));
        sb.append("-");
        sb.append(value.substring(4, 6));
        sb.append("-");
        sb.append(value.substring(6, 8));
        sb.append(" ");
        sb.append(value.substring(9, 11));
        sb.append(":");
        sb.append(value.substring(11, 13));
        sb.append(":");
        sb.append(value.substring(13, 15));
        sb.append(".0");

        java.sql.Timestamp ts = java.sql.Timestamp.valueOf(sb.toString());
        return ts;
    }

    List<String> trimLines(String[] input) {
        List<String> lines = new ArrayList<>();
        for (String s : input) {
            lines.add(s.trim());
        }
        return lines;
    }

    public List<CalendarEvent> call() throws Exception {
        byte[] bytes = readAllBytes(stream, EMPTY_BYTE_ARRAY);
        String full = new String(bytes);

        IcsFlags last = IcsFlags.NULL;

        String[] splitLines = full.split("\n");
        List<String> lines = trimLines(splitLines);

        for (String s : lines) {
            IcsFlags entry = MAP_ICS.get(s);

            if (entry == null) {
                Map.Entry<String, IcsFlags> floor = MAP_ICS.floorEntry(s);

                if ((floor != null) && (s.startsWith(floor.getKey()))) {
                    entry = floor.getValue();
                }
                else {
                    last.append(builder, s);
                    continue;
                }
            }

            boolean addToList = entry.first(builder, s, this);
            if (addToList) {
                CalendarEvent ce = builder.build();
                list.add(ce);
                builder.reset();
            }

            last = entry;
        }

        return list;
    }

    // --- Getter and Setter Methods
    // --- Delegate and Convenience Methods
    // --- Miscellaneous Methods
}
