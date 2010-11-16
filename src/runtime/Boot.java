// Boot.java
package runtime;

import javax.swing.SwingUtilities;
import controller.MainController;

/**
 * The class containing the main entry point to the application.
 */
public class Boot {
    
    /**
     * The main entry point to the application.
     * 
     * @param arguments: the command-line arguments passed in
     */
    public static void main(String[] arguments) {
        
        Application.parse(arguments);

        Runnable boot = new Runnable() {
            @Override
            public void run() {
                MainController controller = new MainController(Application.Settings);
                controller.start();
            }
        };

        SwingUtilities.invokeLater(boot);
        
    }

}