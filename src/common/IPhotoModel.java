// IPhotoModel.java
package common;

import java.awt.Image;
import com.drew.metadata.Metadata;

/**
 * An interface defining the contract that which any implementing photo models
 * must adhere to. This interface defines the required methods for which any
 * photo contained within the gallery must implement.
 */
public interface IPhotoModel extends IItemModel, IChangeBroadcaster, IIconable {

    /**
     * Gets the album this photo belongs to.
     * 
     * @return IAlbumModel: the album this photo belongs to
     */
    IAlbumModel getAlbum();

    /**
     * Gets the metadata associated with this photo model.
     * 
     * @return Metadata: the metadata of this photo model
     */
    Metadata getMetadata();

    /**
     * Gets the image of this photo model.
     * 
     * @return Image: this photo model's image
     */
    Image getImage();

    /**
     * Resets the photo model's image back to it's original/unmodified state.
     */
    void resetImage();

    /**
     * Sets the album for which this photo model belongs to.
     * 
     * @param albumModel: the album this photo belongs to
     */
    void setAlbum(IAlbumModel albumModel);

    /**
     * Scale this photo model's image to the specified width and height.
     * 
     * @param width the new width of this photo model's image
     * @param height the new height of this photo model's image
     */
    void scaleImage(int width, int height);

    /**
     * Gets the path of the actual image represented by this photo model.
     * 
     * 
     * @return String: the path of this photo model's image.
     */
    String getPath();
    
    /**
     * Sets the path of the image that this photo model represents.
     * 
     * @param path: the path of this photo model's image
     */
    void setPath(String path);
    
}