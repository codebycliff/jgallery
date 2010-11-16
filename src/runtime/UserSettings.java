// UserSettings.java
package runtime;

import io.XmlSettingsReader;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import runtime.Constants.ConfigKeys;
import common.ISettingsModel;
import common.IconSize;

/**
 * Class that provides a concrete implementation of the ISettingsModel interface.
 */
public class UserSettings implements ISettingsModel, Iterable<Object> {

    /**
     * Default constructor initializes all the settings to their defaul values.
     */
    public UserSettings() {
        mSettings = new HashMap<Object, Object>();
        mObservers = new LinkedList<PropertyChangeListener>();
        loadDefaults();
    }
    
    /**
     * Constructor that takes in an xml settings reader object from which the
     * settings should be taken from. This object is used to read in the
     * settings from the user settings file.
     * 
     * @param reader: the xml settings reader to get the setting's values from.
     */
    public UserSettings(XmlSettingsReader reader) {
        reader.read();
        mSettings = reader.getSettings();
        mObservers = new LinkedList<PropertyChangeListener>();
        loadDefaults();
    }

    /**
     * Gets an iterator that is iterates over all the settings values. 
     * 
     * @return Iterator: the iterator to be returned.
     */
    public Iterator<Object> valueIterator() {
        List<Object> settingsValues = new LinkedList<Object>();
        for(Object key : this) {
            settingsValues.add(this.getValue(key));
        }
        return settingsValues.iterator();
    }
    
    // --------------------------------------------------------- ISettingsModel 
    
    /*
     * @see common.ISettingsModel#getBoolean(java.lang.Object)
     */
    @Override
    public Boolean getBoolean(Object key) {
        Object value = getValue(key);
        if(value != null) {
            try {
                return Boolean.parseBoolean(value.toString());
            }
            catch(Exception e) {
                Application.dump(e);
            }
        }
        return null;
    }
    
    /*
     * @see common.ISettingsModel#getColor(java.lang.Object)
     */
    @Override
    public Color getColor(Object key) {
        Object value = getValue(key);
        if(value != null) {
            try {
                int hex = Integer.parseInt(value.toString(), 16);
                return new Color(hex);    
            }
            catch(Exception e) {
                Application.dump(e);
            }
        }
        return null;
    }

    /*
     * @see common.ISettingsModel#getDimension(java.lang.Object)
     */
    @Override
    public Dimension getDimension(Object key) {
        Object value = getValue(key);
        if(value != null) {
            try {
                String[] dimensions = value.toString().split("[^0-9]");
                int width = Integer.parseInt(dimensions[0]);
                int height = Integer.parseInt(dimensions[1]);
                return new Dimension(width, height);    
            }
            catch (Exception e) {
                Application.dump(e);
            }
        }
        return null;
    }
    
    /*
     * @see common.ISettingsModel#getFile(java.lang.Object)
     */
    @Override
    public File getFile(Object key) {
        Object value = getValue(key);
        if(value != null) {
            return new File(value.toString());
        }
        return null;
    }
    
    /*
     * @see common.ISettingsModel#getIcon(java.lang.Object, common.IconSize)
     */
    @Override
    public Icon getIcon(Object key, IconSize size) {
        Object value = getValue(key);
        String iconPath = "resources/" + "icons/" + size.resolution() + "/" + value.toString();
        URL url = Boot.class.getResource(iconPath);
        if(url != null) {
            return new ImageIcon(url);
        }
        else {
            Object defaultValue = getValue(ConfigKeys.GalleryView.ITEM_ICON);
            if(defaultValue != null) {
                iconPath = "resources/" + "icons/" + IconSize.DEFAULT.resolution() + "/actions/" + value.toString();    
            }
            URL defaultUrl = Boot.class.getResource(iconPath);
            if(defaultUrl != null) {
                return new ImageIcon(defaultUrl);
            }
        }    
        return null;
    }
    
    /*
     * @see common.ISettingsModel#getInt(java.lang.Object)
     */
    @Override
    public Integer getInt(Object key) {
        Object value = getValue(key);
        
        if(value != null) {
            try {
                return Integer.parseInt(value.toString());        
            }
            catch(Exception e) {
                Application.dump(e);
            }
        }
        return null;
    }

    /*
     * @see common.ISettingsModel#getLong(java.lang.Object)
     */
    @Override
    public Long getLong(Object key) {
        Object value = getValue(key);
        if(value != null) { 
            try {
                return Long.parseLong(value.toString());    
            }
            catch(Exception e) {
                Application.dump(e);
            }
        }
        return null;
    }

    /*
     * @see common.ISettingsModel#getString(java.lang.Object)
     */
    @Override
    public String getString(Object key) {
        Object value = getValue(key);
        return value.toString();    
    }

    /*
     * @see common.ISettingsModel#getValue(java.lang.Object)
     */
    @Override
    public Object getValue(Object key) {
        if(mSettings.containsKey(key)) {
            return mSettings.get(key);    
        }
        return mDefaults.get(key);
    }
       
    /*
     * @see common.ISettingsModel#iterator()
     */
    @Override
    public Iterator<Object> iterator() {
        Set<Object> settingsKeySet = mSettings.keySet();
//        settingsKeySet.addAll(mDefaults.keySet());
        return settingsKeySet.iterator();
    }

    /*
     * @see common.ISettingsModel#putValue(java.lang.Object, java.lang.Object)
     */
    @Override
    public void putValue(Object key, Object value) {
        mSettings.put(key, value);
        notifyObservers(key, value);
    }

    /*
     * @see common.ISettingsModel#notifyObservers(java.lang.Object, java.lang.Object)
     */
    @Override
    public void notifyObservers(Object key, Object value) {
        for(PropertyChangeListener observer : mObservers) {
            observer.propertyChange(new PropertyChangeEvent(this, key.toString(), null, value.toString()));
        }
    }

    /*
     * @see common.ISettingsModel#registerObserver(java.beans.PropertyChangeListener)
     */
    @Override
    public void registerObserver(PropertyChangeListener observer) {
        mObservers.add(observer);
    }
    
    // -------------------------------------------------------- Private Methods
    
    /**
     * Private method that initializes the settings values with hard-coded 
     * defaults. These settings are hard-coded to make sure that there is a safe
     * default to fall back on if the reading of a settings file fails.
     */
    private void loadDefaults() {
        mDefaults = new HashMap<Object, Object>();
        mDefaults.put(ConfigKeys.PLAF_CLASS, UIManager.getLookAndFeel().getClass().getName());
        mDefaults.put(ConfigKeys.NAME, "Photo Gallery");
        mDefaults.put(ConfigKeys.DetailsView.VISIBLE, true);
        mDefaults.put(ConfigKeys.DetailsView.DIVIDER_LOC, 600);
        mDefaults.put(ConfigKeys.GalleryView.VISIBLE, true);
        mDefaults.put(ConfigKeys.GalleryView.THUMBNAIL_HEIGHT, 128);
        mDefaults.put(ConfigKeys.GalleryView.THUMBNAIL_WIDTH, 128);
        mDefaults.put(ConfigKeys.GalleryView.SHOW_ALBUMCOUNT, false);
        mDefaults.put(ConfigKeys.GalleryView.SHOW_PHOTONAME, true);
        mDefaults.put(ConfigKeys.GalleryView.MAXIMUM_WIDTH, 333);
        mDefaults.put(ConfigKeys.GalleryView.DIVIDER_LOC, 200);
        mDefaults.put(ConfigKeys.MainView.SIZE, new Dimension(1000,1000));
        mDefaults.put(ConfigKeys.MainView.TITLE, "Photo Gallery");
        mDefaults.put(ConfigKeys.MainView.X_LOCATION, 25);
        mDefaults.put(ConfigKeys.MainView.Y_LOCATION, 25);
        mDefaults.put(ConfigKeys.PhotoView.BACKGROUND_COLOR, new Color(128,128,128));
        mDefaults.put(ConfigKeys.Slideshow.INTERVAL, 3000L);
        mDefaults.put(ConfigKeys.Slideshow.SIZE, new Dimension(800,800));
        mDefaults.put(ConfigKeys.Slideshow.X_LOCATION, 25);
        mDefaults.put(ConfigKeys.Slideshow.Y_LOCATION, 25);
        mDefaults.put(ConfigKeys.Slideshow.BACKGROUND_COLOR, Color.BLACK);
        mDefaults.put(ConfigKeys.Slideshow.IS_FULLSCREEN_DEFAULT, false);
    }

    //--------------------------------------------------------- Private Fields
    
    private HashMap<Object, Object> mSettings;
    private HashMap<Object,Object> mDefaults;
    private List<PropertyChangeListener> mObservers;

}