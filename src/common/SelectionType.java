// SelectionType.java
package common;

/**
 * An enumeration of different selection types. This enumeration is used with 
 * the ISelectionObserver and ISelectionBroadcaster interfaces.
 * 
 * @see common.ISelectionObserver
 * @see common.ISelectionBroadcaster
 */
public enum SelectionType {
    
    /** The album selection type. */
    ALBUM,
    
    /** The photo selection type. */
    PHOTO,
    
    /** The selection type representing all values. */
    ANY
    
}
