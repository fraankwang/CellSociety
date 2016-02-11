package inputoutput;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class Parser {

	/**
	 * SAXParser reads each key and creates String based on characters method
	 * @param file - the file the user selected
	 * @return mapped parameters to be parsed by the Game and Grid
	 */
    public Map<String, String> parse (File file) {
        Map<String, String> myParams = new HashMap<String, String>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {

                String key;

                public void startElement (String uri,
                                          String localName,
                                          String keyName,
                                          Attributes attributes) throws SAXException {
                    key = keyName;
                }

                public void characters (char ch[], int start, int length) throws SAXException {
                    String val = new String(ch, start, length);
                    val = val.replace("\r", "").replace("\n", "");

                    if (!myParams.containsKey(key)) {
                        myParams.put(key, val);
                    }
                }

            };

            InputStream inputStream = new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream, "UTF-8");

            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");
            saxParser.parse(is, handler);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return myParams;
        
    }
    
}