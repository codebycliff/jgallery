// IIconable.java
package common;

import javax.swing.Icon;

/**
 * An interface defining the contract that which any implementing iconables must
 * adhere to. This interface provides the methods for which any item that can
 * be represented by an icon must implement.
 */
public interface IIconable {

    /**
     * Gets the icon of the associated item.
     * 
     * @return Icon: the icon of the item
     */
    Icon getIcon();
    
    /**
     * Sets the icon of the associated item.
     * 
     * @param icon: the new icon of the item
     */
    void setIcon(Icon icon);
    
}
