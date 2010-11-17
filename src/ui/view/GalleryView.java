// GalleryView.java
package ui.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeCellRenderer;
import model.DefaultItemModel;
import runtime.Application;
import runtime.Constants.ConfigKeys;
import common.IAlbumModel;
import common.IChangeObserver;
import common.IGalleryController;
import common.IGalleryModel;
import common.IItemModel;
import common.IPhotoModel;
import common.IconSize;
import common.ItemChangeEvent;

/**
 * Class that provides a view of the photo gallery as a whole in the form of a
 * tree. The root node of the tree is invisible and each child of the root is
 * an album model in this gallery. Each album model node has a child for each of
 * the photo models in it's collection of photos.
 */
public class GalleryView extends JTree implements  IChangeObserver {

    /**
     * Constructor that instantiates an new instance of gallery view with
     * the specified model and controller.
     * 
     * @param controller: the controller for this view
     * @param model: the model for this view
     */
    public GalleryView(IGalleryController controller, IGalleryModel model) {
        mController = controller;
        mModel = model;
        mPopupMenu = new JPopupMenu();
        mPopupMenu.addPopupMenuListener(new ItemNodePopupListener());
        
        initialize();
        
        setCellRenderer(new ItemNodeRenderer());
        addMouseListener(new MousePopupListener());
        addTreeSelectionListener(new ItemNodeSelectionListener());
    }
    
    //--------------------------------------------------------------- IItemView

    /**
     * Gets this views gallery model.
     * 
     * @return IGalleryModel: this view's gallery model
     */
    public IGalleryModel getGalleryModel() {
        return mModel;
    }

    /**
     * Sets this view's gallery model.
     * 
     * @param model: the new gallery model to be associated with this view
     */
    public void setGalleryModel(IGalleryModel model) {
        mModel = model;
    }

    //--------------------------------------------------------- IChangeObserver

    /*
     * @see common.IChangeObserver#updateChange(common.ItemChangeEvent)
     */
    @Override
    public void updateChange(ItemChangeEvent e) {
        
        DefaultMutableTreeNode lastSelectedNode = (DefaultMutableTreeNode) getLastSelectedPathComponent();
        Object lastSelectedObject = null;
        if(lastSelectedNode != null) {
             lastSelectedObject = lastSelectedNode.getUserObject();    
        }
        
        switch(e.getType()) {
        case ADDITION:
            
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(e.getChange());
            if(e.getChange() instanceof IAlbumModel && e.getItem() == mModel) {
                DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode)mTreeModel.getRoot();
                mTreeModel.insertNodeInto(newNode, rootNode, rootNode.getChildCount());
            }
            else if(e.getItem() == lastSelectedObject) {
                lastSelectedNode.insert(newNode, lastSelectedNode.getChildCount());
                mTreeModel.reload(lastSelectedNode);
            }
            else {
                DefaultMutableTreeNode lastSelectedParent = 
                    (DefaultMutableTreeNode) lastSelectedNode.getParent();
                if(e.getItem() == ((IItemModel)lastSelectedParent.getUserObject())) {
                    lastSelectedParent.insert(newNode, lastSelectedParent.getChildCount());
                    mTreeModel.reload(lastSelectedParent);
                }
            }
            break;
        case RENAMED:
            if(e.getChange() == lastSelectedObject) {
                mTreeModel.reload(lastSelectedNode);
            }
            break;
        case REMOVAL:
            if(e.getChange() == lastSelectedObject) {
                mTreeModel.removeNodeFromParent(lastSelectedNode);
            }
            break;
        }
    }
    
    //---------------------------------------------------- Public Inner Classes

    /**
     * A class that defines the add album action. The implementation is simply
     * a call to the controller to let it know the action was requested.
     */
    public class AddAlbumAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new adds the album action.
         */
        public AddAlbumAction() {
            putValue(NAME, "Add Album");
            putValue(MNEMONIC_KEY, KeyEvent.VK_A);
            putValue(SMALL_ICON, Application.Settings.getIcon(ConfigKeys.Actions.ADDALBUM_ICON, IconSize.SMALL));
            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(ConfigKeys.Actions.ADDALBUM_ICON, IconSize.LARGE));
            putValue(SHORT_DESCRIPTION, "Add an album of images");
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            mController.addAlbum();
            
        }

    }

    /**
     * A class that defines the remove item action. The implementation is simply
     * a call to the controller to let it know the action was requested.
     */
    public class RemoveItemAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new removes the item action.
         */
        public RemoveItemAction() {
            putValue(NAME, "Delete");
            putValue(MNEMONIC_KEY, KeyEvent.VK_DELETE);
            putValue(SMALL_ICON, Application.Settings.getIcon(ConfigKeys.Actions.DELETE_ICON, IconSize.SMALL));
            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(ConfigKeys.Actions.DELETE_ICON, IconSize.LARGE));
            putValue(SHORT_DESCRIPTION, "Delete the selected item.");
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            mController.removeLastSelected();
        }

    }

    /**
     * A class that defines the rename item action. The implementation is simply
     * a call to the controller to let it know the action was requested.
     */
    public class RenameItemAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new rename item action.
         */
        RenameItemAction() {
            putValue(NAME, "Rename");
            putValue(MNEMONIC_KEY, KeyEvent.VK_F2);
            putValue(SMALL_ICON, Application.Settings.getIcon(ConfigKeys.Actions.RENAME_ICON, IconSize.SMALL));
            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(ConfigKeys.Actions.RENAME_ICON, IconSize.LARGE));
            putValue(SHORT_DESCRIPTION, "Rename the selected item.");
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            mController.renameLastSelected();
        }

    }

    /**
     * A class that defines the set photo as album icon action. The 
     * implementation is simply a call to the controller to let it know the 
     * action was requested.
     */
    public class SetPhotoAsAlbumIconAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new sets the photo as album
         * icon action.
         */
        SetPhotoAsAlbumIconAction() {
            putValue(NAME, "Set as Album Icon");
            putValue(MNEMONIC_KEY, KeyEvent.VK_E);
            putValue(SMALL_ICON, Application.Settings.getIcon(ConfigKeys.Actions.SETALBUMICON_ICON, IconSize.SMALL));
            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(ConfigKeys.Actions.SETALBUMICON_ICON, IconSize.LARGE));
            putValue(SHORT_DESCRIPTION, "Set this image as the album's icon.");
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            mController.setIconToLastSelected();
        }

    }

    /**
     * A class that defines the add photo to album action. The implementation is 
     * simply a call to the controller to let it know the action was requested.
     */
    public class AddPhotoToAlbumAction extends AbstractAction {

        /**
         * Default constructor that instantiates a new adds the photo to album
         * action.
         */
        AddPhotoToAlbumAction() {
            putValue(NAME, "Add Photo");
            putValue(MNEMONIC_KEY, KeyEvent.VK_E);
            putValue(SMALL_ICON, Application.Settings.getIcon(ConfigKeys.Actions.ADDPHOTO_ICON, IconSize.SMALL));
            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(ConfigKeys.Actions.ADDPHOTO_ICON, IconSize.LARGE));
            putValue(SHORT_DESCRIPTION, "Add photo to currently selected album.");
        }

        /*
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            mController.addPhoto();
        }

    }

    //--------------------------------------------------- Private Inner Classes

    /**
     * The listener interface for receiving itemNodeSelection events. The class
     * that is interested in processing a itemNodeSelection event implements
     * this interface, and the object created with that class is registered with
     * a component using the component's
     * <code>addItemNodeSelectionListener<code> method. When
     * the itemNodeSelection event occurs, that object's appropriate
     * method is invoked.
     * 
     * This class defines the course of action for when a tree node is selected
     * in the user interface. It's implementation is simply a call this 
     * instance's gallery model's selectItem() method to notify it of which
     * item is currently selected.
     * 
     * @see ItemNodeSelectionEvent
     */
    private class ItemNodeSelectionListener implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            DefaultMutableTreeNode node = 
                (DefaultMutableTreeNode)getLastSelectedPathComponent();
            
            if (node == null) {
                return;
            }

            IItemModel selectedItem = (IItemModel) node.getUserObject();
            mController.selectItem(selectedItem);
        }

    }
    
    /**
     * Class that defines how each item model in the gallery model should be
     * rendered. Each item is rendered as a tree node represented by a JLabel
     * with varying properties.
     */
    private class ItemNodeRenderer extends JLabel implements TreeCellRenderer {

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean selected, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {

            Object selectedItem = ((DefaultMutableTreeNode) value).getUserObject();
            // Root node, which should appear invisible...
            if (row == 0) {
                setText("");
                setIcon(null);
                return this;
            }

            // Album or Photo, so display its icon...
            if(selectedItem instanceof DefaultItemModel) {
                if(selectedItem instanceof IPhotoModel) {
                    IPhotoModel photoModel = (IPhotoModel)selectedItem;
                    setIcon(photoModel.getIcon());
                    if(mDisplayPhotoName) {
                        setText(photoModel.getName());
                    }
                }
                else if(selectedItem instanceof IAlbumModel) {
                    IAlbumModel albumModel = (IAlbumModel)selectedItem;
                    setIcon(albumModel.getIcon());
                    if(mDisplayAlbumCount) {
                        setText(albumModel.getName() + " (" + albumModel.getCount() + ")"); 
                    }
                    else {
                        setText(albumModel.getName());
                    }
                }
                setToolTipText(((DefaultItemModel) selectedItem).getDescription());
            }
            return this;
        }

    }

    /**
     * The listener interface for receiving itemNodePopup events. The class that
     * is interested in processing a itemNodePopup event implements this
     * interface, and the object created with that class is registered with a
     * component using the component's <code>addItemNodePopupListener<code> 
     * method. When the itemNodePopup event occurs, that object's appropriate
     * method is invoked.
     * 
     * @see ItemNodePopupEvent
     */
    private class ItemNodePopupListener implements PopupMenuListener {

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
            mPopupMenu.removeAll();
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            mPopupMenu.removeAll();
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            
            mPopupMenu.add(new RemoveItemAction());
            mPopupMenu.add(new RenameItemAction());
            
            if(mModel.getLastSelected() instanceof IPhotoModel) {
                mPopupMenu.add(new AddPhotoToAlbumAction());
                mPopupMenu.add(new SetPhotoAsAlbumIconAction());
            }
            else {
                mPopupMenu.add(new AddAlbumAction());
            }
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
                mPopupMenu.show(GalleryView.this, e.getX(), e.getY());
            }
        }
    
    }
    
    
    // -------------------------------------------------------- Private Methods
    
    /**
     * Private helper method that initializes the tree model with the contents
     * of the gallery models contents.
     */
    private void initialize() {
        
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();

        for (IAlbumModel album : mModel) {            
            DefaultMutableTreeNode albumNode = new DefaultMutableTreeNode(album);
            if (album.getCount() > 0) {
                for (IItemModel photo : album) {
                    DefaultMutableTreeNode imageNode = new DefaultMutableTreeNode(photo);
                    albumNode.add(imageNode);
                }
            }
            else {
                DefaultMutableTreeNode noImageNode = new DefaultMutableTreeNode(
                        new JLabel("[no images]"));
                albumNode.add(noImageNode);
            }
            rootNode.add(albumNode);
        }

        mTreeModel = new DefaultTreeModel(rootNode);
        setModel(mTreeModel);
    }
   
    //--------------------------------------------------------- Private Fields
    
    private IGalleryController mController;
    private IGalleryModel mModel;
    private DefaultTreeModel mTreeModel;
    private JPopupMenu mPopupMenu;
    private final boolean mDisplayPhotoName = Application.Settings.getBoolean(ConfigKeys.GalleryView.SHOW_PHOTONAME);
    private final boolean mDisplayAlbumCount = Application.Settings.getBoolean(ConfigKeys.GalleryView.SHOW_ALBUMCOUNT);
}