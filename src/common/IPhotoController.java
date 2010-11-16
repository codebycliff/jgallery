// IPhotoController.java
package common;

import java.awt.Dimension;

/**
 * An interface defining the contract that which any implementing photo
 * controllers must adhere to. This controller defines the actions for which
 * can be performed on any photo model.
 */
public interface IPhotoController {
    
    /**
     * Sets the photo of the model.
     * 
     * @param photo: the new photo
     */
    void setPhoto(IPhotoModel photo);
    
    /**
     * Zoom the current photo model to it's original size.
     */
    void zoomOriginal();
    
    /**
     * Zoom the current photo model to fit the dimension specified.
     * 
     * @param dim: the dimension for which the new dimensions of the photo
     *          model must adhere to.
     */
    void zoomToFit(Dimension dim);
    
    /**
     * Zoom the current photo model in one level.
     */
    void zoomIn();
    
    /**
     * Zoom the current photo model out one level.
     */
    void zoomOut();
    
}