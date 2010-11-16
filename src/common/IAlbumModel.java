// IAlbumModel.java
package common;

import java.util.Iterator;
import java.util.List;
import javax.swing.Icon;

/**
 * An interface defining the contract that which any implementing album models
 * must adhere to. 
 */
public interface IAlbumModel extends IItemModel, Iterable<IPhotoModel>, IIconable {
    
    /**
     * Gets the photo from the album that has the name passed in.
     * 
     * @param name: String representing the name of the photo to retrieve.
     * @return IPhotoModel: the photo model
     */
    IPhotoModel getPhoto(String name);
    
    /**
     * Gets the photo at the index specified.
     * 
     * @param index: the index in the album of the photo to be returned.
     * @return IPhotoModel: the photo model
     */
    IPhotoModel getPhoto(int index);
  
    /**
     * Gets all the photos that the album contains.
     * 
     * @return List: list of photos contained by this album
     */
    List<IPhotoModel> getPhotos();
    
    /**
     * Adds the photo to this albums collection of photos.
     * 
     * @param photo: the photo to be added
     */
    void addPhoto(IPhotoModel photo);
    
    /**
     * Removes the photo specified from this album's collection.
     * 
     * @param photo: the photo to be removed
     */
    void removePhoto(IPhotoModel photo);

    /*
     * @see common.IIconable#getIcon()
     */
    @Override 
    Icon getIcon();
    
    /*
     * @see common.IIconable#setIcon(javax.swing.Icon)
     */
    @Override
    void setIcon(Icon icon);
    
    /*
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<IPhotoModel> iterator();

}