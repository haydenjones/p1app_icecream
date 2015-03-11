package temp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class NewsHandler extends DefaultHandler {

    // --- Constants and Variables

    private final InputStream stream;
    private final StringBuilder sb = new StringBuilder();

    NewsItem.Builder builder = new NewsItem.Builder();
    final List<NewsItem> list = new ArrayList<>();

    // --- Constructor and Initialization Methods
    NewsHandler(InputStream is) {
        super();
        stream = is;
    }

    // --- Core and Helper Methods

    public List<NewsItem> call() throws Exception {
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
    	if (XmlFields.ENCLOSURE.qName.equals(qName)) {
    		builder.startElement(qName, attributes);
    	}
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
