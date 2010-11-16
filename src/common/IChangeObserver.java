// IChangeObserver.java
package common;

/**
 * An asynchronous update interface for receiving notifications about an item 
 * that has changed.
 */
public interface IChangeObserver {

    /**
     * This method is called when information about an changed item which was
     * previously requested using an asynchronous interface becomes available.
     * 
     * @param e: the event associated with the item's change
     */
    void updateChange(ItemChangeEvent e);
    
}
