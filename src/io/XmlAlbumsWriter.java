// XmlAlbumsWriter.java
package io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import org.codecompanion.io.FileUtils;
import runtime.Application;
import runtime.Constants;
import runtime.Constants.Xml;
import common.IAlbumModel;
import common.IGalleryModel;
import common.IPhotoModel;

/**
 * A class that provides an interface for writing in the gallery's contents
 * to an xml file.
 */
public class XmlAlbumsWriter {

    /**
     * Constructor that takes in the gallery model from which the contents of
     * the xml file are to be taken from. Note: Instantiating an instance 
     * through this constructor does not result in a written xml file. After 
     * instantiating the instance you must call the write method to perform 
     * the actual writing of the xml.
     * 
     * @param model: the gallery model to be written to the xml file
     */
    public XmlAlbumsWriter(IGalleryModel model) {
        mModel = model;
    }
    
    /**
     * Performs the actual writing of the xml to the file specified. 
     * 
     * @param file: the file to write the xml to
     */
    public void write(File file) {

        if(Application.Runtime.backupFilesEnabled()) {
            
            DateFormat dateFmt = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
            Date currentDateTime = new Date(System.currentTimeMillis());
            String dateSuffix = dateFmt.format(currentDateTime).replace('/', '.');
            String backupFileName = Constants.USER_SETTINGS_FILE.replace(".xml", "");
            backupFileName += (" [" + dateSuffix  + "].xml");
            File backupFile = new File(backupFileName);
           
            try { 
                FileUtils.copyFile(file, backupFile);
            }
            catch (IOException e) {
                Application.dump(e);
            }
        }
        
        FileWriter writer;
        
        try {

            // Open the writer & write the XML declaration and root node
            writer = new FileWriter(file);
            writer.write("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n");
            writer.write("\t<!-- Photo Gallery -->\n");
            writer.write("\t<" + Xml.Nodes.PHOTO_GALLERY + ">\n");
            
            // Write each album as a seperate node under the root node
            for(IAlbumModel album : mModel) {
                
                writer.write("\t<" + Xml.Nodes.PHOTO_ALBUM + " ");
                writer.write(Xml.Attributes.NAME + "=\"" + album.getName() + "\" ");
                writer.write(Xml.Attributes.DESCRIPTION + "=\"" + album.getDescription() + "\" >\n");
                //FIX: IAlbumModel.getIconImagePath does not exist, not should it be. Find work around. -->: if(album.getIconImagePath() != null) { writer.write(Constants.Actions.XML_ATTR_ALBUMICON + "=\"" + album.getIconImagePath() + "\" "); }
                
                // Write each photo as seperate node under the album it belongs to.
                for(IPhotoModel photo : album) {
                    writer.write("\t\t<" + Xml.Nodes.PHOTO + " ");
                    writer.write(Xml.Attributes.NAME + "=\"" + photo.getName() + "\" ");
                    writer.write(Xml.Attributes.DESCRIPTION + "=\"" + photo.getDescription() + "\" ");
                    writer.write(Xml.Attributes.PATH + "=\"" + photo.getPath() + "\" />\n");
                }
                
                writer.write("\t</" + Xml.Nodes.PHOTO_ALBUM + ">\n");
            }
            
            writer.write("</" + Xml.Nodes.PHOTO_GALLERY + ">\n");
            writer.close();
        }
        catch (IOException e) {
            Application.dump(e);
        }
        
    }
    
    // --------------------------------------------------------- Private Fields
    
    private IGalleryModel mModel;

}
