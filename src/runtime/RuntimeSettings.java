// RuntimeSettings.java
package runtime;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.swing.UIManager.LookAndFeelInfo;
import org.codecompanion.io.FileUtils;
import org.codecompanion.ui.dialogs.LookAndFeelChooser;

/**
 * Class representing the settings relevant to the runtime of the application.
 * This means that the settings contained within this class could be swapped
 * at runtime to alter the behavior of the application.
 */
public class RuntimeSettings {
    
    /**
     * Default constructor that sets all the runtime settings to their default
     * values.
     */
    public RuntimeSettings() {
        
        mSettingsFile = new File(Constants.USER_SETTINGS_FILE);
        mAlbumsFile = new File(Constants.USER_ALBUMS_FILE);
        mDebuggingEnabled = true;
        mErrorLog = new File(Constants.ERROR_LOG);
        mBackupFilesEnabled = false;
        
        if(!mSettingsFile.exists()) {
            URL defaultSettingsUrl = Boot.class.getResource(Constants.DEFAULT_SETTINGS_FILE);
            if(defaultSettingsUrl != null) {
                File defaultSettings = new File(defaultSettingsUrl.getPath());
                try {
                    FileUtils.copyFile(defaultSettings, mSettingsFile);
                }
                catch(IOException e) {
                    Application.dump(e);
                }
            }
        }
        
        if(!mAlbumsFile.exists()) {
            URL defaultAlbumsUrl = Boot.class.getResource(Constants.DEFAULT_ALBUMS_FILE);
            if(defaultAlbumsUrl != null) {
                File defaultAlbumsFile = new File(defaultAlbumsUrl.getPath());
                try {
                    FileUtils.copyFile(defaultAlbumsFile, mAlbumsFile);
                }
                catch (IOException e) {
                    Application.dump(e);
                }
            }
        }
    }

    /**
     * Constructor that determines the runtime settings from the command-line
     * arguments passed in as an array of strings. Any settings that can't
     * be determined from the command line arguments are set to their default
     * values.
     * 
     * @param arguments: array of strings representing the command-line arguments
     */
    public RuntimeSettings(String[] arguments) {
        
    }
    
    /**
     * Determines whether or not the settings file is backed up before being
     * modified. If true, before writing the new settings file, the old settings
     * file is copied into a new file with a name containing the date/time
     * of the backup.
     * 
     * @return true: if backup files are to be created; false otherwise
     */
    public boolean backupFilesEnabled() {
        return mBackupFilesEnabled;
    }
    
    /**
     * Determines the file containing the photo gallery's contents in xml format.
     * 
     * @return File: the xml file containing the contents of the gallery
     */
    public File albumsFile() {
        return mAlbumsFile;
    }
    
    /**
     * Determines the file containing the user settings in xml format.
     * 
     * @return File: the xml file containing the user settings
     */
    public File settingsFile() {
        return mSettingsFile;
    }

    /**
     * Determines whether or not debugging is enable. If debugging is turned
     * off, any exceptions that might be thrown are caught silently and dumped
     * to an application error log. If it is enabled, the exceptions will still
     * be written to the error log; however, they will be written to STDERR as
     * well.
     * 
     * @return true: if debugging is enabled; false otherwise
     */
    public boolean debuggingEnabled() {
        return mDebuggingEnabled;
    }
    
    /**
     * Determines the file in which any application errors or exceptions are
     * written to.
     * 
     * @return File: the log in which errors or exceptions are to be written
     */
    public File errorLog() {
        return this.mErrorLog;
    }
    
    /**
     * Determines any extra look and feel libraries that are available for the
     * user to use. The return value is a map with a string value representing
     * the category or vendor of the look & feel. The value for each key is a
     * list of LookAndFeelInfo instances that should be register as available.
     * The purpose for using a map is so that a look and feel that has multiple
     * themes or skins can have it's own category. For instance, the "substance"
     * look and feel has a large amount of themes for which each is a seperate
     * look and feel. Any look and feels that are just single class are inserted
     * under the key "Root".
     * 
     * @return Map: the map containing all the addition look and feels to
     *          register as available to the user.
     */
    public Map<String, List<LookAndFeelInfo>> extraLookAndFeels() {
        
        List<LookAndFeelInfo> rootLandfs = new LinkedList<LookAndFeelInfo>();        
        rootLandfs.add(new LookAndFeelInfo("Lipstik", "com.lipstikLF.LipstikLookAndFeel"));
        rootLandfs.add(new LookAndFeelInfo("Napkin", "net.sourceforge.napkinlaf.NapkinLookAndFeel"));
        rootLandfs.add(new LookAndFeelInfo("NimROD", "com.nilo.plaf.nimrod.NimRODLookAndFeel")); 
        rootLandfs.add(new LookAndFeelInfo("Metal", "net.infonode.gui.laf.InfoNodeLookAndFeel")); // Supports numerous themes... 
        rootLandfs.add(new LookAndFeelInfo("TinyLookAndFeel", "de.muntjak.tinylookandfeel.TinyLookAndFeel"));
        rootLandfs.add(new LookAndFeelInfo("Alloy", "com.incors.plaf.alloy.AlloyLookAndFeel"));
        rootLandfs.add(new LookAndFeelInfo("Squareness", "net.beeger.squareness.SquarenessLookAndFeel"));
        rootLandfs.add(new LookAndFeelInfo("Liquid", "com.birosoft.liquid.LiquidLookAndFeel"));
        rootLandfs.add(new LookAndFeelInfo("PgsLookAndFeel", "com.pagosoft.plaf.PgsLookAndFeel"));
        rootLandfs.add(new LookAndFeelInfo("Quaqua Leopard Cross Platform", "ch.randelshofer.quaqua.QuaquaLookAndFeel"));
        rootLandfs.add(new LookAndFeelInfo("Synthetica Standard Look and Feel", "de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel"));

        List<LookAndFeelInfo> substanceLandfs = new LinkedList<LookAndFeelInfo>();
        substanceLandfs.add(new LookAndFeelInfo("Business Blue Steel", "org.pushingpixels.substance.api.skin.SubstanceBusinessBlueSteelLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Business Black Steel", "org.pushingpixels.substance.api.skin.SubstanceBusinessBlackSteelLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Autumn", "org.pushingpixels.substance.api.skin.SubstanceAutumnLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Creme Coffee", "org.pushingpixels.substance.api.skin.SubstanceCremeCoffeeLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Creme", "org.pushingpixels.substance.api.skin.SubstanceCremeLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Challenger Deep", "org.pushingpixels.substance.api.skin.SubstanceChallengerDeepLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Business", "org.pushingpixels.substance.api.skin.SubstanceBusinessLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Dust Coffee", "org.pushingpixels.substance.api.skin.SubstanceDustCoffeeLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Dust", "org.pushingpixels.substance.api.skin.SubstanceDustLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Gemini", "org.pushingpixels.substance.api.skin.SubstanceGeminiLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Emerald Dusk", "org.pushingpixels.substance.api.skin.SubstanceGraphiteAquaLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Graphite Glass", "org.pushingpixels.substance.api.skin.SubstanceGraphiteGlassLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Graphite", "org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Magellan", "org.pushingpixels.substance.api.skin.SubstanceMagellanLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Mariner", "org.pushingpixels.substance.api.skin.SubstanceMarinerLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Mist Aqua", "org.pushingpixels.substance.api.skin.SubstanceMistAquaLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Mist Silver", "org.pushingpixels.substance.api.skin.SubstanceMistSilverLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Moderate", "org.pushingpixels.substance.api.skin.SubstanceModerateLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Nebula Brick Wall", "org.pushingpixels.substance.api.skin.SubstanceNebulaBrickWallLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Nebula", "org.pushingpixels.substance.api.skin.SubstanceNebulaLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Office Black 2007", "org.pushingpixels.substance.api.skin.SubstanceOfficeBlack2007LookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Office Blue 2007", "org.pushingpixels.substance.api.skin.SubstanceOfficeBlue2007LookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Office Silver 2007", "org.pushingpixels.substance.api.skin.SubstanceOfficeSilver2007LookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Raven", "org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Sahara", "org.pushingpixels.substance.api.skin.SubstanceSaharaLookAndFeel"));
        substanceLandfs.add(new LookAndFeelInfo("Twilight", "org.pushingpixels.substance.api.skin.SubstanceTwilightLookAndFeel"));
        
        List<LookAndFeelInfo> jgoodiesLandfs = new LinkedList<LookAndFeelInfo>();
        jgoodiesLandfs.add(new LookAndFeelInfo("JGoodies Plastic 3D", "com.jgoodies.looks.plastic.Plastic3DLookAndFeel"));
        jgoodiesLandfs.add(new LookAndFeelInfo("JGoodies Plastic", "com.jgoodies.looks.plastic.PlasticLookAndFeel"));
        jgoodiesLandfs.add(new LookAndFeelInfo("JGoodies Plastic XP", "com.jgoodies.looks.plastic.PlasticXPLookAndFeel"));
        jgoodiesLandfs.add(new LookAndFeelInfo("JGoodies Windows", "com.jgoodies.looks.windows.WindowsLookAndFeel"));
        
        List<LookAndFeelInfo> jtattooLandfs = new LinkedList<LookAndFeelInfo>();
        jtattooLandfs.add(new LookAndFeelInfo("Aero", "com.jtattoo.plaf.aero.AeroLookAndFeel"));
        jtattooLandfs.add(new LookAndFeelInfo("Acryl", "com.jtattoo.plaf.acryl.AcrylLookAndFeel"));
        jtattooLandfs.add(new LookAndFeelInfo("Aluminium", "com.jtattoo.plaf.aluminium.AluminiumLookAndFeel"));
        jtattooLandfs.add(new LookAndFeelInfo("Bernstein", "com.jtattoo.plaf.bernstein.BernsteinLookAndFeel"));
        jtattooLandfs.add(new LookAndFeelInfo("Fast", "com.jtattoo.plaf.fast.FastLookAndFeel"));
        jtattooLandfs.add(new LookAndFeelInfo("Graphite", "com.jtattoo.plaf.graphite.GraphiteLookAndFeel"));
        jtattooLandfs.add(new LookAndFeelInfo("HiFi", "com.jtattoo.plaf.hifi.HiFiLookAndFeel"));
        jtattooLandfs.add(new LookAndFeelInfo("XP", "com.jtattoo.plaf.luna.LunaLookAndFeel"));
        jtattooLandfs.add(new LookAndFeelInfo("Mint", "com.jtattoo.plaf.mint.MintLookAndFeel"));
        jtattooLandfs.add(new LookAndFeelInfo("Noire", "com.jtattoo.plaf.noire.NoireLookAndFeel"));
        jtattooLandfs.add(new LookAndFeelInfo("Smart", "com.jtattoo.plaf.smart.SmartLookAndFeel"));
        jtattooLandfs.add(new LookAndFeelInfo("McWin", "com.jtattoo.plaf.mcwin.McWinLookAndFeel"));
     
        Map<String, List<LookAndFeelInfo>> landfs = new HashMap<String, List<LookAndFeelInfo>>();
        landfs.put(LookAndFeelChooser.MENU_ROOT, rootLandfs);
        landfs.put("Substance", substanceLandfs);
        landfs.put("JGoodies", jgoodiesLandfs);
        landfs.put("JTattoo", jtattooLandfs);
        
        return landfs;
    }

    
    // --------------------------------------------------------- Private Fields
    
    private File mAlbumsFile;
    private File mSettingsFile;
    private File mErrorLog;
    private boolean mDebuggingEnabled;
    private boolean mBackupFilesEnabled;
 
}
