// PhotoView.java
package ui.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import runtime.Application;
import runtime.Constants.ConfigKeys;
import common.ChangeType;
import common.IChangeObserver;
import common.IItemModel;
import common.IPhotoController;
import common.IPhotoModel;
import common.ISelectionObserver;
import common.IconSize;
import common.ItemChangeEvent;
import controller.PhotoController;

/**
 * Class that provides a viewing area of a photo model.
 */
public class PhotoView extends JComponent implements ISelectionObserver, IChangeObserver {

    /**
     * Default constructor that instantiates a new photo view with both it's
     * model and controller null;
     */
    public PhotoView() {
        initialize();
        mModel = null;
        mController = null;
    }
    
    /**
     * Constructor that instantiates a new photo view with the specified model
     * and controller.
     * 
     * @param controller: the controller for this view
     * @param model: the model for this view
     */
    public PhotoView(IPhotoController controller, IPhotoModel model) {
        initialize();
        mModel = model;
        mModel.registerChangeObserver((IChangeObserver)this);
        mController = controller;
        mController.setPhoto(mModel);

        if(mModel.getImage() != null) {
            repaint();
        }
    }
    
    /**
     * Gets the photo model associated with this view.
     * 
     * @return IPhotoModel: the photo model for this view
     */
    public IPhotoModel getPhoto() {
        return mModel;
    }

    /**
     * Sets the photo model for this view.
     * 
     * @param model: the new photo model for this view
     */
    public void setPhoto(IPhotoModel model) {
        mModel = model;
        mModel.registerChangeObserver((IChangeObserver)this);
        if(mController == null) {
            mController = new PhotoController(mModel);
        }
        else {
            mController.setPhoto(mModel);
        }

        if(mModel.getImage() != null) {
            repaint();
        }

    }

    
    //------------------------------------------------------ ISelectionObserver
    
    /*
     * @see common.ISelectionObserver#update(common.IItemModel)
     */
    @Override
    public void update(IItemModel selected) {
        if(selected instanceof IPhotoModel) {
//            setPhoto(selected);
        }
    }

    
    //--------------------------------------------------------- IChangeObserver
    
    /*
     * @see common.IChangeObserver#updateChange(common.ItemChangeEvent)
     */
    @Override
    public void updateChange(ItemChangeEvent e) {
        if(e.getType() == ChangeType.STATE) {
            repaint();
        }
    }
    
    
    // -------------------------------------------------------------- Overrides
    
    /*
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(mBackgroundColor);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        
        if(mModel == null) {
            return;
        }
        
        Image image = mModel.getImage();
        if(image == null)
            return;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        int offsetX = (this.getWidth() - imageWidth) / 2;
        int offsetY = (this.getHeight() - imageHeight) / 2;
        g.setColor(mBackgroundColor);
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        g.drawImage(image, offsetX, offsetY, null);
    }

    
    // -------------------------------------------------------- Private Methods
    
    /**
     * Private helper method that centralizes the initialization of certain
     * parts of the photo view.
     */
    private void initialize() {
        mBackgroundColor = Application.Settings.getColor(ConfigKeys.PhotoView.BACKGROUND_COLOR);
        setBackground(mBackgroundColor);
    }
    
    
    // --------------------------------------------------- Public Inner Classes

    /**
     * A class that defines the zoom in action. The implementation is simply
     * a call to the controller to let it know the action was requested.
     */
    public class ZoomInPhotoAction extends AbstractAction {
        
        /**
         * Default constructor that instantiates a new zoom in photo action.
         */
        public ZoomInPhotoAction() {
            putValue(NAME, "Zoom-In");
            putValue(MNEMONIC_KEY, KeyEvent.VK_PLUS);
            putValue(SMALL_ICON, Application.Settings.getIcon(ConfigKeys.Actions.ZOOMIN_ICON, IconSize.SMALL));
            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(ConfigKeys.Actions.ZOOMIN_ICON, IconSize.LARGE));
            putValue(SHORT_DESCRIPTION, "Zoom-In on the current photo.");
        }
        
        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            mController.zoomIn();
        }

    }

    /**
     * A class that defines the zoom out action. The implementation is simply
     * a call to the controller to let it know the action was requested.
     */
    public class ZoomOutPhotoAction extends AbstractAction {
        
        /**
         * Default constructor that instantiates a new zoom out photo action.
         */
        public ZoomOutPhotoAction() {
            putValue(NAME, "Zoom-Out");
            putValue(MNEMONIC_KEY, KeyEvent.VK_MINUS);
            putValue(SMALL_ICON, Application.Settings.getIcon(ConfigKeys.Actions.ZOOMOUT_ICON, IconSize.SMALL));
            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(ConfigKeys.Actions.ZOOMOUT_ICON, IconSize.LARGE));
            putValue(SHORT_DESCRIPTION, "Zoom-Out of the current photo.");
        }
        
        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent arg0) {
            mController.zoomOut();
        }

    }
    
    /**
     * A class that defines the zoom to fit action. The implementation is simply
     * a call to the controller to let it know the action was requested.
     */
    public class ZoomToFitAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new zoom to fit action.
         */
        public ZoomToFitAction() {
            putValue(NAME, "Zoom to fit screen");
            putValue(SMALL_ICON, Application.Settings.getIcon(ConfigKeys.Actions.ZOOMFIT_ICON, IconSize.SMALL));
            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(ConfigKeys.Actions.ZOOMFIT_ICON, IconSize.LARGE));
        }
        
        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            mController.zoomToFit(getSize());
        }
        
    }

    /**
     * A class that defines the zoom original action. The implementation is 
     * simply a call to the controller to let it know the action was requested.
     */
    public class ZoomOriginalAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new zoom original action.
         */
        public ZoomOriginalAction () {
            putValue(NAME, "Zoom Original");
            putValue(SMALL_ICON, Application.Settings.getIcon(ConfigKeys.Actions.ZOOMORIGINAL_ICON, IconSize.SMALL));
            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(ConfigKeys.Actions.ZOOMORIGINAL_ICON, IconSize.LARGE));
        }
        
        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent arg0) {
            mController.zoomOriginal();
        }
        
    }
    
    
    // --------------------------------------------------------- Private Fields
    
    private IPhotoController mController;
    private IPhotoModel mModel;
    private Color mBackgroundColor;
}