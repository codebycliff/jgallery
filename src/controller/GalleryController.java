// GalleryController.java
package controller;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import model.AlbumModel;
import model.PhotoModel;
import common.IAlbumModel;
import common.IGalleryController;
import common.IGalleryModel;
import common.IItemModel;
import common.IPhotoModel;

/**
 * A concrete class implementing the IGalleryController interface. This class
 * contains the implementation of the actions that can be performed on the
 * gallery. 
 */
public class GalleryController implements IGalleryController {

    /**
     * Constructor that instantiates a new gallery controller with it's model
     * set to the value of the argument passed in.
     * 
     * @param model: the model of the new controller instantiated
     */
    public GalleryController(IGalleryModel model) {
        mModel = model;
    }
    
    /*
     * @see common.IGalleryController#addPhoto()
     */
    @Override
    public void addPhoto() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileFilter() {
            
            @Override
            public String getDescription() {
                return "Images | *.jpg|*.png|*.jpeg|";
            }
            
            @Override
            public boolean accept(File f) {
                if(f.isDirectory()) {
                    return true;
                }
                
                String fileName = f.getName();
                int idx = fileName.lastIndexOf('.');
                String ext = fileName.substring(idx + 1).toLowerCase();
                
                String[] supportedExts = new String[] { "jpg", "jpeg", "png" };
                for(String supported : supportedExts) {
                    if(ext.compareTo(supported) == 0) {
                        return true;
                    }
                }
                return false;
            }
        });
        int res = chooser.showOpenDialog(null);
        if(res == JOptionPane.OK_OPTION) {
            File selected = chooser.getSelectedFile();
            IPhotoModel photoModel = new PhotoModel(selected.getPath());
            IAlbumModel lastSelectedAlbum = mModel.getLastSelectedAlbum();
            lastSelectedAlbum.addPhoto(photoModel);
            
        }
    }

    /*
     * @see common.IGalleryController#addAlbum()
     */
    @Override
    public void addAlbum() {
        String name = null;
        while(name == null) {
            name = JOptionPane.showInputDialog("Album Name: ");
            if(mModel.getAlbum(name) != null) {
                JOptionPane.showMessageDialog(null, "Duplicate Album!\n\n" +
                        "An album with the specified name already exists.", 
                        "Error - Duplicate Album", JOptionPane.ERROR_MESSAGE);
                name = null;
            }
        }
        IAlbumModel albumModel = new AlbumModel(name);
        mModel.addAlbum(albumModel);
    }

    /*
     * @see common.IGalleryController#removeLastSelected()
     */
    @Override
    public void removeLastSelected() {
        IItemModel itemModel = mModel.getLastSelected();
        if(itemModel instanceof IAlbumModel) {
            IAlbumModel album = (IAlbumModel) itemModel;
            mModel.removeAlbum(album);
        } 
        else {
            IPhotoModel photo = (IPhotoModel)itemModel;
            photo.getAlbum().removePhoto(photo);
        }
    }

    /*
     * @see common.IGalleryController#renameLastSelected()
     */
    @Override
    public void renameLastSelected() {
        IItemModel item = mModel.getLastSelected();
        String name = null;
        while(name == null) {
            name = JOptionPane.showInputDialog("Name: ");
            if(name == null) {
                return;
            }
            else if(name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Empty Name!\n\n" +
                        "Must supply a new name.", "Error - Empty Name!",
                        JOptionPane.ERROR_MESSAGE);
                name = null;
            }
        }
        item.setName(name);
    }

    /*
     * @see common.IGalleryController#setIconToLastSelected()
     */
    @Override
    public void setIconToLastSelected() {
        IItemModel itemModel = mModel.getLastSelected();
        if(itemModel instanceof IPhotoModel) {
            IPhotoModel photoModel = (IPhotoModel)itemModel;
            IAlbumModel album = photoModel.getAlbum();
            Image image = photoModel.getImage();
            Image iconImage = image.getScaledInstance(album.getIcon().getIconWidth(), album.getIcon().getIconHeight(), Image.SCALE_FAST);
            album.setIcon(new ImageIcon(iconImage));
            ((AlbumModel)album).setIconImagePath(photoModel.getPath());
        }
    }

    /*
     * @see common.IGalleryController#selectItem(common.IItemModel)
     */
    @Override
    public void selectItem(IItemModel model) {
        mModel.setSelectedItem(model);
    }

    
    // --------------------------------------------------------- Private Fields
    
    private IGalleryModel mModel;
    
}
