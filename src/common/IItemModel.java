// IItemModel.java
package common;

/**
 * An interface defining the contract that which any implementing item models
 * must adhere to. This interface is the base from which any item in the photo
 * gallery extends. It provides the access to the properties that all items
 * have in common.
 */
public interface IItemModel extends IChangeBroadcaster {
   
    /**
     * Gets the count of child items contained by this model.
     * 
     * @return int: the count of child items
     */
    int getCount();
    
    /**
     * Gets the description of this item.
     * 
     * @return String: the description of this item
     */
    String getDescription();
    
    /**
     * Gets the name of this item.
     * 
     * @return String: the name of this item
     */
    String getName();
    
    /**
     * Sets the description of this item.
     * 
     * @param description: the new description of the item 
     */
    void setDescription(String description);
    
    /**
     * Sets the name of this item.
     * 
     * @param name: the new name of this item
     */
    void setName(String name);
   
}
