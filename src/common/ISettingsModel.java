// ISettingsModel.java
package common;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Iterator;
import javax.swing.Icon;

/**
 * An interface defining the contract that which any implementing settings
 * models must adhere to. This interface defines the way in which any settings
 * model must be updated and the method for retrieving it's state.
 */
public interface ISettingsModel extends Iterable<Object> {

    /**
     * Gets the boolean value of the key specified.
     * 
     * @param key: the key
     * @return Boolean: the value represented as a boolean
     */
    Boolean getBoolean(Object key);
    
    /**
     * Gets the color represented by the key specified.
     * 
     * @param key: the key
     * @return Color: the value represented as an instance of a Color
     */
    Color getColor(Object key);
    
    /**
     * Gets the value as a Dimension for the key specified.
     * 
     * @param key: the key
     * @return Dimension: the value represented as an instance of Dimension
     */
    Dimension getDimension(Object key);
    
    /**
     * Gets the icon specified by the the key and size passed in.
     * 
     * @param key: the key for the icon requested
     * @param size: the size of the icon to be returned
     * @return Icon: the icon
     */
    Icon getIcon(Object key, IconSize size);
    
    /**
     * Gets the value of the key specified represented as an Integer.
     * 
     * @param key: the key
     * @return Integer: the value represented as an Integer
     */
    Integer getInt(Object key);
    
    /**
     * Gets the value of the key specified represented as a Long.
     * 
     * @param key: the key
     * @return Long: the value represented as a Long
     */
    Long getLong(Object key);
   
    /**
     * Gets the value of the key specified represented as a String
     * 
     * @param key: the key
     * @return String: the value of the key represented as a String
     */
    String getString(Object key);
    
    /**
     * Gets the value key represented as an Object.
     * 
     * @param key: the key
     * @return Object: the value of the key
     */
    Object getValue(Object key);
    
    /*
     * @see java.lang.Iterable#iterator()
     */
    @Override
    Iterator<Object> iterator();
    
    /**
     * Notify the registered observers of change to the settings key.
     * 
     * @param key: the key that was modified
     * @param valueChanged: the new value of the key
     */
    void notifyObservers(Object key, Object valueChanged);
    
    /**
     * Puts the key specified in the settings with the value specified.
     * 
     * @param key: the key to put
     * @param value the value for the key
     */
    void putValue(Object key, Object value);
    
    /**
     * Register an observer to be notified when any settings change.
     * 
     * @param observer: the observer to add
     */
    void registerObserver(PropertyChangeListener observer);
    
    /**
     * Gets the value of the key specified represented as an instance of File.
     * 
     * @param key: the key
     * @return File: the value represented as a File
     */
    File getFile(Object key);
    
}
