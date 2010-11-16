// XmlAlbumsReader.java
package io;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import model.AlbumModel;
import model.PhotoModel;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import runtime.Application;
import runtime.Constants.Xml;
import common.IAlbumModel;
import common.IPhotoModel;

/**
 * A class that provides an interface for reading in the gallery's contents
 * from an xml file.
 */
public class XmlAlbumsReader {

    /**
     * Constructor that takes in the file from which the gallery contents are
     * to be read. Note: Instantiating an instance through this constructor is
     * not enough to be able get the contents. After instantiating the instance
     * you must call the read method to perform the actual reading of the xml.
     * 
     * @param file: the file containing the gallery contents in xml format.
     */
    public XmlAlbumsReader(File file) {
        mAlbums = new LinkedList<IAlbumModel>();
        mFile = file;
    }
    
    /**
     * Performs the actual reading of the xml file. Once this method has been
     * called, you are free to call the getAlbums() method to retrieve the
     * gallery's contents.
     */
    public void read() {

       SAXParserFactory factory = SAXParserFactory.newInstance();
        
        XmlAlbumsHandler xmlhandler = new XmlAlbumsHandler();
        
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(mFile, xmlhandler);
        }
        catch(Exception e) {
           Application.dump(e);
        }
        
    }
    
    /**
     * Gets the gallery's contents (a collection of albums) that were read in 
     * from the xml file.
     * 
     * @return Collection: the collection of albums representing the gallery's
     *      contents.
     */
    public Collection<IAlbumModel> getAlbums() {
        return mAlbums;
    }
   
    
    // ---------------------------------------------------------- Inner Classes
    
    /**
     * Private inner class that handles the actual logic of reading in the
     * xml file.
     */
    private class XmlAlbumsHandler extends DefaultHandler {
        
        /*
         * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
         */
        @Override
        public void startElement(String namespace, String lname, String qname, Attributes attrs) {
            
            String tagName =  lname.equals("") ? qname : lname;
            
            if(tagName.compareTo(Xml.Nodes.PHOTO_ALBUM)== 0) {
                String name = attrs.getValue(Xml.Attributes.NAME);
                String description = attrs.getValue(Xml.Attributes.DESCRIPTION);
                String iconpath = attrs.getValue(Xml.Attributes.ICON);
                
                mCurrentAlbum = new AlbumModel(name);
                mCurrentAlbum.setDescription(description);
                if(iconpath != null) {
                    IPhotoModel icon = new PhotoModel(iconpath);
                    mCurrentAlbum.setIcon(icon.getIcon());
                }
                
            } 
            else if(tagName.compareTo(Xml.Nodes.PHOTO) == 0) {
                
                String name = attrs.getValue(Xml.Attributes.NAME).trim();
                String description = attrs.getValue(Xml.Attributes.DESCRIPTION).trim();
                String path = attrs.getValue(Xml.Attributes.PATH).trim();
                
                if(Application.isResourcePath(path)) {
                    path = Application.getProjectPath(path);
                }
                IPhotoModel photo = new PhotoModel(path);
                photo.setName(name);
                photo.setDescription(description);
                mCurrentAlbum.addPhoto(photo);
                
            }
            
        }
        
        /*
         * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
         */
        @Override
        public void endElement(String namespace, String sname, String qname) throws SAXException {

            String tagName =  sname.equals("") ? qname :sname;
            
            if(tagName.compareTo(Xml.Nodes.PHOTO_ALBUM) == 0 && mCurrentAlbum != null) {
                mAlbums.add(mCurrentAlbum);
            }
            
        }
        
        private IAlbumModel mCurrentAlbum;
        
    }
   
    
    // --------------------------------------------------------- Private Fields
    
    private List<IAlbumModel> mAlbums;
    private File mFile;
    
}
