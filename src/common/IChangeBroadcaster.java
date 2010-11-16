// IChangeBroadcaster.java
package common;

/**
 * An interface defining the contract that which any implementing change
 * broadcasters must adhere to. This interface is typical of any interface
 * that is designed to make use of the observer pattern. 
 */
public interface IChangeBroadcaster {

    /**
     * Notify the registered change observers that something has changed.
     * 
     * @param e: the item change event associated with the change
     */
    void notifyChangeObservers(ItemChangeEvent e);

    /**
     * Registers the specified change observer with this broadcaster.
     * 
     * @param observer: the observer to be registered
     */
    void registerChangeObserver(IChangeObserver observer);

    /**
     * Removes the change observer specified from this broadcaster.
     * 
     * @param observer: the observer to be removed
     */
    void removeChangeObserver(IChangeObserver observer);

}
