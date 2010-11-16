package experimental;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import javax.swing.Icon;
import runtime.Application;
import common.IconSize;

public class UserSettings {
    
    public final static String APP_NAME = Application.Settings.getString("App.Name");    
    public final static String APP_PLAF = Application.Settings.getString("App.LookAndFeel");
    public final static Icon APP_ITEMICON = Application.Settings.getIcon("App.Item.Icon",IconSize.DEFAULT);
    public final static Icon APP_ALBUMICON = Application.Settings.getIcon("App.Defaults.Album.Icon",IconSize.DEFAULT);
    public final static Icon APP_PHOTOICON = Application.Settings.getIcon("App.Defaults.Photo.Icon",IconSize.DEFAULT);
    public final static File APP_SETTINGS_FILE = Application.Settings.getFile("Settings.xml");
    public final static int GALLERYVIEW_THUMBNAIL_WIDTH = Application.Settings.getInt("GalleryView.Thumbnail.Width");
    public final static int GALLERYVIEW_THUMBNAIL_HEIGHT = Application.Settings.getInt("GalleryView.Thumbnail.Height");
    public final static boolean GALLERYVIEW_SHOW_ALBUMCOUNT = Application.Settings.getBoolean("GalleryView.Album.ShowSize");
    public final static boolean GALLERYVIEW_SHOW_PHOTONAME = Application.Settings.getBoolean("GalleryView.Photo.ShowName");
    public final static Icon GALLERYVIEW_ITEM_ICON = Application.Settings.getIcon("GalleryView.Item.Icon",IconSize.DEFAULT);
    public final static Icon GALLERYVIEW_PHOTO_ICON = Application.Settings.getIcon("GalleryView.Photo.Icon",IconSize.DEFAULT);
    public final static Icon GALLERYVIEW_ALBUM_ICON = Application.Settings.getIcon("GalleryView.Album.Icon",IconSize.DEFAULT); 
    public static final Dimension MAINVIEW_SIZE = Application.Settings.getDimension("MainView.Size");
    public static final String MAINVIEW_TITLE = Application.Settings.getString("MainView.Title");
    public static final long SLIDESHOW_INTERVAL = Application.Settings.getLong("Slideshow.Interval");
    public final static Icon ACT_EXITAPP_SMALLICON = Application.Settings.getIcon("Action.ExitApp.Icon",IconSize.DEFAULT);
    public final static Icon ACT_ADDALBUM_SMALLICON = Application.Settings.getIcon("Action.AddAlbum.Icon",IconSize.DEFAULT);
    public final static Icon ACT_TOGGLETOOLBAR_SMALLICON = Application.Settings.getIcon("Action.ToggleToolbar.Icon",IconSize.DEFAULT);
    public final static Icon ACT_ZOOMFIT_SMALLICON = Application.Settings.getIcon("Action.ZoomFit.Icon",IconSize.DEFAULT);
    public final static Icon ACT_ZOOMORIGINAL_SMALLICON = Application.Settings.getIcon("Action.ZoomOriginal.Icon",IconSize.DEFAULT);
    public final static Icon ACT_SHOWABOUTAPP_SMALLICON = Application.Settings.getIcon("Action.ShowAboutApp.Icon",IconSize.DEFAULT);
    public final static Icon ACT_SHOWABOUTDEVELOPERS_SMALLICON = Application.Settings.getIcon("Action.ShowAboutDevelopers.Icon",IconSize.DEFAULT);
    public final static Icon ACT_OPENPHOTO_SMALLICON = Application.Settings.getIcon("Action.OpenPhoto.Icon",IconSize.DEFAULT);
    public final static Icon ACT_TOGGLEALBUMVIEW_SMALLICON = Application.Settings.getIcon("Action.ToggleAlbumView.Icon",IconSize.DEFAULT);
    public final static Icon ACT_TOGGLEPROPERTIESVIEW_SMALLICON = Application.Settings.getIcon("Action.TogglePropertiesView.Icon",IconSize.DEFAULT);
    public final static Icon ACT_ADDPHOTOTOALBUM_SMALLICON = Application.Settings.getIcon("Action.AddPhotoToAlbum.Icon",IconSize.DEFAULT);
    public final static Icon ACT_CHANGELANDF_SMALLICON = Application.Settings.getIcon("Action.ChangeLandF.Icon",IconSize.DEFAULT);
    public final static Icon ACT_ZOOMIN_SMALLICON = Application.Settings.getIcon("Action.ZoomIn.Icon",IconSize.DEFAULT);
    public final static Icon ACT_ZOOMOUT_SMALLICON = Application.Settings.getIcon("Action.ZoomOut.Icon",IconSize.DEFAULT);
    public final static Icon ACT_RENAMEPHOTO_SMALLICON = Application.Settings.getIcon("Action.RenamePhoto.Icon",IconSize.DEFAULT);
    public final static Icon ACT_REMOVEPHOTO_SMALLICON = Application.Settings.getIcon("Action.DeletePhotoFromAlbum.Icon",IconSize.DEFAULT);
    public final static Icon ACT_SETICON_SMALLICON = Application.Settings.getIcon("Action.SetAlbumIcon.Icon",IconSize.DEFAULT);
    public final static Icon ACT_STARTSLIDESHOW_SMALLICON = Application.Settings.getIcon("Action.StartSlideshow.Icon",IconSize.DEFAULT);
    public final static Icon ACT_DELETEALBUM_SMALLICON = Application.Settings.getIcon("Action.DeleteAlbum.Icon",IconSize.DEFAULT);
    public final static Color PHOTOVIEW_BACKGROUND_COLOR = Application.Settings.getColor("PhotoView.Background.Color");

}
