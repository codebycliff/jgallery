// IGalleryController.java
package common;

/**
 * An interface defining the contract that which any implementing gallery
 * controllers must adhere to. A gallery controller is essentially the binding
 * between a gallery model and any/all gallery views. It provides actions for
 * which it requests the gallery model to perform and/or updates the gallery's
 * view in some way.
 */
public interface IGalleryController {

    /**
     * Peforms the work of adding a photo to one of the the gallery model's
     * albums and requests any/all of the gallery's views to update accordingly.
     */
    void addPhoto();

    /**
     * Peforms the work of adding an album to the gallery model and updated 
     * any/all of the gallery's views.
     */
    void addAlbum();

    /**
     * Peforms the work of removing the last selected item from the gallery 
     * model. The item that will be removed can be either a photo or album
     * depending on the item that was last selected in the user interface.
     */
    void removeLastSelected();

    /**
     * Peforms the work of renaming an item in the gallery model. The item could
     * be either an album or photo depending on the last selected item in the 
     * user interface.
     */
    void renameLastSelected();

    /**
     * Performs the work of setting the icon of the album containing the last 
     * selected photo to a thumbnail of that photo.
     */
    void setIconToLastSelected();

    /**
     * Makes the connection between the last selected item in the user interface
     * and the gallery model by requesting the model to update it's state
     * accordingly.
     * 
     * @param model: the item model that was last selected
     */
    void selectItem(IItemModel model);

}
