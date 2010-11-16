// PhotoModel.java
package model;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import runtime.Application;
import runtime.Constants.ConfigKeys;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Metadata;
import common.ChangeType;
import common.IAlbumModel;
import common.IChangeBroadcaster;
import common.IChangeObserver;
import common.IPhotoModel;
import common.IconSize;
import common.ItemChangeEvent;

/**
 * Class that provides a concrete implementation of the IPhotoModel interface 
 * by extending the abstract class DefaultItemModel. This class represents an
 * individual photo that contains various descriptive information about it's
 * contents.
 */
public class PhotoModel extends DefaultItemModel implements IPhotoModel, IChangeBroadcaster {

    /**
     * Default constructor that instantiates a new photo model with default
     * values.
     */
    public PhotoModel() {
        super();
        setIcon(Application.Settings.getIcon(ConfigKeys.GalleryView.PHOTO_ICON, IconSize.LARGE));
        mImage = null;
        mOriginalImage = null;
        mMetadata = null;
        mAlbum = null;
        mObservers = new LinkedList<IChangeObserver>();
    }
    
    /**
     * Constructor that instantiates a new photo model with it's path set to the
     * value of the argument passed in.
     * 
     * @param path: the path of the image this photo model represents
     */
    public PhotoModel(String path) {
        super();
        setPath(path);
        mAlbum = null;
        mObservers = new LinkedList<IChangeObserver>();
    }

    
    //------------------------------------------------------------- IPhotoModel

    /*
     * @see model.DefaultItemModel#getCount()
     */
    @Override
    public int getCount() {
        // TODO Auto-Generated Method Stub
        return 0;
    }
    
    /*
     * @see common.IPhotoModel#getAlbum()
     */
    @Override
    public IAlbumModel getAlbum() {
        return mAlbum;
    }

    /*
     * @see common.IPhotoModel#getImage()
     */
    @Override
    public Image getImage() {
        return mImage;
    }
   
    /*
     * @see common.IPhotoModel#getMetadata()
     */
    @Override
    public Metadata getMetadata() {
        return mMetadata;
    }
    
    /*
     * @see common.IPhotoModel#setAlbum(IAlbumModel)
     */
    @Override
    public void setAlbum(IAlbumModel album) {
        mAlbum = album;
    }
    
    /*
     * @see model.DefaultItemModel#getName()
     */
    @Override
    public String getName() {
        String name = super.getName();
        if(name != null) {
            return name;
        }
        return mFile.getName();
    }
    
    /*
     * @see common.IPhotoModel#scaleImage(int, int)
     */
    @Override
    public void scaleImage(int width, int height) {
        mImage = getScaledImage(width, height);
        notifyChangeObservers(new ItemChangeEvent(null, this, ChangeType.STATE));
    }

    /*
     * @see common.IPhotoModel#resetImage()
     */
    @Override
    public void resetImage() {
        scaleImage(mOriginalImage.getWidth(null),mOriginalImage.getHeight(null));
        notifyChangeObservers(new ItemChangeEvent(null, this, ChangeType.STATE));
    }
    
    /*
     * @see common.IIconable#getIcon()
     */
    @Override
    public Icon getIcon() {
        if(mIcon == null) {
            if(mImage != null) {
                int thumbWidth = Application.Settings.getInt(ConfigKeys.GalleryView.THUMBNAIL_WIDTH);
                int thumbHeight = Application.Settings.getInt(ConfigKeys.GalleryView.THUMBNAIL_HEIGHT);
                mIcon = new ImageIcon(getScaledImage(thumbWidth, thumbHeight));
            }
            else {
                setIcon(Application.Settings.getIcon(ConfigKeys.GalleryView.PHOTO_ICON, IconSize.LARGE));
            }
        }
        return mIcon;
    }

    /*
     * @see common.IIconable#setIcon(Icon)
     */
    @Override
    public void setIcon(Icon icon) {
        mIcon = icon;
    }

    /*
     * @see common.IPhotoModel#getPath()
     */
    @Override
    public String getPath() {
        return mPath;
    }

    /*
     * @see common.IPhotoModel#setPath(String)
     */
    @Override
    public void setPath(String path) {
        mPath = path;
        mFile = new File(mPath);
        try {
            mOriginalImage = ImageIO.read(mFile);
            mImage = getScaledImage(mOriginalImage.getWidth(null), mOriginalImage.getHeight(null));
            mMetadata = JpegMetadataReader.readMetadata(mFile);
        } catch (JpegProcessingException jpege ) {
            Application.dump(jpege);
        }
        catch(IOException ioe) {
            Application.dump(ioe);
        }
    }

    //------------------------------------------------------ IChangeBroadcaster

     /*
     * @see model.DefaultItemModel#notifyChangeObservers(common.ItemChangeEvent)
     */
    @Override
    public void notifyChangeObservers(ItemChangeEvent e) {
        for(IChangeObserver o : mObservers) {
            o.updateChange(e);
        }
    }

    /*
     * @see model.DefaultItemModel#registerChangeObserver(common.IChangeObserver)
     */
    @Override
    public void registerChangeObserver(IChangeObserver observer) {
        mObservers.add(observer);
    }

    /*
     * @see model.DefaultItemModel#removeChangeObserver(common.IChangeObserver)
     */
    @Override
    public void removeChangeObserver(IChangeObserver observer) {
        mObservers.remove(observer);
    }

    // -------------------------------------------------------- Private Methods

    /**
     * Private helper method that takes care of the scaling of the image 
     * internally when {@link common.IPhotoModel#scaleImage(int, int)} is 
     * called.
     * 
     * @param width: the width of the scaled image
     * @param height: the height of the scaled image
     * @return Image: the resulting scaled image
     */
    private Image getScaledImage(int width, int height) {
        double thumbRatio = (double)width / (double)height;
        double imageRatio = (double)mOriginalImage.getWidth(null)/ 
            (double)mOriginalImage.getHeight(null);
        
        if (thumbRatio < imageRatio) {
            height = (int)(width / imageRatio);
        } else {
            width = (int)(height * imageRatio);
        }
        
        BufferedImage thumb = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = thumb.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(mOriginalImage, 0, 0, width, height, null);
        
        return thumb;
    }

    
    // --------------------------------------------------------- Private Fields
    
    private String mPath;
    private File mFile;
    private Icon mIcon;
    private Image mImage;
    private Image mOriginalImage;
    private Metadata mMetadata;
    private IAlbumModel mAlbum;
    private List<IChangeObserver> mObservers;
 

}  