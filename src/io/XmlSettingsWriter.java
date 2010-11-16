// XmlSettingsWriter.java
package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import runtime.Application;
import runtime.Constants;
import runtime.Constants.Xml;
import common.ISettingsModel;

/**
 * A class that provides an interface for writing the user settings to an xml 
 * file.
 */
public class XmlSettingsWriter {

    /**
     * Constructor that takes in the instance of UserSettings from which the 
     * the contents of the xml file are to be taken from. Note: Instantiating an 
     * instance through this constructor will not result in a written xml file.
     * After instantiating the instance you must call the write method to 
     * perform the actual writing of the xml.
     * 
     * @param model: the user settings to write to the xml file
     */
    public XmlSettingsWriter(ISettingsModel model) {
        mModel = model;
    }
    
    /**
     * Performs the actual writing of the xml to the file specified. 
     * 
     * @param file: the file to write the xml to
     */
    public void write(File file) {

        if(Application.Runtime.backupFilesEnabled() && file.exists()) {
            
            InputStream in;
            OutputStream out;
            
            String backupFileName = Constants.USER_SETTINGS_FILE.replace(".xml", "") + " [" 
                + DateFormat.getDateTimeInstance(DateFormat.SHORT, 
                        DateFormat.SHORT).format(new 
                                Date(System.currentTimeMillis())
                        ).replace('/', '.') + "].xml";
            File backupFile = new File(backupFileName);

        
            try {   
                // Backup old configuration file.
                in = new FileInputStream(file);
                out = new FileOutputStream(backupFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0){
                  out.write(buf, 0, len);
                }
                
                in.close();
                out.close();
            } catch(IOException e) {
                Application.dump(e);
            }
        }
        
        FileWriter writer;
        
        try {
            
            // Open writer & write the XML declaration and root node
            writer = new FileWriter(file);
            writer.write("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");            
            writer.write("<!-- General Application Configuration -->\n");
            writer.write("<" + Xml.Nodes.SETTINGS + ">\n");
            
            // Write each key/value pair in settings
            for(Object key : mModel) {
                Object value = mModel.getValue(key);
                writer.write("\t<" + Xml.Nodes.ADD + " ");
                writer.write(Xml.Attributes.KEY + "=\"" + key + "\" ");
                writer.write(Xml.Attributes.VALUE + "=\"" + value + "\" />\n");
            }

            writer.write("</" + Xml.Nodes.SETTINGS+ ">\n");
            writer.close();

        }
        catch (IOException e) {
            Application.dump(e);
        }
    }

    // --------------------------------------------------------- Private Fields
    
    private ISettingsModel mModel;
}
