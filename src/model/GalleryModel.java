// GalleryModel.java
package model;

import io.XmlAlbumsReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import common.ChangeType;
import common.IAlbumModel;
import common.IGalleryModel;
import common.IItemModel;
import common.IPhotoModel;
import common.ISelectionObserver;
import common.ItemChangeEvent;
import common.SelectionType;

/**
 * Class that provides a concrete implementation of the IGalleryModel interface 
 * by extending the abstract class DefaultItemModel. This class represents the
 * photo gallery as a whole and contains a collection of album models, for which
 * each contains a collection of photo models.
 */
public class GalleryModel extends DefaultItemModel implements IGalleryModel {

    /**
     * Default constructor that instantiates a new gallery model with default
     * values.
     */
    public GalleryModel() {
        super();
        initialize();
    }
    
    /**
     * Constructor that instantiates a new gallery model using the specified 
     * xml albums reader to read in it's contents from an xml file. 
     * 
     * @param reader: the xml albums reader used to read in the contents of
     *          the gallery.
     */
    public GalleryModel(XmlAlbumsReader reader) {
        super();
        reader.read();
        mAlbums = new LinkedList<IAlbumModel>(reader.getAlbums());
        initialize();
    }
    
    /**
     * Constructor that instantiates a new gallery model abd initializes it with
     * the collection of albums passed in.
     * 
     * @param albums: the collection of albums to initialize this gallery model 
     *          with.
     */
    public GalleryModel(Collection<IAlbumModel> albums) {
        super();
        mAlbums = new LinkedList<IAlbumModel>(albums);
        initialize();
    }

    // ---------------------------------------------------------- IGalleryModel 

    /*
     * @see common.IGalleryModel#getLastSelected()
     */
    @Override
    public IItemModel getLastSelected() {
        return mLastSelected;
    }
    
    /*
     * @see common.IGalleryModel#getLastSelectedPhoto()
     */
    @Override
    public IPhotoModel getLastSelectedPhoto() {
        return mLastSelectedPhoto;
    }

    /*
     * @see common.IGalleryModel#getLastSelectedAlbum()
     */
    @Override
    public IAlbumModel getLastSelectedAlbum() {
        return mLastSelectedAlbum;
    }
    
    /*
     * @see common.IGalleryModel#iterator()
     */
    @Override
    public Iterator<IAlbumModel> iterator() {
        return mAlbums.iterator();
    }

    /*
     * @see common.IGalleryModel#setSelectedItem(common.IItemModel)
     */
    @Override
    public void setSelectedItem(IItemModel model) {
        mLastSelected = model;
        if(mLastSelected instanceof IAlbumModel) {
            mLastSelectedAlbum = (IAlbumModel)mLastSelected;
        }
        else {
            mLastSelectedPhoto = (IPhotoModel)mLastSelected;
        }
        notifyObservers(model);
    }
    
    /*
     * @see model.DefaultItemModel#getCount()
     */
    @Override
    public int getCount() {
        return mAlbums.size();
    }
    
    /*
     * @see common.IGalleryModel#addAlbum(common.IAlbumModel)
     */
    @Override
    public void addAlbum(IAlbumModel album) {
        mAlbums.add(album);
        notifyChangeObservers(new ItemChangeEvent(this, album, ChangeType.ADDITION));
    }

    /*
     * @see common.IGalleryModel#removeAlbum(common.IAlbumModel)
     */
    @Override
    public void removeAlbum(IAlbumModel album) {
        if(mAlbums.remove(album)) {
            notifyChangeObservers(new ItemChangeEvent(this, album, ChangeType.REMOVAL));
        }
    }
    
    /*
     * @see common.IGalleryModel#getAlbum(int)
     */
    @Override
    public IAlbumModel getAlbum(int index) {
        if(index < mAlbums.size()) {
            return mAlbums.get(index);
        }
        return null;
    }

    /*
     * @see common.IGalleryModel#getAlbum(java.lang.String)
     */
    @Override
    public IAlbumModel getAlbum(String name) {
        for(IItemModel model : mAlbums) {
            if(model.getName().compareTo(name)==0) {
                return (IAlbumModel)model;
            }
        }
        return null;
    }
    
    // -------------------------------------------------- ISelectionBroadcaster
    
    /*
     * @see common.IGalleryModel#notifyObservers(common.IItemModel)
     */
    @Override
    public void notifyObservers(IItemModel model) {
        HashSet<ISelectionObserver> observers = new HashSet<ISelectionObserver>();
        observers.addAll(mSelectionObservers.get(SelectionType.ANY));
        
        if(model instanceof IPhotoModel) {
            observers.addAll(mSelectionObservers.get(SelectionType.PHOTO));
        }
        else if (model instanceof IAlbumModel) {
            observers.addAll(mSelectionObservers.get(SelectionType.ALBUM));
        }
    
        for(ISelectionObserver observer : observers) {
            observer.update(model);
        }
    }

    /*
     * @see common.IGalleryModel#registerObserver(common.ISelectionObserver)
     */
    @Override
    public void registerObserver(ISelectionObserver observer) {
        mSelectionObservers.get(SelectionType.ANY).add(observer);
    }

    /*
     * @see common.IGalleryModel#registerObserver(common.ISelectionObserver, common.SelectionType)
     */
    @Override
    public void registerObserver(ISelectionObserver observer, SelectionType type) {
        mSelectionObservers.get(type).add(observer);
    }
    
    /*
     * @see common.IGalleryModel#removeObserver(common.ISelectionObserver)
     */
    @Override
    public void removeObserver(ISelectionObserver observer) {
        mSelectionObservers.get(SelectionType.ANY).remove(observer);
        mSelectionObservers.get(SelectionType.ALBUM).remove(observer);
        mSelectionObservers.get(SelectionType.PHOTO).remove(observer);
    }
  
    // -------------------------------------------------------- Private Methods
  
    /**
     * Private helper method to centralize the initialize of instance variables.
     */
    private void initialize() {
        mSelectionObservers = new HashMap<SelectionType, List<ISelectionObserver>>();
        mSelectionObservers.put(SelectionType.ANY, new LinkedList<ISelectionObserver>());
        mSelectionObservers.put(SelectionType.ALBUM, new LinkedList<ISelectionObserver>());
        mSelectionObservers.put(SelectionType.PHOTO, new LinkedList<ISelectionObserver>());
    }
    
    // --------------------------------------------------------- Private Fields
    
    private IPhotoModel mLastSelectedPhoto;
    private IAlbumModel mLastSelectedAlbum;
    private IItemModel mLastSelected;
    private Map<SelectionType, List<ISelectionObserver>> mSelectionObservers;
    private List<IAlbumModel> mAlbums;
  
}