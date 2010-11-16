// ChangeType.java
package common;

/**
 * An enumeration of different change types. This enumeration is used with the
 * IChangeObserver and IChangeBroadcaster interfaces, as well as the 
 * ItemChangeEvent class.
 * 
 * @see common.IChangeObserver
 * @see common.IChangeBroadcaster
 * @see common.ItemChangeEvent
 */
public enum ChangeType {
    
    /** Change type representing the addition of an item. */
    ADDITION,
    
    /** Change type representing the removal of an item. */
    REMOVAL,
    
    /** Change type representing a change in an item's state. */
    STATE,
    
    /** Change type representing an change in an item's name. */
    RENAMED,
    
}
