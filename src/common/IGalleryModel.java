// IGalleryModel.java
package common;

import java.util.Iterator;

/**
 * An interface defining the contract that which any implementing gallery models
 * must adhere to. A gallery model represents a photo gallery as whole. This
 * means that internally it is represented by a collection of albums for which
 * each album has a collection of photos.
 */
public interface IGalleryModel extends IItemModel,  Iterable<IAlbumModel>, ISelectionBroadcaster, IChangeBroadcaster {
    
    /**
     * Gets the last selected item in this gallery model.
     * 
     * @return IItemModel: the last selected item
     */
    IItemModel getLastSelected();
    
    /**
     * Gets the last selected photo in this gallery.
     * 
     * @return IPhotoModel: the last selected photo
     */
    IPhotoModel getLastSelectedPhoto();
    
    /**
     * Gets the last selected album in this gallery.
     * 
     * @return IAlbumModel: the last selected album
     */
    IAlbumModel getLastSelectedAlbum();

    /**
     * Sets currently selected item in this gallery.
     * 
     * @param model: the currently selected item
     */
    void setSelectedItem(IItemModel model);

    /**
     * Gets the album that is at the specified index in this gallery.
     * 
     * @param index: the index of the album to be returned
     * @return IAlbumModel: the album to be returned
     */
    IAlbumModel getAlbum(int index);
    
    /**
     * Gets the album that has the name specified from this gallery.
     * 
     * @param name: the name of the album to be returned
     * @return IAlbumModel: the album to be returned
     */
    IAlbumModel getAlbum(String name);
    
    /**
     * Adds an album to this gallery's collection of albums.
     * 
     * @param album: the album to be added
     */
    void addAlbum(IAlbumModel album);
    
    /**
     * Removes the specified album from this gallery's collection of items.
     * 
     * @param album: the album to be removed
     */
    void removeAlbum(IAlbumModel album);
    
    /*
     * @see java.lang.Iterable#iterator()
     */
    @Override
    Iterator<IAlbumModel> iterator();

    /*
     * @see common.ISelectionBroadcaster#notifyObservers(common.IItemModel)
     */
    @Override
    public void notifyObservers(IItemModel model);
    
    /*
     * @see common.ISelectionBroadcaster#registerObserver(common.ISelectionObserver)
     */
    @Override
    public void registerObserver(ISelectionObserver observer);
    
    /*
     * @see common.ISelectionBroadcaster#registerObserver(common.ISelectionObserver, common.SelectionType)
     */
    @Override
    public void registerObserver(ISelectionObserver observer, SelectionType type);
    
    /*
     * @see common.ISelectionBroadcaster#removeObserver(common.ISelectionObserver)
     */
    @Override
    public void removeObserver(ISelectionObserver observer);
    
    /*
     * @see common.IChangeBroadcaster#notifyChangeObservers(common.ItemChangeEvent)
     */
    @Override
    public void notifyChangeObservers(ItemChangeEvent e);
    
    /*
     * @see common.IChangeBroadcaster#registerChangeObserver(common.IChangeObserver)
     */
    @Override
    public void registerChangeObserver(IChangeObserver observer);
    
    /*
     * @see common.IChangeBroadcaster#removeChangeObserver(common.IChangeObserver)
     */
    @Override
    public void removeChangeObserver(IChangeObserver observer);

}
