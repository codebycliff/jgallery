// ISelectionObserver.java
package common;

/**
 * An asynchronous update interface for receiving notifications about the
 * selection information as the currently selected item is changed.
 */
public interface ISelectionObserver {

    /**
     * This method is called when information about a selection type which was
     * previously requested using an asynchronous interface becomes available.
     * 
     * @param selected: the currently selected item
     */
    void update(IItemModel selected);
    
}
