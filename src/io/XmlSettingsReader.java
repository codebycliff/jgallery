// XmlSettingsReader.java
package io;

import java.io.File;
import java.util.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import runtime.Application;
import runtime.Constants.Xml;

/**
 * A class that provides an interface for reading in the user settings from an
 * xml file.
 */
public class XmlSettingsReader {
    
    /**
     * Constructor that takes in the file from which the user settings are to
     * be read. Note: Instantiating an instance through this constructor is
     * not enough to be able get the settings. After instantiating the instance
     * you must call the read method to perform the actual reading of the xml.
     * 
     * @param file: the file containing the user settings in xml format
     */
    public XmlSettingsReader(File file) {
        mSettingsHash = new HashMap<Object, Object>();
        mFile = file;
    }
    
    /**
     * Performs the actual reading of the xml file. Once this method has been
     * called, you are free to call the getSettings() method to retrieve the
     * user settings that were read in.
     */
    public void read() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        
        XmlSettingsHandler xmlhandler = new XmlSettingsHandler();
        
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(mFile, xmlhandler);
        }
        catch(Exception e) {
           Application.dump(e);
        }
    }
    
    /**
     * Gets the user settings (a map of key/values) that were read in from the 
     * xml file.
     * 
     * @return HashMap: the map of key/value pairs representing the user settings.
     */
    public HashMap<Object,Object> getSettings() {
        return mSettingsHash;
    }

    
    // ---------------------------------------------------------- Inner Classes
    
    /**
     * Private inner class that handles the actual logic of reading in the
     * xml file.
     */
    private class XmlSettingsHandler extends DefaultHandler {
        
        @Override
        public void startElement(String namespace, String lname, String qname, Attributes attrs) {
            
            String tagName =  lname.equals("") ? qname :lname;
            
            if(tagName.compareTo(Xml.Nodes.ADD) == 0) {
                String key = attrs.getValue(Xml.Attributes.KEY);
                String value = attrs.getValue(Xml.Attributes.VALUE);
                mSettingsHash.put(key, value);
            }
            
        }
        
    }

    
    // --------------------------------------------------------- Private Fields
    
    private File mFile;
    private HashMap<Object,Object> mSettingsHash;

}