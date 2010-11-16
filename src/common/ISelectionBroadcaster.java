// ISelectionBroadcaster.java
package common;

/**
 * An interface defining the contract that which any implementing selection
 * broadcasters must adhere to. The selection broadcaster implementing this
 * iterface has the responsibility of notifying any register observers when
 * the current selected item in the gallery has changed.
 */
public interface ISelectionBroadcaster {

    /**
     * Notify the registered observers that the currently selected item has
     * changed.
     * 
     * @param model: the currently selected model
     */
    void notifyObservers(IItemModel model);

    /**
     * Register an observer to be notified of any selection changes.
     * 
     * @param observer: the observer to register
     */
    void registerObserver(ISelectionObserver observer);
    
    /**
     * Register an observer to be notified when the specified selection type
     * has occurred.
     * 
     * @param observer: the observer to be registered
     * @param type: the type of selection the observer is interested in
     */
    void registerObserver(ISelectionObserver observer, SelectionType type);

    /**
     * Removes the specified observer from this broadcaster's collection of
     * observers.
     * 
     * @param observer: the observer to be removed
     */
    void removeObserver(ISelectionObserver observer);

}
