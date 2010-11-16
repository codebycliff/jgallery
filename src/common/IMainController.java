// IMainController.java
package common;

/**
 * An interface defining the contract that which any implementing main
 * controllers must adhere to. This controller provides the actions for which
 * the main application must support.
 */
public interface IMainController {

    /**
     * Starts the application and displays the user interface. 
     */
    void start();

    /**
     * Toggles the user interface's main tool bar.
     */
    void toggleMainToolBar();
    
    /**
     * Exits the application and performs any necessary work before shutting
     * down.
     */
    void exitApplication();
    
}
