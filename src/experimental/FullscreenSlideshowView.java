//package experimental;
//
//import java.awt.BorderLayout;
//import java.awt.Frame;
//import java.awt.Graphics;
//import java.awt.GraphicsConfiguration;
//import java.awt.GraphicsDevice;
//import java.awt.MediaTracker;
//import java.awt.event.ActionEvent;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseMotionListener;
//import javax.swing.AbstractAction;
//import javax.swing.JToolBar;
//import runtime.Application;
//import runtime.Constants;
//import view.SlideshowView.PauseSlideshowAction;
//import view.SlideshowView.SlideshowMouseMotionListener;
//import view.SlideshowView.SlideshowNextAction;
//import view.SlideshowView.SlideshowPreviousAction;
//import view.SlideshowView.StartSlideshowAction;
//import view.SlideshowView.StopSlideshowAction;
//import common.controller.ISlideshowController;
//import common.model.IItemModel;
//import common.model.IPhotoModel;
//
//
//public class FullscreenSlideshowView {
//    public SlideshowView(ISlideshowController controller) {
//        super();
//        
//        mController = controller;
//        mToolBar = new JToolBar("Slideshow");
//        mToolBar.add(new StartSlideshowAction());
//        mToolBar.add(new StopSlideshowAction());
//        mToolBar.add(new PauseSlideshowAction());
//        mToolBar.add(new SlideshowNextAction());
//        mToolBar.add(new SlideshowPreviousAction());
//        
//        mMediaTracker = new MediaTracker(this);
//        mCurrentPhoto = null;
//        mPhotoCount = 0;
//
//    }
//       
//    public void initialize() {
//        
//        GraphicsDevice device = Application.Graphics.getDefaultScreenDevice();
//        GraphicsConfiguration gc = device.getDefaultConfiguration();
//        
//        mFrame = new Frame(gc);
//        mFrame.setLayout(new BorderLayout());
//        mFrame.add(this, BorderLayout.CENTER);
//        mFrame.add(mToolBar, BorderLayout.SOUTH);
//        mFrame.setUndecorated(true);
//        
//        if(device.isFullScreenSupported()) {
//            device.setFullScreenWindow(mFrame);
//        }
//        
//        if(device.isDisplayChangeSupported()) {
//            device.setDisplayMode(Application.Runtime.bestDisplayMode(device));
//        }
//        
//        mToolBar.setVisible(false);
//        
//        addMouseMotionListener(new SlideshowMouseMotionListener());
//      
//    }
//    
//    public void paint(Graphics g) {
//        if(mCurrentPhoto != null) {
//            g.drawImage(mCurrentPhoto.getImage(), 10, 10, mFrame);
//        }
//    }
//
//    @Override
//    public void update(IItemModel selected) {
//        mCurrentPhoto = (IPhotoModel)selected;
//        mMediaTracker.addImage(mCurrentPhoto.getImage(), ++mPhotoCount);
//        try {
//            mMediaTracker.waitForID(mPhotoCount);
//            if(mMediaTracker.isErrorAny()) {
//                Application.dump(new Exception("Error loading image."));
//            }
//        } catch (Exception e) {
//            Application.dump(e);
//        }
//        repaint();
//    }
//
//    private class SlideshowMouseMotionListener implements MouseMotionListener {
//
//        @Override
//        public void mouseMoved(MouseEvent e) {
//            showAndFadeToolbar();
//        }
//        
//        @Override
//        public void mouseDragged(MouseEvent e) {
//            showAndFadeToolbar();
//        }
//        
//        private void showAndFadeToolbar() {
//            mToolBar.setVisible(true);
//        }
//        
//    }
//    
//   
//    
//    private ISlideshowController mController;
//    private IPhotoModel mCurrentPhoto;
//    private JToolBar mToolBar;
//    private Frame mFrame;
//    private MediaTracker mMediaTracker;
//    private Integer mPhotoCount;
//}
