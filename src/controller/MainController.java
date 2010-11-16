// MainController.java
package controller;

import io.XmlSettingsWriter;
import runtime.Application;
import ui.view.MainView;
import common.IMainController;
import common.ISettingsModel;

/**
 * A concrete implementation of the IMainController interface. This class 
 * provides the implementations for all the actions that can be performed on 
 * the running application.
 */
public class MainController implements IMainController {
    
    /**
     * Constructor that instantiates a new main controller with it's model set
     * to the value of the argument passed in.
     * 
     * @param model: the model of the newly instatiated MainController
     */
    public MainController(ISettingsModel model) {
        mModel = model;
    }

    /**
     * Gets the view associated with this controller.
     * 
     * @return MainView: the view associated with this controller.
     */
    public static MainView getView() {
        return mView;
    }
    
    // -------------------------------------------------------- IMainController 

    /*
     * @see common.IMainController#toggleMainToolBar()
     */
    @Override
    public void toggleMainToolBar() {
        // TODO Auto-Generated Method Stub
        
    }    
    
    /*
     * @see common.IMainController#start()
     */
    @Override
    public void start() {
        try {
            mView = new MainView(this, Application.Settings);
            mView.setVisible(true);
        }
        catch(Exception e) {
            Application.dump(e);
        }
    }

    /*
     * @see common.IMainController#exitApplication()
     */
    @Override
    public void exitApplication() {
        XmlSettingsWriter writer = new XmlSettingsWriter(mModel);
        writer.write(Application.Runtime.settingsFile());
        System.exit(0);
    }
 
    // --------------------------------------------------------- Private Fields
    
    private ISettingsModel mModel;
    private static MainView mView;
}
