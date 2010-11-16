// AlbumModel.java
package model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.Icon;
import runtime.Application;
import runtime.Constants.ConfigKeys;
import common.ChangeType;
import common.IAlbumModel;
import common.IPhotoModel;
import common.IconSize;
import common.ItemChangeEvent;

/**
 * Class that provides a concrete implementation of the IAlbumModel interface 
 * by extending the abstract class DefaultItemModel. This class represents a 
 * photo album that contains a collection of photos along with other 
 * descriptive information pertaining to the album.
 */
public class AlbumModel extends DefaultItemModel implements IAlbumModel  {

    /**
     * Constructor that instantiates a new album with the specified name.
     * 
     * @param name: the name of the album to be created
     */
    public AlbumModel(String name) {
        super(name);
        mPhotos = new LinkedList<IPhotoModel>();
    }

    /**
     * Constructor that instantiates a new album with the specified name and
     * sets it's initial contents to the collection of photos passed in.
     * 
     * @param name: the name of the album to be created
     * @param photos: the collection of photos to initialize this album with
     */
    public AlbumModel(String name, Collection<IPhotoModel> photos) {
        super(name);
        mPhotos = new LinkedList<IPhotoModel>();
        for(IPhotoModel photo : photos) {
            mPhotos.add(photo);
        }
    }
    
    /**
     * Gets the path to the image that is to be used as this album's icon.
     * 
     * @return String: the path to the image of this album's icon
     */
    public String getIconImagePath() {
        if(mIconImagePath != null)
            return mIconImagePath;
        return null;
    }
    
    // ----------------------------------------------------------- IAlbumModel 
    
    /*
     * @see common.IAlbumModel#iterator()
     */
    @Override
    public Iterator<IPhotoModel> iterator() {
        return mPhotos.iterator();
    }
          
    /*
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getName();
    }

    /*
     * @see common.IAlbumModel#getPhoto(int)
     */
    @Override
    public IPhotoModel getPhoto(int index) {
        return mPhotos.get(index);
    }
    
    /*
     * @see common.IAlbumModel#getIcon()
     */
    @Override
    public Icon getIcon() {
        if(mIcon != null) {
            return mIcon;
        }
        return Application.Settings.getIcon(ConfigKeys.GalleryView.ALBUM_ICON, IconSize.SMALL);
    }

    /*
     * @see common.IAlbumModel#setIcon(javax.swing.Icon)
     */
    @Override
    public void setIcon(Icon icon) {
        mIcon = icon;
        // notifyObservers
    }

    /*
     * @see model.DefaultItemModel#getCount()
     */
    @Override
    public int getCount() {
        return mPhotos.size();
    }
    
    /*
     * @see common.IAlbumModel#getPhoto(java.lang.String)
     */
    @Override
    public IPhotoModel getPhoto(String name) {
        for(IPhotoModel photo : mPhotos) {
            if(photo.getName().compareTo(name) == 0) {
                return photo;
            }
        }
        return null;
    }

    /*
     * @see common.IAlbumModel#getPhotos()
     */
    @Override
    public List<IPhotoModel> getPhotos() {
        return mPhotos;
    }

    /*
     * @see common.IAlbumModel#addPhoto(common.IPhotoModel)
     */
    @Override
    public void addPhoto(IPhotoModel photo) {
        mPhotos.add(photo);
        photo.setAlbum(this);
        notifyChangeObservers(new ItemChangeEvent(this, photo, ChangeType.ADDITION));
    }

    /*
     * @see common.IAlbumModel#removePhoto(common.IPhotoModel)
     */
    @Override
    public void removePhoto(IPhotoModel photo) {
        if(mPhotos.remove(photo)) {
            notifyChangeObservers(new ItemChangeEvent(this, photo, ChangeType.REMOVAL));
        }
    }
    
    
    // --------------------------------------------------------- Private Fields
    
    private String mIconImagePath;
    private List<IPhotoModel> mPhotos;
    private Icon mIcon;

}