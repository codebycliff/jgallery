// IconSize.java
package common;

/**
 * An enumeration of different icon sizes.
 */
public enum IconSize {
    
    /** The small icon size. */
    SMALL(16),
    
    /** The medium icon size. */
    MEDIUM(32),
    
    /** The large icon size. */
    LARGE (48),
    
    /** The default icon size. */
    DEFAULT(16);
    
    /**
     * Constructor that instantiates a new icon size with it's size set to the
     * value of the argument passed in.
     * 
     * @param size the size of the newly instantiated IconSize
     */
    IconSize(int size) {
        mSize = size;
    }
    
    /**
     * Get's the width portion of the icon's dimensions.
     * 
     * @return int: the width of the icon size.
     */
    public int width() {
        return mSize;
    }
    
    /**
     * Get's the height portion of the icon's dimensions.
     * 
     * @return int: the height of the icon size.
     */
    public int height() {
        return mSize;
    }
    
    /**
     * Get's the icon's size / resolution represented as it's width and height
     * delimited by the 'x' character. For example, an icon size of width 16
     * and height 16 would return "16x16".
     * 
     * @return String: the icon's resolution.
     */
    public String resolution() {
        return mSize + "x" + mSize;
    }
    
    private int mSize;
}
