// Constants.java
package runtime;

/**
 * A class containing only runtime constants and inner classes that contain
 * only runtime constants.
 */
public class Constants {
    
    /** Constant representing the path to the user settings file. */
    public final static String USER_SETTINGS_FILE = "Settings.xml";
    
    /** Constant representing the path to the application error log. */
    public final static String ERROR_LOG = "ErrorLog.xml";
    
    /** Constant representing the path to the user albums file. */
    public static final String USER_ALBUMS_FILE = "Albums.xml";
    
    /** Constant representing the path to the default settings file. */
    public static final String DEFAULT_SETTINGS_FILE = "resources/data/DefaultSettings.xml";
    
    /** Constant representing the path to the default albums file. */
    public static final String DEFAULT_ALBUMS_FILE = "resources/data/DefaultAlbums.xml";
    
    /**
     * A static class containing the constants that are to be used in 
     * conjunction with the UserSettings class.
     * 
     * @see runtime.UserSettings
     */
    public static class ConfigKeys {
        
        /** Key whose value contains the name of the application. */
        public final static String NAME = "App.Name";    
        
        /** Key whose value contains the name of the Look & Feel class. */
        public final static String PLAF_CLASS = "App.LookAndFeel.Class";

        /**
         * A static class contains configuration keys for actions defined
         * throughout the application.
         */
        public static class Actions {
            
            /** Key whose value contains icon path for the exit application action. */
            public final static String EXITAPP_ICON = "Action.ExitApp.Icon";
            
            /** Key whose value contains icon path for the add album action. */
            public final static String ADDALBUM_ICON = "Action.AddAlbum.Icon";
            
            /** Key whose value contains icon path for the toggle toolbar action. */
            public final static String TOGGLETOOLBAR_ICON = "Action.ToggleToolbar.Icon";
            
            /** Key whose value contains icon path for the show about application action. */
            public final static String SHOWABOUT_APP_ICON = "Action.ShowAbout.App.Icon";
            
            /** Key whose value contains icon path for the show about developers action. */
            public final static String SHOWABOUT_DEVELOPERS_ICON = "Action.ShowAbout.Developers.Icon";
            
            /** Key whose value contains icon path for the toggle gallery view action. */
            public final static String TOGGLEGALLERYVIEW_ICON = "Action.ToggleGalleryView.Icon";
            
            /** Key whose value contains icon path for the toggle details view action. */
            public final static String TOGGLEDETAILSVIEW_ICON = "Action.ToggleDetailsView.Icon";
            
            /** Key whose value contains icon path for the add photo action. */
            public final static String ADDPHOTO_ICON = "Action.AddPhoto.Icon";
            
            /** Key whose value contains icon path for the change Look&Feel action. */
            public final static String CHANGEPLAF_ICON = "Action.ChangePlaf.Icon";
            
            /** Key whose value contains icon path for the rename action. */
            public final static String RENAME_ICON = "Action.Rename.Icon";
            
            /** Key whose value contains icon path for the open slideshow action. */
            public final static String TOGGLESLIDESHOW_ICON = "Action.ToggleSlideshow.Icon";
            
            /** Key whose value contains icon path for the delete action. */
            public final static String DELETE_ICON = "Action.Delete.Icon";
            
            /** Key whose value contains icon path for the slideshow start action. */
            public final static String SLIDESHOWSTART_ICON = "Action.SlideshowStart.Icon";
            
            /** Key whose value contains icon path for the slideshow stop action. */
            public final static String SLIDESHOWSTOP_ICON = "Action.SlideshowStop.Icon";
            
            /** Key whose value contains icon path for the slideshow pause action. */
            public final static String SLIDESHOWPAUSE_ICON = "Action.SlideshowPause.Icon";
            
            /** Key whose value contains icon path for the slideshow next action. */
            public final static String SLIDESHOWNEXT_ICON = "Action.SlideshowNext.Icon";
            
            /** Key whose value contains icon path for the slideshow previous action. */
            public final static String SLIDESHOWPREVIOUS_ICON = "Action.SlideshowPrevious.Icon";
            
            /** Key whose value contains icon path for the zoom in action. */
            public final static String ZOOMIN_ICON = "Action.ZoomIn.Icon";
            
            /** Key whose value contains icon path for the zoom out action. */
            public final static String ZOOMOUT_ICON = "Action.ZoomOut.Icon";
            
            /** Key whose value contains icon path for the zoom fit action. */
            public final static String ZOOMFIT_ICON = "Action.ZoomFit.Icon";
            
            /** Key whose value contains icon path for the zoom original action. */
            public final static String ZOOMORIGINAL_ICON = "Action.ZoomOriginal.Icon";
            
            /** Key whose value contains icon path for the set album icon actionn. */
            public final static String SETALBUMICON_ICON = "Action.SetAlbumIcon.Icon";
            
            /** Key whose value contains icon path for the configure application action. */
            public final static String CONFIGUREAPP_ICON = "Action.ConfigureApplication.Icon";
        }
        
        /**
         * A static class containing constants for the setting's keys for the
         * details view.
         */
        public static class DetailsView {
            
            /** Key whose value contains the detail view's width. */
            public static final String DIVIDER_LOC = "DetailsView.Divider.Location";
            
            /** Key whose value contains the detail view's maximum width. */
            public static final String MAXIMUM_WIDTH = "DetailsView.Width.Maximum";
            
            /** Key whose value contains whether the details view is visible or not. */
            public static final String VISIBLE = "DetailsView.Visible";
        }
        
        /**
         * A static class containing constants for the setting's keys for the
         * gallery view.
         */
        public static class GalleryView {
            
            /** Constant representing THUMBNAIL_WIDTH. */
            public final static String THUMBNAIL_WIDTH = "GalleryView.Photo.ThumbnailWidth";
            
            /** Constant representing THUMBNAIL_HEIGHT. */
            public final static String THUMBNAIL_HEIGHT = "GalleryView.Photo.ThumbnailHeight";
            
            /** Constant representing SHOW_ALBUMCOUNT. */
            public final static String SHOW_ALBUMCOUNT = "GalleryView.Album.ShowCount";
            
            /** Constant representing SHOW_PHOTONAME. */
            public final static String SHOW_PHOTONAME = "GalleryView.Photo.ShowName";
            
            /** Constant representing ITEM_ICON. */
            public final static String ITEM_ICON = "GalleryView.Item.Icon";
            
            /** Constant representing PHOTO_ICON. */
            public final static String PHOTO_ICON = "GalleryView.Photo.Icon";
            
            /** Constant representing ALBUM_ICON. */
            public final static String ALBUM_ICON = "GalleryView.Album.Icon";
            
            /** Constant representing MAXIMUM_WIDTH. */
            public static final String MAXIMUM_WIDTH = "GalleryView.Width.Maximum";
            
            /** Constant representing DIVIDER_LOC. */
            public static final String DIVIDER_LOC = "GalleryView.Divider.Location";
            
            /** The visible string. */
            public static final String VISIBLE = "GalleryView.Visible";
        }

        /**
         * A static class containing constants for the setting's keys for the
         * main view (the main application user interface).
         */
        public static class MainView {
            
            /** Key whose value contains the main window's size. */
            public static final String SIZE = "MainView.Size";
            
            /** Key whose value contains the main window's title. */
            public static final String TITLE = "MainView.Title";
            
            /** Key whose value contains the main window's x location. */
            public static final String X_LOCATION = "MainView.Location.X";
            
            /** Key whose value contains the main window's y location. */
            public static final String Y_LOCATION = "MainView.Location.Y";
            
        }

        /**
         * A static class containing constants for the setting's keys for the
         * photo view.
         */
        public static class PhotoView {
            
            /** Key whose value contains the background color of the photo view. */
            public final static String BACKGROUND_COLOR = "PhotoView.Background.Color";
        
        }

        /**
         * A static class containing constants for the setting's keys for the
         * slideshow.
         */
        public static class Slideshow {
            
            /** Key whose value contains the slideshow interval. */
            public static final String INTERVAL = "Slideshow.Interval";
            
            /** Key whose value contains the size of the slideshow. */
            public static final String SIZE = "Slideshow.Size";
            
            /** Key whose value contains the background color of the slideshow. */
            public static final String BACKGROUND_COLOR = "Slideshow.BackgroundColor";
            
            /** Key whose value determines if the slideshow should be fullscreen or not. */
            public static final String IS_FULLSCREEN_DEFAULT = "Slideshow.IsFullscreenDefault";
            
            /** Key whose value contains the x location of the slideshow.. */
            public static final String X_LOCATION = "Slideshow.Location.X";
            
            /** Key whose value contains the y location of the slideshow.. */
            public static final String Y_LOCATION = "Slideshow.Location.Y";
        }

    }
    
    /**
     * A static class contains the names of the xml nodes and xml attributes
     * used in various application settings files.
     */
    public static class Xml {
        
        /**
         * A static class contains the names of the xml attributes used in 
         * various application settings files.
         */
        public static class Attributes {
            
            /** Constant representing the "Name" attribute. */
            public final static String NAME = "Name";
            
            /** Constant representing the "Description" attribute. */
            public final static String DESCRIPTION = "Description";
            
            /** Constant representing the "Path" attribute. */
            public final static String PATH = "Path";
            
            /** Constant representing the "Key" attribute. */
            public final static String KEY = "Key";
            
            /** Constant representing the "Value" attribute. */
            public final static String VALUE = "Value";
            
            /** Constant representing the "Icon" attribute. */
            public static final String ICON = "Icon";    
        }
        
        /**
         * A static class contains the names of the xml nodes used in various 
         * application settings files.
         */
        public static class Nodes {
            
            /** Constant representing the <Settings> node. */
            public final static String SETTINGS = "Settings";
            
            /** Constant representing the <PhotoGallery> node. */
            public final static String PHOTO_GALLERY = "PhotoGallery";
            
            /** Constant representing the <PhotoAlbum> node. */
            public final static String PHOTO_ALBUM = "PhotoAlbum";
            
            /** Constant representing the <Photo> node. */
            public final static String PHOTO = "Photo";
            
            /** Constant representing the <Add> node. */
            public final static String ADD = "Add";    
        }
        
    }
     
}
