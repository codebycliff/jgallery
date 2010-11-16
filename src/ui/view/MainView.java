// MainView.java
package ui.view;

import io.XmlAlbumsReader;
import io.XmlAlbumsWriter;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import model.GalleryModel;
import model.PhotoModel;
import runtime.Application;
import runtime.Constants;
import runtime.Constants.ConfigKeys;
import ui.AboutApplicationDialog;
import com.javadocking.DockingManager;
import com.javadocking.dock.CompositeDock;
import com.javadocking.dock.Position;
import com.javadocking.dock.SplitDock;
import com.javadocking.dock.TabDock;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.DockingMode;
import com.javadocking.model.FloatDockModel;
import common.IAlbumModel;
import common.IChangeObserver;
import common.IGalleryController;
import common.IGalleryModel;
import common.IItemModel;
import common.IMainController;
import common.IPhotoController;
import common.IPhotoModel;
import common.ISelectionObserver;
import common.ISettingsModel;
import common.IconSize;
import common.SelectionType;
import controller.GalleryController;
import controller.PhotoController;

/**
 * Class that provides the main user interface for the application. This class
 * is responsible for putting together all the components. It retrieves the
 * available actions from all the views in order to gather all the actions in
 * a condensed location (e.g. the menubar/toolbar).
 */
public class MainView extends JFrame implements ISelectionObserver {

    /**
     * Default constructor that instantiates the main view with the specified
     * model and controller.
     * 
     * @param controller: the controller of this view
     * @param model: the model of this view
     */
    public MainView(IMainController controller, ISettingsModel model) {

        mMainController = controller;
        mSettingsModel = model;
        
        mGalleryModel = new GalleryModel(new XmlAlbumsReader(Application.Runtime.albumsFile()));
        mGalleryController = new GalleryController(mGalleryModel);
        mGalleryView = new GalleryView(mGalleryController, mGalleryModel);

        mPhotoModel = new PhotoModel();
        mPhotoController = new PhotoController(mPhotoModel);
        mPhotoView = new PhotoView(mPhotoController, mPhotoModel);

        mDetailsView = new DetailsView();
        
        initialize();
        
    }

    /**
     * Gets this view's gallery model.
     * 
     * @return IGalleryModel: this view's gallery model
     */
    public IGalleryModel getGalleryModel() {
        return mGalleryModel;
    }

    /**
     * Sets this view's gallery model to the value of the model passed in.
     * 
     * @param model: the new gallery model
     */
    public void setGalleryModel(IGalleryModel model) {
        mGalleryModel = model;
    }

    // ----------------------------------------------------- ISelectionObserver
    
    /*
     * @see common.ISelectionObserver#update(common.IItemModel)
     */
    @Override
    public void update(IItemModel selected) {
        boolean photoSelected = (selected instanceof IPhotoModel);
        if (photoSelected) {
            togglePhotoSelectedActions(true);
        }
        else {
            toggleAlbumSelectedActions(true);
        }
    }

    // -------------------------------------------------------- Private Methods

    /**
     * Private helper that initialize's this view by delegating out all the
     * responsibilities to various private helper methods.
     */
    private void initialize() {
        setupEnvironment();
        setupActions();
        setupMenu();
        setupToolBar();
        setupControls();
        setupWindow();
        setupObservers();
        unserializeState();
        
        addComponentListener(new ComponentListener() {
            
            @Override
            public void componentShown(ComponentEvent e) {
                // TODO Auto-Generated Method Stub
                
            }
            
            @Override
            public void componentResized(ComponentEvent e) {
                if(!mDetailsViewDock.isVisible()) {
                    mRightSplitPane.setDividerLocation(getWidth()-10);
                }
            }
            
            @Override
            public void componentMoved(ComponentEvent e) {
                // TODO Auto-Generated Method Stub
                
            }
            
            @Override
            public void componentHidden(ComponentEvent e) {
                // TODO Auto-Generated Method Stub
                
            }
        });
    }
    
    /**
     * Private helper method that sets up the environment of the application
     * before creating any controls or putting anything together (e.g. sets
     * the look and feel).
     */
    private void setupEnvironment() {
        String landf = Application.Settings.getString(Constants.ConfigKeys.PLAF_CLASS);
        if (landf != null) {
            try {
                UIManager.setLookAndFeel(landf);
            }
            catch (Exception e) {
                Application.dump(e);
            }
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    /**
     * Private helper method that initializes all of this view's actions, as 
     * well as gathers all the actions provided by the rest of the views.
     */
    private void setupActions() {

        // This view's actions...
        mExitApplicationAction = new ExitApplicationAction();
        mShowAboutApplicationAction = new ShowAboutApplicationAction();
        mShowAboutDevelopersAction = new ShowAboutDevelopersAction();
        mTogglePropertiesViewAction = new ToggleDetailsViewAction();
        mToggleAlbumsViewAction = new ToggleGalleryViewAction();
        mOpenSlideshowAction = new OpenSlideshowAction();
        mToggleToolBarAction = new ToggleToolBarAction();

        // Gallery view's actions...
        mAddAlbumAction = mGalleryView.new AddAlbumAction();
        mRemoveItemAction = mGalleryView.new RemoveItemAction();
        mRenameItemAction = mGalleryView.new RenameItemAction();
        mSetAlbumIconAction = mGalleryView.new SetPhotoAsAlbumIconAction();
        mAddPhotoToAlbumAction = mGalleryView.new AddPhotoToAlbumAction();

        // Photo view's actions...
        mZoomInPhotoAction = mPhotoView.new ZoomInPhotoAction();
        mZoomOutPhotoAction = mPhotoView.new ZoomOutPhotoAction();
        mZoomOriginalAction = mPhotoView.new ZoomOriginalAction();
        mZoomToFitAction = mPhotoView.new ZoomToFitAction();

    }

    /**
     * Private helper method that uses the newly initialized and gathered
     * actions to create the main menubar. 
     */
    private void setupMenu() {
        // Set up the menu bar
        this.mMenuBar = new JMenuBar();

        // File Menu
        this.mFileMenu = new JMenu("File");
        mFileMenu.setMnemonic('F');

        mFileMenu.add(mAddAlbumAction);
        mFileMenu.add(mAddPhotoToAlbumAction);
        mFileMenu.add(mRemoveItemAction);
        mFileMenu.add(new JSeparator());
        mFileMenu.add(mExitApplicationAction);

        // Edit Menu
        this.mEditMenu = new JMenu("Edit");
        mEditMenu.setMnemonic('E');
        mEditMenu.add(mRenameItemAction);
        mEditMenu.add(mSetAlbumIconAction);

        // View Menu
        this.mViewMenu = new JMenu("View");
        mViewMenu.setMnemonic('V');
        mViewMenu.add(mOpenSlideshowAction);

        JCheckBoxMenuItem showAlbumsView = new JCheckBoxMenuItem();
        showAlbumsView.setAction(mToggleAlbumsViewAction);
        showAlbumsView.setState(true);
        mViewMenu.add(showAlbumsView);

        JCheckBoxMenuItem showPropertiesView = new JCheckBoxMenuItem();
        showPropertiesView.setAction(mTogglePropertiesViewAction);
        showPropertiesView.setState(true);
        mViewMenu.add(showPropertiesView);

        JCheckBoxMenuItem showHideToolbar = new JCheckBoxMenuItem();
        showHideToolbar.setAction(mToggleToolBarAction);
        showHideToolbar.setState(true);
        mViewMenu.add(showHideToolbar);
        mViewMenu.add(new JSeparator());

        mViewMenu.add(new JSeparator());
        mViewMenu.add(mZoomToFitAction);
        mViewMenu.add(mZoomOriginalAction);
        mViewMenu.add(mZoomOutPhotoAction);
        mViewMenu.add(mZoomInPhotoAction);

//        this.mSettingsMenu = new JMenu("Settings");
//        LookAndFeelChooser chooser = new LookAndFeelChooser(this);
//        for(LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//            chooser.registerLookAndFeelInfo(info);
//        }
//        for(String key : Application.Runtime.extraLookAndFeels().keySet()) {
//            List<LookAndFeelInfo> landfs = Application.Runtime.extraLookAndFeels().get(key);
//            for(LookAndFeelInfo info : landfs) {
//                chooser.registerLookAndFeelInfo(info);
//            }
//        }
//        
//        JMenu plafMenu = chooser.getChooserMenu();
//        plafMenu.setMnemonic('L');
//        mSettingsMenu.add(plafMenu);

        // Help Menu
//        this.mHelpMenu = new JMenu("Help");
//        mHelpMenu.add(mShowAboutDevelopersAction);
//        mHelpMenu.add(mShowAboutApplicationAction);

        // Add menus to menubar and set the menubar
        mMenuBar.add(mFileMenu);
        mMenuBar.add(mEditMenu);
        mMenuBar.add(mViewMenu);
//        mMenuBar.add(mSettingsMenu);
//        mMenuBar.add(mHelpMenu);

        setJMenuBar(this.mMenuBar);

    }

    /**
     * Private helper method that adds a subset of the collection of actions
     * to the main toolbar.
     */
    private void setupToolBar() {
        mToolBar = new JToolBar();
                
        mDetailsViewToggleButton = new JToggleButton(mTogglePropertiesViewAction);
        mDetailsViewToggleButton.setText("");
        
        mGalleryViewToggleButton = new JToggleButton(mToggleAlbumsViewAction);
        mGalleryViewToggleButton.setText("");
        
        mToolBar.add(mAddAlbumAction);
        mToolBar.add(mAddPhotoToAlbumAction);
        mToolBar.add(mRemoveItemAction);
        mToolBar.add(mRenameItemAction);
        mToolBar.add(mSetAlbumIconAction);
        mToolBar.add(mGalleryViewToggleButton);
        mToolBar.add(mDetailsViewToggleButton);
        mToolBar.add(mOpenSlideshowAction);
        mToolBar.add(mZoomToFitAction);
        mToolBar.add(mZoomOriginalAction);
        mToolBar.add(mZoomOutPhotoAction);
        mToolBar.add(mZoomInPhotoAction);
        mToolBar.add(mExitApplicationAction);
        mToolBar.setVisible(true);

        // Add the ToolBar to the North Panel of MainWindow
        getContentPane().add(this.mToolBar, BorderLayout.NORTH);
    }

    /**
     * Private helper method that initializes and lays out the various views 
     * and other controls that make up the application's user interface.
     */
    private void setupControls() {

 
//         Dockable: AlbumView
        mGalleryViewDock = new TabDock();
        mGalleryViewDock.addDockable(new DefaultDockable("Dock.GalleryView",
                new JScrollPane(mGalleryView), "Photo Albums", null,
                DockingMode.ALL), new Position(0));

        // Dockable: PropertiesView
        mDetailsViewDock = new TabDock();
        mDetailsViewDock.addDockable(new DefaultDockable(
                "Dock.DetailsView", new JScrollPane(mDetailsView),
                "Details", null, DockingMode.ALL), new Position(1));

        // Dock: Left
        mLeftDock = new SplitDock();
        mRightDock = new SplitDock();
        mLeftDock.addChildDock(mGalleryViewDock, new Position(Position.CENTER));
        mRightDock.addChildDock(mDetailsViewDock, new Position(Position.BOTTOM));
        mLeftDock.setMaximumSize(new Dimension((int)(getWidth() * 0.33), getHeight()));
        mLeftDock.setPreferredSize(new Dimension((int)(getWidth()*0.25), getHeight()));
        mLeftDock.setMaximumSize(new Dimension((int)(getWidth() * 0.33), getHeight()));
        mRightDock.setPreferredSize(new Dimension((int)(getWidth()*0.25), getHeight()));

        // Setup dock model
        FloatDockModel dockModel = new FloatDockModel();
        dockModel.addOwner("MainWindow", this);
        dockModel.addRootDock("RootDock.LeftSplitDock", mLeftDock, this);
        dockModel.addRootDock("RootDock.RightSplitDock", mRightDock, this);
        DockingManager.setDockModel(dockModel);

        // Add docks to split pane
        mLeftSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mRightSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        
        mLeftSplitPane.add(mLeftDock);
        mLeftSplitPane.setRightComponent(mRightSplitPane);
        mRightSplitPane.add(new JScrollPane(mPhotoView));
        mRightSplitPane.setRightComponent(mRightDock);

        // Add splitpane as the central component for the window
        getContentPane().add(mLeftSplitPane, BorderLayout.CENTER);

    }

    /**
     * Private helper method that sets up the window's properties.
     */
    private void setupWindow() {

        // Set frame's properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(Application.Settings.getString(ConfigKeys.MainView.TITLE));
        setSize(Application.Settings.getDimension(ConfigKeys.MainView.SIZE));

        // Set the initial state for the actions...
        toggleNoItemSelectedActions();

    }

    /**
     * Private helper method that registers the various observers to their 
     * associated broadcaster.
     */
    private void setupObservers() {
        mGalleryModel.registerObserver(mPhotoView, SelectionType.PHOTO);
        mGalleryModel.registerObserver(mDetailsView);
        mGalleryModel.registerChangeObserver((IChangeObserver)mGalleryView);
        mGalleryModel.registerObserver(this);
        
        for(IItemModel model : mGalleryModel) {
            model.registerChangeObserver(mGalleryView);
            for(IItemModel photo : ((IAlbumModel)model)) {
                photo.registerChangeObserver(mGalleryView);
            }
        }
    }
   
    /**
     * Private helper method that serializes the window's state / properties
     * to the user settings.
     */
    private void serializeState() {
        Application.Settings.putValue(ConfigKeys.MainView.SIZE, getWidth() + ":" + getHeight());
        Application.Settings.putValue(ConfigKeys.MainView.X_LOCATION, "" + getBounds().x);
        Application.Settings.putValue(ConfigKeys.MainView.Y_LOCATION, "" + getBounds().y);
        Application.Settings.putValue(Constants.ConfigKeys.PLAF_CLASS, UIManager.getLookAndFeel().getClass().getName());
        Application.Settings.putValue(ConfigKeys.GalleryView.DIVIDER_LOC, ""+mLeftSplitPane.getDividerLocation());
        Application.Settings.putValue(ConfigKeys.DetailsView.DIVIDER_LOC, ""+mRightSplitPane.getDividerLocation());
    }

    /**
     * Private helper method that unserializes the window's state / properties
     * by reading the user settings and updating the controls with the read 
     * values.
     */
    private void unserializeState() {
        Dimension size = Application.Settings.getDimension(ConfigKeys.MainView.SIZE);
        int windowX = Application.Settings.getInt(ConfigKeys.MainView.X_LOCATION);
        int windowY = Application.Settings.getInt(ConfigKeys.MainView.Y_LOCATION);
        setBounds(windowX, windowY, size.width, size.height);
        
        Integer galleryViewMaxWidth = Application.Settings.getInt(ConfigKeys.GalleryView.MAXIMUM_WIDTH);
        if(galleryViewMaxWidth != null) {
            mGalleryView.setMaximumSize(new Dimension(galleryViewMaxWidth, getHeight()));
        }
        Integer leftDockDividerLoc = Application.Settings.getInt(ConfigKeys.GalleryView.DIVIDER_LOC);
        if(leftDockDividerLoc != null) {
            mLeftSplitPane.setDividerLocation(leftDockDividerLoc);
        }
        Integer rightDockDividerLoc = Application.Settings.getInt(ConfigKeys.DetailsView.DIVIDER_LOC);
        if(rightDockDividerLoc != null) {
            mRightSplitPane.setDividerLocation(rightDockDividerLoc);
        }

        Boolean detailsVisible = Application.Settings.getBoolean(ConfigKeys.DetailsView.VISIBLE);
        if(detailsVisible != null && !detailsVisible) {
            new ToggleDetailsViewAction().actionPerformed(null);
        }
        Boolean galleryVisible = Application.Settings.getBoolean(ConfigKeys.GalleryView.VISIBLE);
        if(galleryVisible != null && !galleryVisible) {
            new ToggleGalleryViewAction().actionPerformed(null);
        }

    }
    
    /**
     * Private helper method that toggles the action's enabled state based on 
     * any type of item being selected.
     * 
     * @param state: whether or not to enable the various actions.
     */
    private void toggleItemSelectedActions(boolean state) {
        mRemoveItemAction.setEnabled(state);
        mRenameItemAction.setEnabled(state);
        mDetailsViewToggleButton.setSelected(mDetailsViewDock.isVisible());
        mGalleryViewToggleButton.setSelected(mGalleryViewDock.isVisible());
    }

    /**
     * Private helper method that toggles the action's enabled state when an
     * album has been selected. 
     * 
     * @param state: Whether or not to enable the various actions.
     */
    private void toggleAlbumSelectedActions(boolean state) {
        toggleItemSelectedActions(state);
        mAddPhotoToAlbumAction.setEnabled(state);
        mOpenSlideshowAction.setEnabled(state);
    }

    /**
     * Private helper method that toggles the action's enabled state when a
     * photo has been selected.
     * 
     * @param state: whether or not to enable the various actions.
     */
    private void togglePhotoSelectedActions(boolean state) {
        toggleItemSelectedActions(state);
        mSetAlbumIconAction.setEnabled(state);
        mZoomInPhotoAction.setEnabled(state);
        mZoomOriginalAction.setEnabled(state);
        mZoomOutPhotoAction.setEnabled(state);
        mZoomToFitAction.setEnabled(state);
    }

    /**
     * Private helper method that toggles the action's enabled state when there
     * is no item selected at all.
     */
    private void toggleNoItemSelectedActions() {
        toggleItemSelectedActions(false);
        toggleAlbumSelectedActions(false);
        togglePhotoSelectedActions(false);
    }
    
    // -------------------------------------------------- Private Inner Classes

    /**
     * A class that defines the exit application action. This action is 
     * responsible for performing any last minute operations such as serializing
     * state, writing settings and gallery contents before it exits
     * the application.
     */
    public class ExitApplicationAction extends AbstractAction {

        public ExitApplicationAction() {
            putValue(NAME, "Exit Application");
            putValue(MNEMONIC_KEY, KeyEvent.VK_X);
            putValue(SMALL_ICON,mSettingsModel.getIcon(ConfigKeys.Actions.EXITAPP_ICON, IconSize.SMALL));
            putValue(LARGE_ICON_KEY,mSettingsModel.getIcon(ConfigKeys.Actions.EXITAPP_ICON, IconSize.LARGE));
            putValue(SHORT_DESCRIPTION, "Exit the application.");
        }

        @Override
        public void actionPerformed(ActionEvent ae) {

            
//            Thread serializeThread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    serializeState();        
//                }
//            }, "State Serialization");
//            
//            try {
//                serializeThread.start();
//            
//                XmlAlbumsWriter writer = new XmlAlbumsWriter(mGalleryModel);
//                writer.write(Application.Runtime.albumsFile());
//           
//                serializeThread.join();
//            }
//            catch (InterruptedException e) {
//                Application.dump(e);
//            }
            serializeState();
            XmlAlbumsWriter writer = new XmlAlbumsWriter(mGalleryModel);
            writer.write(Application.Runtime.albumsFile());
            mMainController.exitApplication();
        }

    }

    /**
     * A class that defines the show about application action. This action is 
     * responsible for displaying the about application dialog to the user.
     */
    public class ShowAboutApplicationAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new show about application
         * action.
         */
        public ShowAboutApplicationAction() {
            putValue(NAME, "About Application");
            putValue(SMALL_ICON,
                    Application.Settings
                            .getIcon(ConfigKeys.Actions.SHOWABOUT_APP_ICON, IconSize.SMALL));
            putValue(LARGE_ICON_KEY,
                    Application.Settings
                            .getIcon(ConfigKeys.Actions.SHOWABOUT_APP_ICON, IconSize.LARGE));
            putValue(SHORT_DESCRIPTION, "About this application...");
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            AboutApplicationDialog dialog = new AboutApplicationDialog();
            dialog.setVisible(true);
        }

    }

    /**
     * A class that defines the show about developer action. This action is 
     * responsible for displaying the about developer dialog to the user.
     */
    public class ShowAboutDevelopersAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new show about developers
         * action.
         */
        public ShowAboutDevelopersAction() {
            putValue(NAME, "About Developers");
            putValue(
                    SMALL_ICON,
                    Application.Settings
                            .getIcon(ConfigKeys.Actions.SHOWABOUT_DEVELOPERS_ICON, IconSize.SMALL));
            putValue(
                    LARGE_ICON_KEY,
                    Application.Settings
                            .getIcon(ConfigKeys.Actions.SHOWABOUT_DEVELOPERS_ICON, IconSize.LARGE));
            putValue(SHORT_DESCRIPTION, "Learn about the developers...");
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
//            AboutDeveloperDialog dialog = new AboutDeveloperDialog();
//            dialog.setVisible(true);
        }

    }

    /**
     * A class that defines the toggle details action. This action is 
     * responsible for toggliing the visibility of the details view.
     */
    public class ToggleDetailsViewAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new toggle details view
         * action.
         */
        public ToggleDetailsViewAction() {
            putValue(NAME, "Properties View");
            putValue(MNEMONIC_KEY, KeyEvent.VK_P);
            putValue(
                    SMALL_ICON,
                    Application.Settings
                            .getIcon(ConfigKeys.Actions.TOGGLEDETAILSVIEW_ICON, IconSize.SMALL));
            putValue(
                    LARGE_ICON_KEY,
                    Application.Settings
                            .getIcon(ConfigKeys.Actions.TOGGLEDETAILSVIEW_ICON, IconSize.LARGE));
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            mDetailsViewDock.setVisible(!mDetailsViewDock.isVisible());
            mDetailsViewToggleButton.setSelected(mDetailsViewDock.isVisible());
            CompositeDock dock = mDetailsViewDock.getParentDock();
            if(!mDetailsViewDock.isVisible()) {
                if(dock == mRightDock) {
                    mRightSplitPane.setDividerLocation(mRightSplitPane.getWidth() -5);
                }
                else {
                    mLeftSplitPane.setDividerLocation(5);
                }
            }
            else {
                if(dock == mRightDock) {
                    mRightSplitPane.setDividerLocation(mRightSplitPane.getWidth() - mDetailsViewDock.getWidth());
                }
                else {
                    mLeftSplitPane.setDividerLocation(mDetailsViewDock.getWidth());
                }
                
            }
        }

    }

    /**
     * A class that defines the toggle gallery view action. This action is 
     * responsible for toggling the visibility of the gallery view.
     */
    public class ToggleGalleryViewAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new toggle gallery view
         * action.
         */
        public ToggleGalleryViewAction() {
            putValue(NAME, "Album View");
            putValue(MNEMONIC_KEY, KeyEvent.VK_A);
            putValue(
                    SMALL_ICON,
                    Application.Settings
                            .getIcon(ConfigKeys.Actions.TOGGLEGALLERYVIEW_ICON, IconSize.SMALL));
            putValue(
                    LARGE_ICON_KEY,
                    Application.Settings
                            .getIcon(ConfigKeys.Actions.TOGGLEGALLERYVIEW_ICON, IconSize.LARGE));
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            mGalleryViewDock.setVisible(!mGalleryViewDock.isVisible());
            mGalleryViewToggleButton.setSelected(mGalleryViewDock.isVisible());
            CompositeDock dock = mGalleryViewDock.getParentDock();
            if(!mGalleryViewDock.isVisible()) {
                if(dock == mRightDock) {
                    mRightSplitPane.setDividerLocation(mRightSplitPane.getWidth() -5);
                }
                else {
                    mLeftSplitPane.setDividerLocation(5); 
                    mRightSplitPane.setDividerLocation(mRightSplitPane.getWidth() -5); 
                }
            }
            else {
                if(dock == mRightDock) {
                    mRightSplitPane.setDividerLocation(mRightSplitPane.getWidth() - mGalleryViewDock.getWidth());     
                }
                else {
                    mLeftSplitPane.setDividerLocation(mGalleryViewDock.getWidth());
                }
                
            }
        }

    }

    /**
     * A class that defines the toggle toolbar action. This action is 
     * responsible for toggling the visibility of the main toolbar.
     */
    public class ToggleToolBarAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new toggle tool bar action.
         */
        public ToggleToolBarAction() {
            putValue(NAME, "Show Toolbar");
            putValue(MNEMONIC_KEY, KeyEvent.VK_T);
            putValue(SHORT_DESCRIPTION, "Show the toolbar.");
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
//            mMainController.toggleMainToolBar();
            boolean flag = mToolBar.isVisible();
            mToolBar.setVisible(!flag);
            putValue(NAME, flag ? "Hide Toolbar" : "Show Toolbar");
        }

    }

    /**
     * A class that defines the open slideshow action. This action is 
     * responsible for creating and displaying the slideshow view to the user.
     */
    public class OpenSlideshowAction extends AbstractAction {

        private OpenSlideshowAction() {
            putValue(NAME, "Start Slideshow");
            putValue(
                    SMALL_ICON,
                    Application.Settings
                            .getIcon(ConfigKeys.Actions.TOGGLESLIDESHOW_ICON, IconSize.SMALL));
            putValue(
                    LARGE_ICON_KEY,
                    Application.Settings
                            .getIcon(ConfigKeys.Actions.TOGGLESLIDESHOW_ICON, IconSize.LARGE));
            putValue(SHORT_DESCRIPTION, "Starts the slideshow");
            putValue(SELECTED_KEY, false);
//            mDisabledComponents = new LinkedList<Component>();
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            IAlbumModel album = mGalleryModel.getLastSelectedAlbum();
            SlideshowView slideshowView = new SlideshowView(album);
            slideshowView.setVisible(true);
        }

    }

    // --------------------------------------------------------- Private Fields

    private ISettingsModel mSettingsModel;
    private IGalleryModel mGalleryModel;
    private IGalleryController mGalleryController;
    private IPhotoModel mPhotoModel;
    private IMainController mMainController;
    private IPhotoController mPhotoController;
    private GalleryView mGalleryView;
    private PhotoView mPhotoView;
    private DetailsView mDetailsView;
    private SplitDock mLeftDock;
    private SplitDock mRightDock;
    private TabDock mDetailsViewDock;
    private TabDock mGalleryViewDock;
    private JSplitPane mLeftSplitPane;
    private JSplitPane mRightSplitPane;
    private JMenuBar mMenuBar;
    private JMenu mFileMenu;
    private JMenu mEditMenu;
    private JMenu mViewMenu;
    private JMenu mSettingsMenu;
    private JMenu mHelpMenu;
    private JToolBar mToolBar;
    private JToggleButton mGalleryViewToggleButton;
    private JToggleButton mDetailsViewToggleButton;
    private Action mSetAlbumIconAction;
    private Action mOpenSlideshowAction;
    private Action mToggleAlbumsViewAction;
    private Action mTogglePropertiesViewAction;
    private Action mToggleToolBarAction;
    private Action mZoomToFitAction;
    private Action mZoomOriginalAction;
    private Action mZoomOutPhotoAction;
    private Action mZoomInPhotoAction;
    private Action mShowAboutDevelopersAction;
    private Action mShowAboutApplicationAction;
    private Action mAddAlbumAction;
    private Action mRemoveItemAction;
    private Action mRenameItemAction;
    private Action mAddPhotoToAlbumAction;
    private Action mExitApplicationAction;
 
}