// PhotoController.java
package controller;

import java.awt.Dimension;
import common.IPhotoController;
import common.IPhotoModel;

/**
 * A concrete implementation of the IPhotoController interface. This class 
 * provides the implementations of all the actions that can be performed on
 * a photo.
 */
public class PhotoController implements IPhotoController {

    /**
     * Constructor that instantiates a new photo controller with it's photo set
     * to the value of the model passed in.
     * 
     * @param photo: the initial photo model of this controller
     */
    public PhotoController(IPhotoModel photo) {
        mModel = photo;
    }
    
    /*
     * @see common.IPhotoController#setPhoto(common.IPhotoModel)
     */
    @Override
    public void setPhoto(IPhotoModel photo) {
        mModel = photo;
    }
    
    /*
     * @see common.IPhotoController#zoomOriginal()
     */
    @Override
    public void zoomOriginal() {
        mModel.resetImage();
    }

    /*
     * @see common.IPhotoController#zoomToFit(java.awt.Dimension)
     */
    @Override
    public void zoomToFit(Dimension dim) {
        
        int parentWidth = dim.width;
        int parentHeight = dim.height;
        int width = (mModel.getImage().getWidth(null));
        int height = (mModel.getImage().getHeight(null));
        
        boolean isWidthConstraining = width > height;
        if(isWidthConstraining) {
            mZoomFactor -= (parentWidth/width);
            mModel.scaleImage(parentWidth, height );
        } else {
            mModel.scaleImage(width, parentHeight); 
        }
        
    }

    /*
     * @see common.IPhotoController#zoomIn()
     */
    @Override
    public void zoomIn() {
        mCurZoomCount += 1;
        mZoomFactor += 0.1;
        int width = (int)(mModel.getImage().getWidth(null) * mZoomFactor);
        int height = (int)(mModel.getImage().getHeight(null) * mZoomFactor);
        mModel.scaleImage(width, height);
    }

    /*
     * @see common.IPhotoController#zoomOut()
     */
    @Override
    public void zoomOut() {
        mCurZoomCount -= 1;
        mZoomFactor -= 0.1;
        int width = (int)(mModel.getImage().getWidth(null) * mZoomFactor);
        int height = (int) (mModel.getImage().getHeight(null) * mZoomFactor);
        mModel.scaleImage(width, height);
    }

    
    //---------------------------------------------------------- Private Fields
    
    private IPhotoModel mModel;
    private double mZoomFactor = 1.0;
    private int mCurZoomCount = 0;
     
}
