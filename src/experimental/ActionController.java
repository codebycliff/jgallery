//package experimental;
//
//import java.awt.event.ActionEvent;
//import java.awt.event.KeyEvent;
//import javax.swing.AbstractAction;
//import javax.swing.Action;
//import javax.swing.JOptionPane;
//import model.AlbumModel;
//import model.PhotoModel;
//import runtime.Application;
//import runtime.Constants;
//import common.controller.IMainController;
//import common.model.IAlbumModel;
//import common.model.IGalleryModel;
//
//
//public class ActionController {
//
//    public ActionController(IMainController controller, IGalleryModel model) {
//        mController = controller;
//        mModel = model;
//    }
//    
//    public Action getAddAlbumAction() {
//        return new AddAlbumAction();
//    }
//    
//    public Action getRemoveItemAction() {
//        return new RemoveItemAction();
//    }
//    
//    public Action getRenameItemAction () {
//        return new RemoveItemAction();
//    }
//    
//    public Action getSetAlbumIconAction() {
//        return new SetAlbumIconAction();
//    }
//    
//    public Action getAddPhotoToAlbumAction() {
//        return new AddPhotoToAlbumAction();
//    }
//    
//    
//    private class AddAlbumAction extends AbstractAction {
//     
//        public AddAlbumAction() {
//            putValue(NAME, "Add Album");
//            putValue(MNEMONIC_KEY, KeyEvent.VK_A);
//            putValue(SMALL_ICON, Application.Settings.getIcon(Constants.Actions.ADDALBUM_SMALLICON));
//            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(Constants.Actions.ADDALBUM_SMALLICON));
//            putValue(SHORT_DESCRIPTION, "Add an album of images");
//        }
//        
//        @Override
//        public void actionPerformed(ActionEvent ae) {
//            Object source = ae.getSource();
//            if(source instanceof IAlbumModel) {
//                IAlbumModel album = (IAlbumModel)source;
//                mModel.add(album);    
//            }
//            
//        }
//
//    }
//    
//    private class RemoveItemAction extends AbstractAction {
//        
//        public RemoveItemAction() {
//            putValue(NAME, "Delete");
//            putValue(MNEMONIC_KEY, KeyEvent.VK_DELETE);
//            putValue(SMALL_ICON, Application.Settings.getIcon(Constants.Actions.DELETE_SMALLICON));
//            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(Constants.Actions.DELETE_SMALLICON));
//            putValue(SHORT_DESCRIPTION, "Delete the selected item.");
//        }
//        
//        @Override
//        public void actionPerformed(ActionEvent ae) {
//            Object o = ae.getSource();
//            
//            if(o instanceof AlbumModel){
//                AlbumModel album = (AlbumModel)o;
//                mModel.remove(album);
//            }else if(o instanceof PhotoModel){
//                PhotoModel photo = (PhotoModel)o;
//                IAlbumModel album = photo.getAlbum();
//                album.remove(photo);
//            }
//        }
//
//    }
//
//    private class RenameItemAction extends AbstractAction {
//        
//        private RenameItemAction() {
//            putValue(NAME, "Rename");
//            putValue(MNEMONIC_KEY, KeyEvent.VK_F2);
//            putValue(SMALL_ICON, Application.Settings.getIcon(Constants.Actions.RENAME_SMALLICON));
//            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(Constants.Actions.RENAME_SMALLICON));
//            putValue(SHORT_DESCRIPTION, "Rename the selected item.");
//        }
//        
//        @Override
//        public void actionPerformed(ActionEvent ae) {
//            Object o = ae.getSource();
//            String newName = JOptionPane.showInputDialog(null, "Name:");
//            
//            if(o instanceof AlbumModel){
//                AlbumModel album = (AlbumModel)o;
//                album.setName(newName);
//            }else if(o instanceof PhotoModel){
//                PhotoModel photo = (PhotoModel)o;
//                photo.setName(newName);
//            }
//        }
//
//    }
//   
//    private class SetAlbumIconAction extends AbstractAction {
//        
//        private SetAlbumIconAction() {
//            putValue(NAME, "Set as Album Icon");
//            putValue(MNEMONIC_KEY, KeyEvent.VK_E);
//            putValue(SMALL_ICON, Application.Settings.getIcon(Constants.Actions.SETALBUMICON_SMALLICON));
//            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(Constants.Actions.SETALBUMICON_LARGEICON));
//            putValue(SHORT_DESCRIPTION, "Set this image as the album's icon.");
//        }
//        
//        @Override
//        public void actionPerformed(ActionEvent ae) {
//            PhotoModel photo = (PhotoModel)ae.getSource();
//            AlbumModel album = (AlbumModel) photo.getAlbum();
//            album.setIcon(photo);
//        }
//
//    }
//    
//    private class AddPhotoToAlbumAction extends AbstractAction {
//        
//        private AddPhotoToAlbumAction() {
//            putValue(NAME, "Add Photo");
//            putValue(MNEMONIC_KEY, KeyEvent.VK_E);
//            putValue(SMALL_ICON, Application.Settings.getIcon(Constants.Actions.ADDPHOTO_SMALLICON));
//            putValue(LARGE_ICON_KEY, Application.Settings.getIcon(Constants.Actions.ADDPHOTO_SMALLICON));
//            putValue(SHORT_DESCRIPTION, "Add photo to currently selected album.");
//        }
//        
//        @Override
//        public void actionPerformed(ActionEvent ae) {
//            //IMPLEMENT: yeah.
//        }
//
//        private static final long serialVersionUID = 1L;
//    }
//    
//    
//    private IMainController mController;
//    private IGalleryModel mModel;
//
//}
