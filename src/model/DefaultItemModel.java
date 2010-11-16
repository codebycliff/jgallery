// DefaultItemModel.java
package model;

import java.util.LinkedList;
import java.util.List;
import common.ChangeType;
import common.IChangeBroadcaster;
import common.IChangeObserver;
import common.IItemModel;
import common.ItemChangeEvent;

/**
 * Class providing an abstract default implementation of the IItemModel 
 * interface. Classes that wish to extend this class must only provide an
 * implementation to the getCount() method to obtain a full implementation.
 */
public abstract class DefaultItemModel implements IItemModel, IChangeBroadcaster {
    
    /**
     * Default constructor that initializes all instance variables to their
     * default values.
     */
    public DefaultItemModel() {
        mChangeObservers = new LinkedList<IChangeObserver>();
        mName = new String();
        mDescription = new String();
    }
    
    /**
     * Constructor that creates a new default item model with it's name set
     * to the name specified.
     * 
     * @param name: the name of the item
     */
    public DefaultItemModel(String name) {
        mChangeObservers = new LinkedList<IChangeObserver>();
        mName = name;
        mDescription = new String();
    }

    /*
     * @see common.IItemModel#getCount()
     */
    @Override
    abstract public int getCount();
    
    /*
     * @see common.IItemModel#getDescription()
     */
    @Override
    public String getDescription() {
        return mDescription;
    }
    
    /*
     * @see common.IItemModel#getName()
     */
    @Override
    public String getName() {
        return mName;
    }

    /*
     * @see common.IItemModel#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(String description) {
        mDescription = description;
    }
    
    /*
     * @see common.IItemModel#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        mName = name;
        notifyChangeObservers(new ItemChangeEvent(null, this, ChangeType.RENAMED));
    }

    /*
     * @see common.IChangeBroadcaster#notifyChangeObservers(common.ItemChangeEvent)
     */
    @Override
    public void notifyChangeObservers(ItemChangeEvent e) {
        for(IChangeObserver observer : mChangeObservers) {
            observer.updateChange(e);
        }
    }

    /*
     * @see common.IChangeBroadcaster#registerChangeObserver(common.IChangeObserver)
     */
    @Override
    public void registerChangeObserver(IChangeObserver observer) {
        mChangeObservers.add(observer);
    }

    /*
     * @see common.IChangeBroadcaster#removeChangeObserver(common.IChangeObserver)
     */
    @Override
    public void removeChangeObserver(IChangeObserver observer) {
        mChangeObservers.remove(observer);
    }

    // --------------------------------------------------------- Private Fields
    
    private List<IChangeObserver> mChangeObservers;
    private String mName;
    private String mDescription;

}
