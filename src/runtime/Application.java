// Application.java
package runtime;

import io.XmlSettingsReader;
import java.awt.GraphicsEnvironment;
import java.io.FileWriter;
import java.io.IOException;
import common.ISettingsModel;

/**
 * Class representing an application wide interface to actions and properites
 * that are common to a lot of the application.
 */
public class Application {
    
    /** The Runtime Settings for the current application instance. */
    public static RuntimeSettings Runtime;
    
    /** The User Settings for the current application instance */
    public static ISettingsModel Settings;
    
    /** The Graphics Environment for the current application instance */
    public static GraphicsEnvironment Graphics;
    
    static {
        Runtime = new RuntimeSettings();
        Settings = new UserSettings(new XmlSettingsReader(Runtime.settingsFile()));
        Graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
    }
 
    /**
     * Method that takes in an exception and outputs the exception to an error
     * log in xml format. Depending on the runtime settings, this method will
     * also dump the exception to the console.
     * 
     * @param e: the exception to dump
     */
    public static void dump(Exception e) {
        if(Runtime.debuggingEnabled()) {
            System.err.println("Message: " + e.getMessage());
            System.err.print("Stack Trace: ");
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter(Application.Runtime.errorLog(),true);
            writer.write("<Error Type=\"" + e.getClass().getName() + "\">\n");
            writer.write("\t<Message>" + e.getMessage() + "</Message>\n");
            writer.write("\t<StackTrace>\n");
            for(StackTraceElement elem : e.getStackTrace()) {
                writer.write("\t\t<Position Class=\"" + elem.getClassName() + 
                    "\" Method=\"" + elem.getMethodName() + "\" Line=\"" +
                    elem.getLineNumber() + "\" File=\"" + elem.getFileName() +"\"/>\n");
            }
            writer.write("\t</StackTrace>\n</Error>\n");
            writer.close();
        }
        catch (IOException e1) {}
        
        
    }
    
    /**
     * Parses the array of command-line arguments and modifies the runtime
     * settings accordingly. If the array passed in is empty, this method 
     * simply ignores it.
     * 
     * @param arguments: the command-line arguments to apply to the runtime
     *          settings.
     */
    static void parse(String[] arguments) {
        if(arguments.length > 0) {
            Runtime = new RuntimeSettings(arguments);
        }
    }
    
    /**
     * Gets the resource path for path that is relative to the project. The
     * resources directory is located under the runtime package in a package
     * called resources. Therefore, a path such as "src/runtime/resources/..."
     * would be returned as "resources/...". This is useful for when an 
     * specified relative to the project needs to be accessed as a resource.
     * 
     * @param path: the path relative to the project
     * @return String: the path relative to the application resources
     */
    public static String getResourcePath(String path) {
        return path.replace("src/runtime/", "");
    }
    
    /**
     * Gets a path that is specified relative the application resources as a
     * modified path relative to the project. The application resources are
     * located under the runtime package in the package called resources. 
     * Therefore, a path passed in as "resources/images/..." gets returned as
     * "src/runtime/resources/images/...". This is useful for when an item
     * specified relative to the application resources needs to be accessed
     * without the usual resource resolving techniques.
     * 
     * @param path: the path relative to the application resources
     * @return String: the modified path relative to the project
     */
    public static String getProjectPath(String path) {
        if(isResourcePath(path)) {
            return "src/runtime/" + path;
        }
        return null;
    }
    
    /**
     * Checks if the path specified is relative to the application resources
     * or not.
     * 
     * @param path: the path to check
     * @return true: if the path passed in is relative to the application
     *      resources; false otherwise.
     */
    public static boolean isResourcePath(String path) {
        return path.startsWith("resources/");
    }
}




