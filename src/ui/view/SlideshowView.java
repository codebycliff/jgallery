// SlideshowView.java
package ui.view;

import java.awt.BorderLayout;
import java.awt.DisplayMode;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import model.AlbumModel;
import runtime.Application;
import runtime.Constants.ConfigKeys;
import common.IAlbumModel;
import common.IPhotoModel;
import common.IconSize;


/**
 * Class that provides an implementation of a photo slideshow.
 */
public class SlideshowView extends JFrame implements  Runnable {

    //TEMP: Move all these to the actionMap() 
    public StartSlideshowAction StartAction = new StartSlideshowAction();
    public StopSlideshowAction StopAction = new StopSlideshowAction();
    public PauseSlideshowAction PauseAction = new PauseSlideshowAction();
    public SlideshowNextAction NextAction = new SlideshowNextAction();
    public SlideshowPreviousAction PreviousAction = new SlideshowPreviousAction();
    public ToggleFullscreenAction ExitFullscreen = new ToggleFullscreenAction(ToggleFullscreenAction.EXIT_FULLSCREEN);
    public ToggleFullscreenAction EnterFullscreen = new ToggleFullscreenAction(ToggleFullscreenAction.EXIT_FULLSCREEN);
    public static GraphicsDevice GraphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    
    /**
     * Constructor that instantiates a new slideshow and initializes its model
     * to the model passed in.
     * 
     * @param model: the album model containing the photos to be displayed in
     *      this slideshow.
     */
    public SlideshowView(IAlbumModel model) {
        super(GraphicsDevice.getDefaultConfiguration());
        mModel = model;

        // Setup the view...
        initialize();

        // Display the first image...
        next();
    }

    /**
     * Constructor that instantiates a new slideshow that will display the
     * collection of photos passed in.
     * 
     * @param photos: the collection of photos to be displayed by this 
     *      slideshow
     */
    public SlideshowView(Collection<IPhotoModel> photos) {
        super(GraphicsDevice.getDefaultConfiguration());
        mModel = new AlbumModel("Assorted Collection", photos);

        // Set up the view...
        initialize();

        // Display the first image...
        next();
    }

    /**
     * Starts the slideshow or resumes it if it was previous pasued.
     */
    public void start() {
        if (mIsPaused) {
            resume();
            return;
        }
        else if (mHasRanPreviously) {
            mThread = new Thread(this, "SlideShow");
        }
        mIsRunning = true;
        mHasRanPreviously = true;
        mThread.start();
    }

    /**
     * Moves to the next photo in the slideshow's collection of photos.
     */
    public void next() {
        mCurrentIndex = (mCurrentIndex + 1) % mImages.size();
        repaint();
    }

    /**
     * Moves to the previous photo in the slideshow's collection of photos.
     */
    public void previous() {
        mCurrentIndex = (mCurrentIndex - 1) % mImages.size();
        repaint();
    }

    /**
     * Pauses the slideshow on the current photo being displayed.
     */
    public void pause() {
        setPaused(true);
    }

    /**
     * Stops the slideshow effectively bringing the slideshow to an end.
     */
    public void stop() {
        mIsRunning = false;
        mThread.interrupt();
    }

    /**
     * Checks if there is another photo left in this slideshow's collection of
     * photos.
     * 
     * @return true: if there is another photo to display; false otherwise
     */
    public boolean hasNext() {
        return true;
    }

    /**
     * Checks if there is a previous photo in this slideshow's collection of
     * photos.
     * 
     * @return true: if there is a previous photo; false otherwise
     */
    public boolean hasPrevious() {
        return true;
    };

    /**
     * Checks if the slideshow is currently running or not.
     * 
     * @return true: if the slideshow is running; false otherwise
     */
    public boolean isRunning() {
        return mIsRunning;
    }
    
    /**
     * Checks if the thread this slideshow is executing on is alive or not.
     * 
     * @return true: if the thread this slideshow is executing on is alive; 
     *      false otherwise
     */
    public boolean isAlive() {
        return mThread.isAlive();
    }

    /**
     * Checks if the slideshow is currently paused or not.
     * 
     * @return true: if the slideshow is paused; false otherwise
     */
    public boolean isPaused() {
        return mIsPaused;
    }

    /**
     * Resumes the slideshow if it has previously been paused.
     */
    public void resume() {
        setPaused(false);
    }

    /**
     * Checks if slideshow is currently being displayed as fullscreen or not.
     * 
     * @return true: if the slideshow is in fullscreen view mode; false otherwise
     */
    public boolean isFullscreen() {
        return mIsFullScreen;
    }

    /**
     * Determines if the user's computer supports the slideshow's fullscreen mode.
     * 
     * @return true: if fullscreen mode is supported; false otherwise
     */
    public boolean supportsFullscreen() {
        return GraphicsDevice.isFullScreenSupported();
    }

    /**
     * Sets whether or not this slideshow is to be displayed in fullscreen mode
     * or not.
     * 
     * @param value: whether or not to display this slideshow in fullscreen
     */
    public void setFullscreen(boolean value) {
        mIsFullScreen = value;
        if (mIsFullScreen) {
            if (supportsFullscreen()) {
                GraphicsDevice.setFullScreenWindow(this);
                setUndecorated(true);
                setIgnoreRepaint(true);
            }
        }
        else {
            DisplayMode displayMode = GraphicsDevice.getDisplayMode();
            int xoff = (displayMode.getWidth()- getWidth()) / 2;
            int yoff = (displayMode.getHeight() - getHeight()) / 2;
            setBounds(xoff, yoff, getWidth(), getHeight());
            setUndecorated(false);
            setIgnoreRepaint(false);
        }
    }

    //--------------------------------------------------------------- Overrides

    /*
     * @see java.lang.Runnable#run()
     */
    public void run() {
        try {
            loop();
        }
        catch (Exception e) {
            Application.dump(e);
        }
    }

    /*
     * @see java.awt.Window#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Image image = mImages.get(mCurrentIndex);

        if (image == null) {
            throw new NullPointerException(
                    "Image was unexpectedly null! Index in image array: "
                            + mCurrentIndex);
        }

        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        
        int offsetX = (this.getWidth() - imageWidth) / 2;
        int offsetY = ((getHeight() - mToolBar.getHeight()) - imageHeight) / 2;
        g.drawImage(image, offsetX, offsetY, getBackground(), null);
    }

    // -------------------------------------------------------- Private Methods
    
    /**
     * Private helper method that contains the main slideshow loop that is 
     * executed in the slideshow's thread.
     */
    private void loop() {
        while (mIsRunning) {
            try {
                waitWhilePaused();
                Thread.sleep(mInterval); // 10 frames per second
                next();
            }
            catch (InterruptedException x) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Private helper to set whether or not the slideshow is paused.
     * 
     * @param paused: whether or not the slideshow is to be paused
     */
    private void setPaused(boolean paused) {
        synchronized (mPauseLock) {
            if (mIsPaused != paused) {
                mIsPaused = paused;
                mPauseLock.notifyAll();
            }
        }
    }
    
    /**
     * Private helper method that will wait to return until the the slideshow
     * is unpaused through the setPaused(boolean) method.
     * 
     * @throws InterruptedException: if the the thread this slideshow is
     *      executing on is interrupted while waiting for the pause lock to lift.
     */
    private void waitWhilePaused() throws InterruptedException {
        synchronized (mPauseLock) {
            while (mIsPaused) {
                mPauseLock.wait();
            }
        }
    }

    /**
     * Private helper method to toggle the actions enabled state based on the
     * current state of the slideshow and it's index in the collection of photos.
     */
    private void toggleActions() {
        StartAction.setEnabled((!isRunning() || isPaused()) && hasNext());
        StopAction.setEnabled((isRunning() || isPaused()) && hasNext());
        PauseAction.setEnabled((isRunning() && !isPaused()) && hasNext());
        NextAction.setEnabled(hasNext());
        PreviousAction.setEnabled(hasPrevious());
//        StartAction.setEnabled(true);
//        StopAction.setEnabled(true);
//        PauseAction.setEnabled(true);
//        NextAction.setEnabled(true);
//        PreviousAction.setEnabled(true);
    }

    /**
     * Private helper method that sets up and lays out the slideshow's 
     * components.
     */
    private void initialize() {

        // Initialize members...
        mHasRanPreviously = false;
        mCurrentIndex = 0;
        mIsRunning = false;
        mIsPaused = false;
        mThread = new Thread(this, "SlideShow");
        mInterval = Application.Settings.getLong(ConfigKeys.Slideshow.INTERVAL);
        mIsFullScreen = Application.Settings.getBoolean(ConfigKeys.Slideshow.IS_FULLSCREEN_DEFAULT);
        
        // Create list of images from the photo models...
        mImages = new LinkedList<Image>();
        for (IPhotoModel photo : mModel) {
            mImages.add(photo.getImage());
        }

        // Set up the toolbar with the actions...
        mToolBar = new JToolBar();
        JToggleButton startToggleButton = new JToggleButton();
        startToggleButton.setAction(StartAction);
        mToolBar.add(StartAction);
        mToolBar.add(StopAction);
        mToolBar.add(PauseAction);
        mToolBar.add(PreviousAction);
        mToolBar.add(NextAction);
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(mToolBar);
        getContentPane().add(panel, BorderLayout.SOUTH);
        toggleActions();

        // Create and register the popup menu to escape/toggle fullscreen...
        mPopUpMenu = new JPopupMenu();
        mPopUpMenu.addPopupMenuListener(new ToggleFullscreenListener());
        addMouseListener(new MousePopupListener());

        // Register the key listener for the escape key to close slideshow...
        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                hideIfEscapeKey(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                hideIfEscapeKey(e);
            }

            private void hideIfEscapeKey(KeyEvent e) {
                int escapeKey = KeyEvent.VK_ESCAPE;
                if (e.getKeyCode() == escapeKey) {
                    StopAction.actionPerformed(null);
                }
            }
        });

        // Set up frame's properties
        setFullscreen(mIsFullScreen);
        setBackground(Application.Settings.getColor(ConfigKeys.Slideshow.BACKGROUND_COLOR));
        setSize(Application.Settings.getDimension(ConfigKeys.Slideshow.SIZE));
        
    }
    
    
    // ---------------------------------------------------------- Inner Classes

    /**
     * A class that defines the start slideshow action. The implementation is 
     * simply a call to the associated member, followed by a call to 
     * toggleActions() to update the rest of the action's enabled state.
     */
    public class StartSlideshowAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new start slideshow action.
         */
        public StartSlideshowAction() {
            putValue(NAME, "Start Slideshow");
            putValue(SMALL_ICON, Application.Settings.getIcon(
                    ConfigKeys.Actions.SLIDESHOWSTART_ICON, IconSize.LARGE));
            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(
                    ConfigKeys.Actions.SLIDESHOWSTART_ICON, IconSize.LARGE));
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            start();
            toggleActions();
        }

    }

    /**
     * A class that defines the stop slideshow action. The implementation is 
     * simply a call to the associated member, followed by a call to 
     * toggleActions() to update the rest of the action's enabled state.
     */
    public class StopSlideshowAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new stop slideshow action.
         */
        public StopSlideshowAction() {
            putValue(NAME, "Stop Slideshow");
            putValue(SMALL_ICON, Application.Settings.getIcon(
                    ConfigKeys.Actions.SLIDESHOWSTOP_ICON, IconSize.LARGE));
            putValue(SMALL_ICON, Application.Settings.getIcon(
                    ConfigKeys.Actions.SLIDESHOWSTOP_ICON, IconSize.LARGE));
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            stop();
            setVisible(false);
        }

    }

    /**
     * A class that defines the pause slideshow action. The implementation is 
     * simply a call to the associated member, followed by a call to 
     * toggleActions() to update the rest of the action's enabled state.
     */
    public class PauseSlideshowAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new pause slideshow action.
         */
        public PauseSlideshowAction() {
            putValue(NAME, "Pause Slideshow");
            putValue(SMALL_ICON, Application.Settings.getIcon(
                    ConfigKeys.Actions.SLIDESHOWPAUSE_ICON, IconSize.LARGE));
            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(
                    ConfigKeys.Actions.SLIDESHOWPAUSE_ICON, IconSize.LARGE));
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (isPaused()) {
                resume();
            }
            else {
                pause();
            }
            toggleActions();
        }

    }

    /**
     * A class that defines the toggle slideshow fullscreen action. The 
     * implementation is simply a call to the associated member, followed by a 
     * call to toggleActions() to update the rest of the action's enabled state.
     */
    public class ToggleFullscreenAction extends AbstractAction {

        /** Constant representing ENTER_FULLSCREEN. */
        public static final String ENTER_FULLSCREEN = "Enter Fullscreen Mode";
        
        /** Constant representing EXIT_FULLSCREEN. */
        public static final String EXIT_FULLSCREEN = "Exit Fullscreen Mode";

        /**
         * Constructor that instantiates a new toggle fullscreen action with
         * it's name set to the value of the argument passed in.
         * 
         * @param name the name
         */
        public ToggleFullscreenAction(String name) {
            putValue(NAME, name);
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            if (getValue(NAME).toString().compareTo(ENTER_FULLSCREEN) == 0) {
                setFullscreen(true);
                putValue(NAME, EXIT_FULLSCREEN);
            }
            else {
                setFullscreen(false);
                putValue(NAME, ENTER_FULLSCREEN);
            }

        }

    }

    /**
     * A class that defines the slideshow next action. The implementation is 
     * simply a call to the associated member, followed by a call to 
     * toggleActions() to update the rest of the action's enabled state.
     */
    public class SlideshowNextAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new slideshow next action.
         */
        public SlideshowNextAction() {
            putValue(NAME, "Stop Slideshow");
            putValue(SMALL_ICON, Application.Settings.getIcon(
                    ConfigKeys.Actions.SLIDESHOWNEXT_ICON, IconSize.LARGE));
            putValue(SMALL_ICON, Application.Settings.getIcon(
                    ConfigKeys.Actions.SLIDESHOWNEXT_ICON, IconSize.LARGE));
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            next();
            toggleActions();
        }

    }
    
    /**
     * A class that defines the slideshow previous action. The implementation is 
     * simply a call to the associated member, followed by a call to 
     * toggleActions() to update the rest of the action's enabled state.
     */
    public class SlideshowPreviousAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new slideshow previous
         * action.
         */
        public SlideshowPreviousAction() {
            putValue(NAME, "Stop Slideshow");
            putValue(SMALL_ICON, Application.Settings.getIcon(
                    ConfigKeys.Actions.SLIDESHOWPREVIOUS_ICON, IconSize.LARGE));
            putValue(SMALL_ICON, Application.Settings.getIcon(
                    ConfigKeys.Actions.SLIDESHOWPREVIOUS_ICON, IconSize.LARGE));
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            previous();
            toggleActions();
        }

    }

    /**
     * The listener interface for receiving mousePopup events. The class that is
     * interested in processing a mousePopup event implements this interface,
     * and the object created with that class is registered with a component
     * using the component's <code>addMousePopupListener<code> method. When
     * the mousePopup event occurs, that object's appropriate
     * method is invoked.
     * 
     * @see MousePopupEvent
     */
    private class MousePopupListener extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            checkPopup(e);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            checkPopup(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            checkPopup(e);
        }

        /**
         * Private helper that encapsulates the same action for all the mouse
         * events defined by {@link MousePopupListener}.
         * 
         * @param e: the mouse event associated with the mouse click
         */
        private void checkPopup(MouseEvent e) {
            if (e.isPopupTrigger()) {
                mPopUpMenu.show(SlideshowView.this, e.getX(), e.getY());
            }
        }

    }

    /**
     * The listener interface for receiving toggleFullscreen events. The class
     * that is interested in processing a toggleFullscreen event implements this
     * interface, and the object created with that class is registered with a
     * component using the component's
     * <code>addToggleFullscreenListener<code> method. When
     * the toggleFullscreen event occurs, that object's appropriate
     * method is invoked.
     * 
     * @see ToggleFullscreenEvent
     */
    private class ToggleFullscreenListener implements PopupMenuListener {

        /**
         * Default constructor that instantiates a new toggle fullscreen
         * listener.
         */
        public ToggleFullscreenListener() {
            mExitSlideshowAction = new StopSlideshowAction();
            mExitSlideshowAction.putValue(Action.NAME, "Exit Slideshow");
            mExitSlideshowAction.putValue(Action.SMALL_ICON,
                    Application.Settings.getIcon(
                            ConfigKeys.Actions.EXITAPP_ICON, IconSize.SMALL));
            mExitSlideshowAction.putValue(Action.LARGE_ICON_KEY,
                    Application.Settings.getIcon(
                            ConfigKeys.Actions.EXITAPP_ICON, IconSize.LARGE));
            mEnterFullscreenAction = new ToggleFullscreenAction(
                    ToggleFullscreenAction.ENTER_FULLSCREEN);
            mExitFullscreenAction = new ToggleFullscreenAction(
                    ToggleFullscreenAction.EXIT_FULLSCREEN);
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            mPopUpMenu.removeAll();
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            mPopUpMenu.removeAll();
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            if (mIsFullScreen) {
                mPopUpMenu.add(mExitFullscreenAction);
            }
            else {
                mPopUpMenu.add(mEnterFullscreenAction);
            }
            mPopUpMenu.add(mExitSlideshowAction);
        }

        private StopSlideshowAction mExitSlideshowAction;
        private ToggleFullscreenAction mEnterFullscreenAction;
        private ToggleFullscreenAction mExitFullscreenAction;
    }

    
    // --------------------------------------------------------- Private Fields

    private IAlbumModel mModel;
    private List<Image> mImages;
    private Thread mThread;
    private volatile long mInterval;
    private volatile boolean mIsRunning;
    private volatile boolean mIsPaused;
    private volatile int mCurrentIndex;
    private volatile boolean mHasRanPreviously;
    private volatile boolean mIsFullScreen;
    private JToolBar mToolBar;
    private JPopupMenu mPopUpMenu;
    private final Object mPauseLock = new Object();
    
}
