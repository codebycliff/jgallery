// ItemChangeEvent.java
package common;


/**
 * A class representing an item change event. This class is used with the 
 * IChangeObserver and IChangeBroadcaster interfaces.
 * 
 * @see common.IChangeObserver
 * @see common.IChangeBroadcaster
 */
public class ItemChangeEvent {
    
    /**
     * Constructor that instantiates a new item change event with the passed
     * in item (parent), changed item, and the change type.
     * 
     * @param item: the item which has changed
     * @param changedItem: the item that has changed for the parent item
     * @param type: the type of change
     */
    public ItemChangeEvent(IItemModel item, IItemModel changedItem, ChangeType type) {
        mItem = item;
        mItemChanged = changedItem;
        mChangeType = type;
    }
    
    /**
     * Gets the item that has changed on the parent item.
     * 
     * @return IItemModel: the item changed
     */
    public IItemModel getChange() {
        return mItemChanged;
    }

    /**
     * Gets the item that has a change associated with.
     * 
     * @return IItemModel: the item associated with the change
     */
    public IItemModel getItem() {
        return mItem;
    }

    /**
     * Gets the type of change.
     * 
     * @return ChangeType: the type of change
     */
    public ChangeType getType() {
        return mChangeType;
    }

    
    // --------------------------------------------------------- Private Fields
    
    private IItemModel mItem;
    private IItemModel mItemChanged;
    private ChangeType mChangeType;
    
}
